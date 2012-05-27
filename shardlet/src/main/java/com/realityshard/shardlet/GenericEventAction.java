/**
 * For copyright information see the LICENSE document.
 */

package com.realityshard.shardlet;


/**
 * This is a reference implementation of ShardletEventAction, that you can use
 * (meaning you can inherit from it) to reduce the code within your 
 * implementation of that interface.
 * 
 * It has the same abilities as GenericAction (direct inheritance)
 * 
 * This class is abstract, because you should never use it directly!
 * (There is no 'general' message that needs to have the ability of being triggerable
 * on an event aggregator)
 * 
 * @author _rusty
 */
public abstract class GenericEventAction extends GenericAction implements ShardletEventAction
{
    
    /**
     * You will need to override this method in your implementation.
     * 
     * @param       aggregator              The EventAggregator that you want an
     *                                      this action to be published on
     */
    @Override
    public abstract void triggerEvent(EventAggregator aggregator);
}
