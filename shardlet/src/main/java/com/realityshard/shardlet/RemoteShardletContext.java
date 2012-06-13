/**
 * For copyright information see the LICENSE document.
 */

package com.realityshard.shardlet;


/**
 * Resembles a remote shardlet-context, so that one can send events etc. to it.
 * 
 * @author _rusty
 */
public interface RemoteShardletContext 
{
    
    /**
     * Sends a new shardlet-event-action (meaning an action that
     * can be triggered as an event ;D)
     * 
     * Use this method to trigger events directly within the remote context,
     * by providing an action that creates these events.
     * 
     * Think of the 'event-action' as an event-wrapper.
     * 
     * @param       action                  The event-action that will be used in the remote
     *                                      context to trigger the desired concrete event
     */
    public void sendRemoteEventAction(ShardletEventAction action);
}
