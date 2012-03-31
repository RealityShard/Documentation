/**
 * For copyright information see the LICENSE document.
 */

package com.gamerevision.rusty.realityshard.container;

import com.gamerevision.rusty.realityshard.schemas.GameApp;
import java.io.InputStream;
import java.util.concurrent.Executor;
import javax.xml.bind.JAXBException;
import org.xml.sax.SAXException;


/**
 * This loads a game-apps deployment descriptor.
 * 
 * @author _rusty
 */
public class ContextLoader 
{
    
    /**
     * Tries to load the specified game app deployment descriptor object
     * 
     * @param       deplDescPath            Path to the server config XML file
     * @param       schemaLocation          Path to the schema file to validate the XML doc
     * @return      The parsed/validated/marshalled server config object
     * @throws      Exception               If anything went wrong with the XML doc.
     */
    public static GameApp getGameAppDeploymentDescriptor(String deplDescPath, String schemaLocation)
            throws Exception
    {
        // if this fails, the game app may not be started!
        try 
        {
            // try to get the file
            InputStream stream = ConfigLoader.class.getResourceAsStream(deplDescPath);
            
            // try to parse/validate/marshal it
            return JaxbUtils.validateAndUnmarshal(GameApp.class, stream, schemaLocation);
        } 
        catch (JAXBException | SAXException ex) 
        {
            throw new Exception("GameApp deployment descriptor could not be marshalled. Make sure it fitts the schema.", ex);
        }
    }
    
    
    /**
     * Tries to create a new game app from the given deployment descriptor
     * 
     * @param       app                     The deployment descriptor object of the GameApp
     * @param       executor                The default executor used for the event aggregator
     *                                      that is created within the context.
     * @param       additionalContextParams Any additional params that will be loaded into
     *                                      the context as InitParams
     * @return      The new game app instance as a game app context.
     */
    public static GameAppContext loadGameApp(GameApp app, Executor executor, String[] additionalContextParams)
    {
        // TODO implement me!
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
