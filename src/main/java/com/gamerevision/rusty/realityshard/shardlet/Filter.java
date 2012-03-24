/**
 * 
 */
package com.gamerevision.rusty.realityshard.shardlet;

import java.io.IOException;


/**
 * @author _rusty
 *
 */
public interface Filter
{
    
    //public void init(FilterConfig filterConfig) 
            //throws ShardletException;
    
    public void doInFilter(ShardletAction action)
            throws IOException, ShardletException;
    
    public void doOutFilter(ShardletAction action)
            throws IOException, ShardletException;
}
