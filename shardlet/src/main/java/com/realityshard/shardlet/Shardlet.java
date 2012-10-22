/**
 * For copyright information see the LICENSE document.
 */

package com.realityshard.shardlet;


/**
 * This is taken from the javax.servlet specification, 
 * so go take a look how the API works.
 * 
 * Thx Oracle!
 * 
 * @author _rusty
 */
public interface Shardlet
{

   /**
    * Getter.
    * 
    * @return       The shardlet config object given to this shardlet at initialization.
    */
   public ConfigShardlet getShardletConfig();
   
   
   /**
    * Getter.
    * 
    * @return       The shardlet-description.
    */
   public String getShardletInfo();
   
    
    /**
     * Called by the container when the shardlet is loaded.
     * 
     * @param       config                  The config object, a serialized part of the DD
     *                                      concerning this Shardlet.
     * @exception   Exception               May be thrown if anything didn't work right during
     *                                      initialization.
     */
    public void init(ConfigShardlet config)
            throws Exception;
    
    
    /**
     * Called by the container when the shardlet is destroyed/disposed
     */
    public void destroy();
    
    
    /**
     * Called by the development-environment, when not using dynamic
     * Shardlet creation. (Meaning when using hardcoded classes instead of
     * dynamically loaded java class files, within the context of the
     * development environment)
     * 
     * @return      A clone of this shardlet. The clone does not need to be
     *              initialized. You can expect the container to call init() on
     *              the clone again.
     */
    public Shardlet clone();
}
