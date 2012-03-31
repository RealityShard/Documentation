/**
 * For copyright information see the LICENSE document.
 */

package com.gamerevision.rusty.realityshard.container;

import com.gamerevision.rusty.realityshard.schemas.ServerConfig;
import java.io.InputStream;
import javax.xml.bind.JAXBException;
import org.xml.sax.SAXException;


/**
 * This loads the server config file.
 * 
 * @author _rusty
 */
public class ConfigLoader
{
    
    /**
     * Tries to load the server configuration object
     * 
     * @param       configPath              Path to the server config XML file
     * @param       schemaLocation          Path to the schema file to validate the XML doc
     * @return      The parsed/validated/marshalled server config object
     * @throws      Exception               If anything went wrong with the XML doc.
     */
    public static ServerConfig getServerConfig(String configPath, String schemaLocation)
            throws Exception
    {
        // if this fails, the container may not be started!
        try 
        {
            // try to get the file
            InputStream stream = ConfigLoader.class.getResourceAsStream(configPath);
            
            // try to parse/validate/marshal it
            return JaxbUtils.validateAndUnmarshal(ServerConfig.class, stream, schemaLocation);
        } 
        catch (JAXBException | SAXException ex) 
        {
            throw new Exception("Config file could not be marshalled. Make sure it fitts the schema.", ex);
        }
    }
}
