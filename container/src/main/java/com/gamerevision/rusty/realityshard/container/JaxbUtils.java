/**
 * Distributed under the GNU GPL v.3
 */

package com.gamerevision.rusty.realityshard.container;

import java.io.InputStream;
import java.net.URL;
import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import org.xml.sax.SAXException;


/**
 * Used to handle XML files with the JAXB framework
 * 
 * @author _rusty
 */
public class JaxbUtils 
{
    /**
     * Tries to read an XML input stream and validate and marshal it.
     * Returns a valid object of type T if succesful.
     * 
     * @param       <T>                     The type that will be validated and marshalled
     * @param       docClass                The class that represents the type T
     * @param       inputStream             The raw document input stream
     * @param       schemaLocation          The path to the schema file for validation
     * @return      An object of type T filled with the content from the
     *              XML document (if everything went right that is)
     * @throws      JAXBException           If the JAXB framework threw an error
     * @throws      SAXException            If the SAX framework threw an error
     */
    public static <T> T validateAndUnmarshal(Class<T> docClass, InputStream inputStream, String schemaLocation)
            throws JAXBException, SAXException 
    {
        // get the schema to validate the inputStream later on
        SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Schema schema = sf.newSchema(JaxbUtils.class.getResource(schemaLocation));
        
        // create the unmarshaller that will parse/validate/marshal the inputStream
        String packageName = docClass.getPackage().getName();
        Unmarshaller unmarshaller = JAXBContext.newInstance(packageName).createUnmarshaller();
        
        // set the schema that the unmarshaller will use
        unmarshaller.setSchema(schema);
        
        // unmarshal and return the type
        return (T) unmarshaller.unmarshal(inputStream);
    }
}
