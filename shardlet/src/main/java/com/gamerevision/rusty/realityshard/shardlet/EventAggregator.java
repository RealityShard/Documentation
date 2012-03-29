/**
 * Distributed under the GNU GPL v.3
 * Google it if you want to have a copy of the license.
 */


package com.gamerevision.rusty.realityshard.shardlet;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


/**
 * An EventAggregator is the EventAggregator-pattern class implemented by the container
 * that will manage the events coming from or triggering the shardlets.
 * 
 * TODO: Check if the generics work they way they should.
 * 
 * @author _rusty
 */
public interface EventAggregator
{
    
    /**
     * Register a new listener with the event->listeners associations.
     * 
     * @param       listener                The listener that will be triggered by various events. 
     *                                      (Depends on its listeners, these methods should have <code>@EventListener</code>
     *                                      as an annotation, and follow the signature:
     *                                      only one parameter that class implements Shardlet.Event)
     */
    public void addListener(Object listener);
    
    
    /**
     * Trigger an event, the EventAggregator will try to distribute it to all 
     * registered listeners
     * 
     * Note that the generic type parameter is necessary in order to
     * be able to call the listener later on.
     * 
     * @exception   InvocationTargetExpection
     *                                      This Exception is thrown when an exception
     *                                      was thrown from within a handler.
     */
    public void triggerEvent(Event event)
            throws InvocationTargetException;
}
