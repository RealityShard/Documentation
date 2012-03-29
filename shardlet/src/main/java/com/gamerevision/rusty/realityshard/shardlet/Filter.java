/**
 * Distributed under the GNU GPL v.3
 */

package com.gamerevision.rusty.realityshard.shardlet;

import java.io.IOException;


/**
 * A filter is a passive, stateless object that has some kind of filter functionality
 * it uses to transform a given action. E.g. used for encryption, data compression,
 * protocol specific parsing etc.
 * A filter is invoked directly by the container, based on the shardlet it precedes
 * filters are also used to implement different low-level protocol functionality.
 * These filters have to be added to the protocol definition within the deployment-descriptor.
 * 
 * Based on javax.servlet.Filter
 * Thx Oracle!
 * 
 * @author _rusty
 */
public interface Filter
{
    
    /**
     * Initialzes the filter passing a config object to it.
     * 
     * @param       filterConfig            The filter configuration object, created from
     *                                      the deployment-descriptor data
     * @throws      ShardletException       When a shardlet-internal problem occured
     */
    public void init(FilterConfig filterConfig) 
            throws ShardletException;
    
    /**
     * Filters an incoming action / network message
     * 
     * @param       action                  The action that will be filtered
     * @throws      IOException             The usual I/O stuff
     * @throws      ShardletException       When a shardlet-internal problem occured
     */
    public void doInFilter(ShardletAction action)
            throws IOException, ShardletException;
    
    /**
     * Filters an outgoing action / network message
     * 
     * @param       action                  The action that will be filtered
     * @throws      IOException             The usual I/O stuff
     * @throws      ShardletException       When a shardlet-internal problem occured
     */
    public void doOutFilter(ShardletAction action)
            throws IOException, ShardletException;
}
