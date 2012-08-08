/**
 * For copyright information see the LICENSE document.
 */

package com.realityshard.shardlet.utils;

import com.realityshard.shardlet.Event;
import com.realityshard.shardlet.EventAggregator;
import com.realityshard.shardlet.EventHandler;
import com.realityshard.shardlet.GlobalExecutor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * <p>
 * Implementation of the event aggregator pattern (or some kind of it)
 * This should be concurrent:
 * It is able to handle event triggers from different threads and
 * creates a new invokation-execution for each handler via
 * the provided executor.
 * </p>
 * 
 * <p>
 * New in version 0.1.1:
 * Events may be triggered bound to a special context object.
 * This extended functionality was needed by the entity system, because
 * we may want to choose from which entity we want to recieve events.
 * (In that case this entity from which we want to recieve events only is our
 * context. Note that the context can be ANY object. So its not bound to entities only)
 * Thats kind of an anonymous observer pattern...
 * 
 * Also i have removed the term 'listener' from the comments. 
 * See the EventAggregator interface for explanations.
 * </p> 
 * 
 * @author _rusty
 *
 */
public final class ConcurrentEventAggregator implements EventAggregator
{
    
    /**
     * Used for keeping the listener-object/listener-method
     * stuff within a pair. (Needed for invokation of the method lateron)
     */
    private static final class EventHandlerReference
    {
        private Object handlerObject;
        private Object context;
        private Method handlerMethod;
        
        
        /**
         * Constructor.
         * 
         * @param       object
         * @param       entity
         * @param       handler 
         */
        public EventHandlerReference(Object object, Object context, Method handler)
        {
            this.handlerObject = object;
            this.context = context;
            this.handlerMethod = handler;
        }

        
        /**
         * Getter.
         * 
         * @return      The object that holds the method
         */
        public Object getObject() 
        {
            return handlerObject;
        }
        
        
        /**
         * Getter.
         * 
         * @return      The entity connected to this event handler
         */
        public Object getContext()
        {
            return context;
        }
        
        
        /**
         * Getter.
         * 
         * @return      The method declared within the class of the object
         */
        public Method getHandler() 
        {
            return handlerMethod;
        }     
    }
    
    
    /**
     * Used to invoke the listeners dynamically and by the executor
     */
    private static final class Invokable implements Runnable
    {
        
        private final EventHandlerReference invokableHandler;
        private final Event parameter;
        
        
        /**
         * Constructor.
         * 
         * @param       invokableHandler
         * @param       parameter 
         */
        public Invokable(EventHandlerReference invokableHandler, Event parameter)
        {
            this.invokableHandler = invokableHandler;
            this.parameter = parameter;
            
        }
        
        
        /**
         * Will be executed by executor
         */
        @Override
        public void run() 
        {
            try 
            {
                invokableHandler.getHandler()
                        .invoke(invokableHandler.getObject(), parameter);
            } 
            catch (    IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) 
            {
                LOGGER.warn("Could not execute an event handler", ex);
            }
        }
    }
    
    
    private static final Logger LOGGER = LoggerFactory.getLogger(ConcurrentEventAggregator.class);
    
    private final Map<Class<? extends Event>, List<EventHandlerReference>> eventMapping;
    private final Executor executor;
    
    
    /**
     * Constructor.
     */
    public ConcurrentEventAggregator()
    {
        // get the executor defined by the application
        this.executor = GlobalExecutor.get();
        eventMapping = new ConcurrentHashMap<>();
    }
    
    
    /**
     * Add a new object with handler implementations to the event->listener association
     * 
     * @param       handlerImpl             The listener object. The aggregator will call the
     *                                      handler methods of this object in case of an event.
     */
    @Override
    public void register(Object handlerImpl)
    {
        register(handlerImpl, null);
    }
    
    
    /**
     * Add an entity-dependent listener to the listener collection.
     * Note that by using the usual entity independent addListener method,
     * you will always recieve events, even if they dont concern your entity/component.
     * 
     * @param       entity                  The concrete entity that you want to recieve events for.
     * @param       listener                The listener object (may contain several event-handlers, as usual!)
     */
    @Override
    public void register(Object handlerImpl, Object context)
    {
        // get all the declared methods of the object with the handler implementations
        // so we can look for annotations
        Method[] methods = handlerImpl.getClass().getDeclaredMethods();
        
        // try extracting the methods with our handler annotation, specified in
        // Shardlet.EventHandler

        for (Method method : methods) 
        {
            // failchecks first
            
            // check if the method has an annotation of type EventHandler
            if (method.getAnnotation(EventHandler.class) == null) { continue; }

            Class<?>[] params = method.getParameterTypes();
            
            // check if the method follows the general handler method conventions
            // meaning it takes only one argument which has a class that implements
            // Shardlet.Event
            if (params.length != 1 || !Event.class.isAssignableFrom(params[0]))
            {
                LOGGER.warn("An object has a method that is annotated as EventHandler but doesnt follow the signature.", handlerImpl);
                continue;
            }
            
            // we've got a valid handler. lets add it :D

            // check wich event class we are looking for...
            Class<? extends Event> clazz = (Class<? extends Event>)params[0];
            
            // we need to check whether we need to use a context
            Object handlerContext = context;
            
            if (method.getAnnotation(EventHandler.class).forceGlobal())
            {
                handlerContext = null;
            }
            
            // lets build the EventHandlerReference now, used to store the handler its containing object and
            // the context
            EventHandlerReference handler = new EventHandlerReference(handlerImpl, handlerContext, method);

            // we want to add the handler methods to a list of methods that have the same
            // signature, and thus handle the same event
            // so try to get the list
            List<EventHandlerReference> list = eventMapping.get(clazz);

            if (list == null)
            {
                // if there is no entry yet, create a new handler list for this event
                list = new CopyOnWriteArrayList<>();
                eventMapping.put(clazz, list);
            }

            // finally add the handler reference
            list.add(handler);
        }
    }
    
    
    /**
     * Remove all event handlers associated to a given context from this aggregator.
     * 
     * @param       entity                  All handlers from this context object will be removed from the
     *                                      event-aggregator. (No matter which object holds them)
     */
    @Override
    public void unregisterByContext(Object context) 
    {
        // its kinda tricky to remove stuff from our mapping, because we cant simply
        // look up an event. the values of our hashmap consist of lists of the associations
        // that we want to remove, so me must iterate those lists and find the values, then we must remove them from the lists
        
        List<EventHandlerReference> found = new ArrayList<>();
        
        // iterate trough the dictionary values, getting a list of associations
        for (List<EventHandlerReference> list: eventMapping.values())
        {
            // now iterate through the associations, and search for the 'entity'
            for (EventHandlerReference handler: list)
            {
                if (handler.getContext().equals(context))
                {
                    // we've found one of the associations that we want to remove.
                    // temporarily save it...
                    found.add(handler);
                }
            }
            
             // remove all those associations that we found
            for (EventHandlerReference handler: found)
            {
                list.remove(handler);
            }
            
            // clear up the list that we are using to temporarily save associations
            // because we'll simply re-use it for the next hashmap value
            found.clear();
        }
    }
    
    
    /**
     * Remove all event handlers implemented by a given object from this aggregator.
     * 
     * @param       handlerImpl             The object that implements the handlers.
     *                                      It's the scope of the handlers actually.
     */
    @Override
    public void unregisterByImpl(Object handlerImpl)
    {
        // its kinda tricky to remove stuff from our mapping, because we cant simply
        // look up an event. the values of our hashmap consist of lists of the associations
        // that we want to remove, so me must iterate those lists and find the values, then we must remove them from the lists
        
        List<EventHandlerReference> found = new ArrayList<>();
        
        // iterate trough the dictionary values, getting a list of associations
        for (List<EventHandlerReference> list: eventMapping.values())
        {
            // now iterate through the associations, and search for the object that implements
            // the handlers and that equals the one we have been given
            for (EventHandlerReference handler: list)
            {
                if (handler.getObject().equals(handlerImpl))
                {
                    // we've found one of the associations that we want to remove.
                    // temporarily save it...
                    found.add(handler);
                }
            }
            
             // remove all those associations that we found
            for (EventHandlerReference handler: found)
            {
                list.remove(handler);
            }
            
            // clear up the list that we are using to temporarily save associations
            // because we'll simply re-use it for the next hashmap value
            found.clear();
        }
    }
    
    
    /**
     * Trigger an event, the aggregator will try to distribute 
     * it to the appropriate handlers
     * 
     * @param       event                   The concrete event     
     */
    @Override
    public void triggerEvent(Event event)
    {
        // get the listeners of the event
        List<EventHandlerReference> handlers = eventMapping.get(event.getClass());
        
        // failcheck
        if (handlers == null) { return; }
        
        for (EventHandlerReference handler: handlers)
        {
            // for each handler in the handler collection,
            // try to invoke the handler with
            // the object that holds it and the event
            // but make sure that we only invoke handlers marked as global...
            // (meaning they are not associated with any context)
            if (handler.getContext() == null)
            {
                executor.execute(new Invokable(handler, event));
            }
        }
    }
    
    
    /**
     * Trigger an event, the aggregator will try to distribute 
     * it to the appropriate handlers
     * 
     * @param       event                   The concrete event     
     * @param       context                 The event's context object. All global
     *                                      handlers of that event will be triggered, and
     *                                      additionally all events that solely subscribed for the
     *                                      given context object.
     */
    @Override
    public void triggerEvent(Event event, Object context) 
    {
        // get the listeners of the event
        List<EventHandlerReference> handlers = eventMapping.get(event.getClass());
        
        // failcheck
        if (handlers == null) { return; }
        
        // get the ones that we actually want to invoke
        for (EventHandlerReference handler : handlers) 
        {
            // we want to invoke methods that:
            //  a) are associated to the context or
            //  b) have no associated context (null reference), and thus are global
            if (handler.getContext().equals(context) || handler.getContext() == null)
            {
                // for each handler in the handler collection,
                // try to invoke the handler object with
                // the object that holds it and the event
                executor.execute(new Invokable(handler, event));
            }
        }
    }
}
