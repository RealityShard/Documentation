/**
 * For copyright information see the LICENSE document.
 */

package com.realityshard.shardlet.gameapp;

import com.realityshard.shardlet.ConfigProtocolFilter;
import com.realityshard.shardlet.ProtocolFilter;
import com.realityshard.shardlet.ShardletAction;
import com.realityshard.shardlet.ShardletEventAction;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Does nothing, just for testing
 * 
 * @author _rusty
 */
public class ProtocolFilterTest implements ProtocolFilter
{
    private final static Logger LOGGER = LoggerFactory.getLogger(ProtocolFilterTest.class);

    @Override
    public void init(ConfigProtocolFilter filterConfig) 
            throws Exception 
    {
        LOGGER.debug("Filter initialized!");
    }

    @Override
    public List<ShardletEventAction> doInFilter(ShardletEventAction action) 
            throws IOException
    {
        //action.setAttribute("ProtocolFilter.test", "It's a me, tha ProtocolFilter attribute!");
        
        LOGGER.debug("In filter method called!");
        
        return Arrays.asList(action);
    }

    @Override
    public ShardletAction doOutFilter(ShardletAction action) 
            throws IOException
    {
        LOGGER.debug("Out filter method called!");
        //LOGGER.debug("Found attribute: " + (String) action.getAttribute("ProtocolFilter.test"));
        
        return action;
    }

}
