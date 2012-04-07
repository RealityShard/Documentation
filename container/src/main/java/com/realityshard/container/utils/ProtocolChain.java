/**
 * For copyright information see the LICENSE document.
 */

package com.realityshard.container.utils;

import com.realityshard.shardlet.ConfigProtocolFilter;
import com.realityshard.shardlet.ProtocolFilter;
import com.realityshard.shardlet.ShardletAction;
import java.io.IOException;
import java.util.List;


/**
 * This class holds a list of ProtocolFilters and executes the
 * chain if it recieves a doIn/OutFilter request
 * 
 * @author _rusty
 */
public class ProtocolChain implements ProtocolFilter
{
    
    private final List<ProtocolFilter> incomingFilters;
    private final List<ProtocolFilter> outgoingFilters;
    
    
    /**
     * Constructor.
     * 
     * Note that filters given to this Constructor should have been
     * initialized by calling init() on them before using this.
     * 
     * @param       incomingFilters         A list of ProtocolFilters for Incoming Actions (Packets)
     * @param       outgoingFilters         A list of ProtocolFilters for Outgoing Actions (Packets)
     */
    public ProtocolChain(List<ProtocolFilter> incomingFilters, List<ProtocolFilter> outgoingFilters)
    {
        this.incomingFilters = incomingFilters;
        this.outgoingFilters = outgoingFilters;
    }

    
    /**
     * Do nothing!
     * This WONT initialize the protocol filters!
     * We don't have sufficient information to do this,
     * so it should have been done before creating a ProtocolChain object.
     * 
     * @param       filterConfig            Do nothing!
     * @throws      Exception       Do nothing!
     */
    @Override
    public void init(ConfigProtocolFilter filterConfig) 
            throws Exception 
    {
        // do nothing here, all filters should have been initialized before
    }

    
    /**
     * Executes the filter chain by calling the doInFilter() method
     * on each Filter.
     * 
     * @param       action                  The action (aka Packet) that needs
     *                                      to be processed.
     * @throws      IOException             If any filter threw it.
     */
    @Override
    public void doInFilter(ShardletAction action) 
            throws IOException
    {
        for (ProtocolFilter protocolFilter : incomingFilters) 
        {
            // the action will be transformed by the filter
            protocolFilter.doOutFilter(action);
        }
    }

    
    /**
     * Executes the filter chain by calling the doOutFilter() method
     * on each Filter.
     * 
     * @param       action                  The action (aka Packet) that needs
     *                                      to be processed.
     * @throws      IOException             If any filter threw it.
     */
    @Override
    public void doOutFilter(ShardletAction action) 
            throws IOException
    {
        for (ProtocolFilter protocolFilter : outgoingFilters) 
        {
            // the action will be transformed by the filter
            protocolFilter.doOutFilter(action);
        }
    }

}
