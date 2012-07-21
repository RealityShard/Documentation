/**
 * For copyright information see the LICENSE document.
 */

package com.realityshard.shardlet.utils;

import com.realityshard.shardlet.Event;
import com.realityshard.shardlet.EventAggregator;
import com.realityshard.shardlet.EventHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Implementation of the event aggregator pattern (or some kind of it)
 * This should be concurrent:
 * It is able to handle event triggers from different threads and
 * creates a new invokation-execution for each listener via
 * the extern provided Executor.
 * 
 * @author _rusty
 *
 */
public final class ConcurrentEventAggregator implements EventAggregator
{
    
    /**
     * Used for keeping the listener-object/listener-method
     * stuff within a pair. (Needed for invokation of the method later on)
     */
    private static final class EventHandlerReference
    {
        private Object object;
        private Method handler;
        
        
        /**
         * Constructor.
         * 
         * @param       object
         * @param       method 
         */
        public EventHandlerReference(Object object, Method method)
        {
            this.object = object;
            this.handler = method;
        }

        
        /**
         * Getter.
         * 
         * @return      The object that holds the method
         */
        public Object getObject() 
        {
            return object;
        }
        
        
        /**
         * Getter.
         * 
         * @return      The method declared within the class of the object
         */
        public Method getHandler() 
        {
            return handler;
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
    public ConcurrentEventAggregator(Executor executor)
    {
        // get the executor defined by the application
        this.executor = executor;
        
        eventMapping = new ConcurrentHashMap<>();
    }
    
    
    /**
     * Add a new listener to the event->listener association
     * 
     * @param       listener                The listener object. The aggregator will call the
     *                                      handler methods of this object in case of an event.
     */
    @Override
    public void addListener(Object listener)
    {
        // get all the declared methods of the listener, so we can look for annotations
        Method[] methods = listener.getClass().getDeclaredMethods();
        
        // try extracting the methods with our listener annotation, specified in
        // Shardlet.EventHandler
        Map<Class<? extends Event>, Method> listenerMethods = new HashMap<>();
        for (Method method : methods) 
        {
            // check if the method has an annotation of type EventHandler
            if(method.getAnnotation(EventHandler.class) != null)
            {
                Class<?>[] params = method.getParameterTypes();
                // check if the method follows the general listener method conventions
                // meaning it takes only one argument which has a class that implements
                // Shardlet.Event
                if (params.length == 1 && Event.class.isAssignableFrom(params[0]))
                {
                    listenerMethods.put((Class<? extends Event>)params[0], method);
                }
                else
                {
                    LOGGER.warn("Listener has a method that is annotated as EventHandler but doesnt follow the signature.", listener);
                }
            }
        }
        
        // add the methods to the aggregator
        for (Map.Entry<Class<? extends Event>, Method> entry : listenerMethods.entrySet()) 
        {
            Class<? extends Event> clazz = entry.getKey();
            EventHandlerReference handler = new EventHandlerReference(listener, entry.getValue());
        
            // we want to add the listener methods to a list of methods that have the same
            // signature, and thus listen for the same event
            // so try to get the list
            List<EventHandlerReference> list = eventMapping.get(clazz);

            if (list == null)
            {
                // if there is no entry yet, create a new listener list
                list = new CopyOnWriteArrayList<>();
                eventMapping.put(clazz, list);
            }

            // finally add the listener
            list.add(handler);
        }
    }
    
    
    /**
     * Trigger an event, the aggregator will try to distribute 
     * it to the appropriate listeners
     * 
     * @param       event                   The concrete event     
     */
    @Override
    public void triggerEvent(Event event)
    {
        // get the listeners of the event
        List<EventHandlerReference> listeners = eventMapping.get(event.getClass());
        
        // failcheck
        if (listeners == null) { return; }
        
        for (EventHandlerReference handler: listeners)
        {
            // for each listener in the listener collection,
            // try to invoke the handler with
            // the object that holds it and the event
            executor.execute(new Invokable(handler, event));
        }
    }
} 
