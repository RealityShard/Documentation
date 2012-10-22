/**
 * For copyright information see the LICENSE document.
 */

package com.realityshard.container;

import com.realityshard.container.gameapp.GameAppFactory;
import com.realityshard.shardlet.ProtocolFilter;
import java.util.List;


/**
 * This class will be used to load up all the components (Protocols, Game-Apps)
 * statically, instead of the way its done in the production environment,
 * meaning by statically loading them instead of dynamically loading them.
 * 
 * @author _rusty
 */
public interface DevelopmentEnvironment 
{
    
    /**
     * This class acts as a dataholder for ONLY ONE PROTOCOL!
     */
    public static class ProtocolDataContainer
    {
        private final String name;
        private final int port;
        private final List<ProtocolFilter> inFilters;
        private final List<ProtocolFilter> outfilters;
        
        
        /**
         * Constructor.
         * 
         * @param   name                    Name of the protocol.
         * @param   port                    The port that this protocol runs on.
         * @param   inFilters               All in-filter references (Must be
         *                                  already initialized)
         * @param   outfilters              All out-filter references (Must be
         *                                  already initialized)
         */
        public ProtocolDataContainer(
                String name,
                int port,
                List<ProtocolFilter> inFilters, 
                List<ProtocolFilter> outfilters)
        {
            this.name = name;
            this.port = port;
            this.inFilters = inFilters;
            this.outfilters = outfilters;
        }

        public String getName() {
            return name;
        }

        public int getPort() {
            return port;
        }

        public List<ProtocolFilter> getInFilters() {
            return inFilters;
        }

        public List<ProtocolFilter> getOutfilters() {
            return outfilters;
        }
    }
    
    
    /**
     * This method should return ALL game-app-factories that you want to use
     * within reality-shard.
     * 
     * @return      The game-app-factories that this instance of a container should be able to use.
     */
    public GameAppFactory[] getGameAppFactories();
    
    
    /**
     * This method should return ALL protocols that you want to use
     * within reality-shard.
     * 
     * @return      The data-holders for protocols (these are used to create protocol
     *              chains and register them with the network adapter)
     */
    public ProtocolDataContainer[] getProtocolDataContainers();
}
