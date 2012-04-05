/**
 * For copyright information see the LICENSE document.
 */

package com.realityshard.schemas;

import com.realityshard.schemas.ObjectFactory;
import com.realityshard.schemas.Start;
import com.realityshard.schemas.GameApp;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import org.junit.Test;


/**
 *
 * @author _rusty
 */
public class GameAppTest 
{
   
    @Test
    public void marshall() throws JAXBException, FileNotFoundException, IOException 
    {
        ObjectFactory of = new ObjectFactory();
        
        GameApp appConfig = of.createGameApp();
        
        appConfig.setDisplayName("TestGameApp");
        appConfig.setDescription("This is used as a test only.");
        appConfig.setStartup(Start.WHEN_REQUESTED);
        appConfig.setHeartBeat((long) 50);


        String packageName = appConfig.getClass().getPackage().getName();
        JAXBContext jc = JAXBContext.newInstance(packageName);
        Marshaller m = jc.createMarshaller();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        m.marshal(appConfig, baos);
        
//        try (FileOutputStream file = new FileOutputStream("test.xml")) 
//        {
//            file.write(baos.toByteArray());
//        }

        //String resultingXml = baos.toString();
        //assertEquals(expectedOutput, resultingXml);
    }
}
