/**
 * For copyright information see the LICENSE document.
 */

package com.realityshard.shardlet;


/**
 * This interface is used with classes that are both an action and an event
 * (Meaning they resemble a packet or message from the client, but they are
 * also distributeable by the aggregator)
 * 
 * Use this with actions that are coming from a client. (incoming)
 * You may want to use ShardletAction instead of this interface for actions
 * that are to be send to a client (outgoing)
 * 
 * @author _rusty
 */
public interface TriggerableAction extends Event, Action
{
    
    /**
     * This method will try to use the given aggregator, to
     * distribute the implementing class on it.
     * 
     * Example:
     * Your class is called
     *     public final ClientExitAction implements ShardletEventAction
     * That means it may be an action from the client which essentially
     * tells us that the client want to terminate its connection.
     * Now when you got an instance of that class and call this method with
     * any aggregator that you have access to, that aggragtor will try to invoke
     * any EventHandlers that handle the class "ClientExitAction"
     * 
     * @param       aggregator              The EventAggregator that you want an
     *                                      this action to be published on
     *                                      (As this action is also an event, this is
     *                                      no problem for the aggregator)
     */
    public void triggerEvent(EventAggregator aggregator);
}
