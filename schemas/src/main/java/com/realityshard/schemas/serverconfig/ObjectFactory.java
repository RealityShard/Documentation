//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.5-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2012.08.08 at 04:50:15 PM CEST 
//


package com.realityshard.schemas.serverconfig;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.realityshard.schemas.serverconfig package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.realityshard.schemas.serverconfig
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ServerConfig }
     * 
     */
    public ServerConfig createServerConfig() {
        return new ServerConfig();
    }

    /**
     * Create an instance of {@link Ports }
     * 
     */
    public Ports createPorts() {
        return new Ports();
    }

    /**
     * Create an instance of {@link ClusterInfo }
     * 
     */
    public ClusterInfo createClusterInfo() {
        return new ClusterInfo();
    }

    /**
     * Create an instance of {@link Port }
     * 
     */
    public Port createPort() {
        return new Port();
    }

    /**
     * Create an instance of {@link ClusterServer }
     * 
     */
    public ClusterServer createClusterServer() {
        return new ClusterServer();
    }

    /**
     * Create an instance of {@link PortRange }
     * 
     */
    public PortRange createPortRange() {
        return new PortRange();
    }

}
