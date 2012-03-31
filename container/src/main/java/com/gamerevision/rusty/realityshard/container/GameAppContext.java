/**
 * For copyright information see the LICENSE document.
 */

package com.gamerevision.rusty.realityshard.container;

import com.gamerevision.rusty.realityshard.shardlet.EventAggregator;
import com.gamerevision.rusty.realityshard.shardlet.ShardletContext;
import java.io.InputStream;
import java.util.Enumeration;


/**
 * Implementation of a ShardletContext
 * 
 * @author _rusty
 */
public class GameAppContext implements ShardletContext
{

    /**
     * 
     * @return 
     */
    @Override
    public String getServerInfo() 
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    /*package*/ void setServerInfo(String info)
    {
    }

    @Override
    public String getShardletContextName() 
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public EventAggregator getAggregator() 
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public InputStream getResourceAsStream(String path) 
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String getInitParameter(String name) 
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Enumeration<String> getInitParameterNames() 
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean setInitParameter(String name, String value) 
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Object getAttribute(String name) 
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Enumeration<String> getAttributeNames() 
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setAttribute(String name, Object object) 
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void removeAttribute(String name) 
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
