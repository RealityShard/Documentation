/**
 * For copyright information see the LICENSE document.
 */

package com.gamerevision.rusty.realityshard.schemas;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import org.junit.Test;


/**
 *
 * @author _rusty
 */
public class ServerConfigTest 
{
    @Test
    public void marshall() throws JAXBException, FileNotFoundException, IOException
    {
        ObjectFactory of = new ObjectFactory();
        
        ServerConfig srvConfig = of.createServerConfig();
        
        srvConfig.setServerName("TestServer");
        srvConfig.setDescription("A test server configuration");
        srvConfig.setVersion("0.1.0 RC1 pre-Alpha");
        
        Ports ports = of.createPorts();
        ports.setCluster((long) 6111);
        ports.setRmi((long) 6112);
        ports.setSsh((long) 6113);
        ports.setMaintenance((long) 6114);
        srvConfig.setOpenPorts(ports);
        
        
        ClusterInfo cluster = of.createClusterInfo();
        srvConfig.setClusterInfo(cluster);


        String packageName = srvConfig.getClass().getPackage().getName();
        JAXBContext jc = JAXBContext.newInstance(packageName);
        Marshaller m = jc.createMarshaller();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        m.marshal(srvConfig, baos);
        
//        try (FileOutputStream file = new FileOutputStream("server-config.xml")) 
//        {
//            file.write(baos.toByteArray());
//        }

        //String resultingXml = baos.toString();
        //assertEquals(expectedOutput, resultingXml);
    }
}
