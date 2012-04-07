/**
 * For copyright information see the LICENSE document.
 */

package com.realityshard.shardlet.gameapp;

import com.realityshard.shardlet.ConfigProtocolFilter;
import com.realityshard.shardlet.ProtocolFilter;
import com.realityshard.shardlet.ShardletAction;
import java.io.IOException;
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
    public void doInFilter(ShardletAction action) 
            throws IOException
    {
        action.setAttribute("ProtocolFilter.test", "It's a me, tha ProtocolFilter attribute!");
        
        LOGGER.debug("In filter method called!");
    }

    @Override
    public void doOutFilter(ShardletAction action) 
            throws IOException
    {
        LOGGER.debug("Out filter method called!");
        LOGGER.debug((String) action.getAttribute("ProtocolFilter.test"));
    }

}
