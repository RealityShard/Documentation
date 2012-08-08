/**
 * For copyright information see the LICENSE document.
 */

package com.realityshard.shardlet;


/**
 * <p>
 * An EventAggregator is the EventAggregator-pattern class implemented within
 * the shardlet.utils package that will manage the events coming from or triggering 
 * any handlers within the shardlets (or other objects that register themselfes with
 * the aggregator).
 * </p>
 * 
 * <p>
 * Context-bound means you can publish events that only concern a special context object.
 * This HAS NOTHING TO DO WITH SHARDLET CONTEXTS! It only means that you can practically
 * use any object as an implicit observable. Other objects can trigger events within
 * that context. That means the observable doesnt need to trigger events itself
 * to be a valid context!
 * NOTE! If you register a global (bound to no context) handler, you will recieve ALL
 * events of that type. (No matter whether they are explicitly context bound or global)
 * </p>
 * 
 * <p>
 * New in version 0.1.1:
 * Merged the interface with an extended version first implemented within 
 * the entitysystem.
 * 
 * Removed the term 'listener' from this interface and any implementations. It
 * was confusing and never used in another place.
 * A listener was intended to be the object that holds event-handler methods.
 * For now, we only use the term handler because it describes the functionality best.
 * 
 * It is now possible to remove handlers completely from this event aggregator.
 * You can do this either by removing all handlers of a context, or removing all
 * handlers that held by a given object (that are implemented in that object).
 * </p>
 * 
 * @author _rusty
 */
public interface EventAggregator
{
    
    /**
     * Register a new object that holds handler methods with this
     * event-aggregator.
     * 
     * This is done globally. Any event of the requested kind will be transmitted to
     * the handler. For the context bound registration of handlers see 
     * registerHandlers(Object listener, Object context)
     * 
     * @param       handlerImpl             The object that holds different kind of handlers for various
     *                                      events. 
     *                                      These handler-methods should have <code>@EventListener</code>
     *                                      as an annotation, and follow the signature:
     *                                      * they have only one parameter which type implements Shardlet.Event and
     *                                      * return void
     */
    public void register(Object handlerImpl);
    
    
    /**
     * Register a new object that holds handler methods with this
     * event-aggregator.
     * 
     * This is done context specific: If you register an object that implements
     * handlers X, Y and Z, then these methods will only be invoked if an event concering
     * the given context is triggered. Given contexts will be compared using equals()
     * 
     * You can register for other contexts using this method mulitple times.
     * 
     * @param       handlerImpl             The object that holds different kind of handlers for various
     *                                      events. 
     *                                      These handler-methods should have <code>@EventListener</code>
     *                                      as an annotation, and follow the signature:
     *                                      * they have only one parameter which type implements Shardlet.Event and
     *                                      * return void
     * @param       context                 The object that is of interest to you. You will only recieve events that
     *                                      have been triggered using the optional 'context' argument, and you will only recieve
     *                                      those which have been triggered using the same context as the one given here.                                    
     */
    public void register(Object handlerImpl, Object context);
    
    
    /**
     * Unregister all handlers associated to that context
     * 
     * @param       context                 The context that some handlers may be interested in
     *                                      This does not have to be the object that actually
     *                                      implements the handlers! See unregisterByImpl for that behaviour.
     */
    public void unregisterByContext(Object context);
    
    
    /**
     * Unregister all handlers implemented within the given object
     * 
     * @param       handlerImpl             The object that holds different kind of handlers.
     */
    public void unregisterByImpl(Object handlerImpl);
    
    
    /**
     * Trigger an event globally; the EventAggregator will try to distribute it to all 
     * registered handler methods
     * 
     * @param       event                   The event that will be published on this event-aggregator
     */
    public void triggerEvent(Event event);
    
    
    /**
     * Trigger an event concering a given context object; the EventAggregator will 
     * try to distribute it to all registered handler methods that are interested in
     * that context object.
     * 
     * @param       event                   The event that will be published on this event-aggregator       
     * @param       context                 The context that the handlers should be interested in
     */
    public void triggerEvent(Event event, Object context);
}
