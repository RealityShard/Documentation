/**
 * For copyright information see the LICENSE document.
 */

package com.realityshard.shardlet.gameapp;

import com.realityshard.shardlet.ProtocolFilter;
import com.realityshard.shardlet.ConfigProtocolFilter;
import com.realityshard.shardlet.ShardletAction;
import java.io.IOException;
import org.slf4j.LoggerFactory;


/**
 * Does nothing, just for testing
 * 
 * @author _rusty
 */
public class ProtocolFilterTest implements ProtocolFilter
{

    @Override
    public void init(ConfigProtocolFilter filterConfig) 
            throws Exception 
    {
    }

    @Override
    public void doInFilter(ShardletAction action) 
            throws IOException
    {
        action.setAttribute("ProtocolFilter.test", "It's a me, tha ProtocolFilter attribute!");
    }

    @Override
    public void doOutFilter(ShardletAction action) 
            throws IOException
    {
        LoggerFactory.getLogger(ProtocolFilterTest.class).debug((String) action.getAttribute("ProtocolFilter.test"));
    }

}
