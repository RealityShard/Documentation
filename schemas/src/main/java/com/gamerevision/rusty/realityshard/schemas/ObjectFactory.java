//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.5-b10 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2012.03.31 at 05:05:26 PM CEST 
//


package com.gamerevision.rusty.realityshard.schemas;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.gamerevision.rusty.realityshard.schemas package. 
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

    private final static QName _GameApp_QNAME = new QName("", "GameApp");
    private final static QName _ServerConfig_QNAME = new QName("", "ServerConfig");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.gamerevision.rusty.realityshard.schemas
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GameApp }
     * 
     */
    public GameApp createGameApp() {
        return new GameApp();
    }

    /**
     * Create an instance of {@link ServerConfig }
     * 
     */
    public ServerConfig createServerConfig() {
        return new ServerConfig();
    }

    /**
     * Create an instance of {@link ClusterInfo }
     * 
     */
    public ClusterInfo createClusterInfo() {
        return new ClusterInfo();
    }

    /**
     * Create an instance of {@link Protocols }
     * 
     */
    public Protocols createProtocols() {
        return new Protocols();
    }

    /**
     * Create an instance of {@link DataBase }
     * 
     */
    public DataBase createDataBase() {
        return new DataBase();
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

    /**
     * Create an instance of {@link ProtocolFilter }
     * 
     */
    public ProtocolFilter createProtocolFilter() {
        return new ProtocolFilter();
    }

    /**
     * Create an instance of {@link Port }
     * 
     */
    public Port createPort() {
        return new Port();
    }

    /**
     * Create an instance of {@link Shardlet }
     * 
     */
    public Shardlet createShardlet() {
        return new Shardlet();
    }

    /**
     * Create an instance of {@link Shardlets }
     * 
     */
    public Shardlets createShardlets() {
        return new Shardlets();
    }

    /**
     * Create an instance of {@link Context }
     * 
     */
    public Context createContext() {
        return new Context();
    }

    /**
     * Create an instance of {@link InitParam }
     * 
     */
    public InitParam createInitParam() {
        return new InitParam();
    }

    /**
     * Create an instance of {@link Protocol }
     * 
     */
    public Protocol createProtocol() {
        return new Protocol();
    }

    /**
     * Create an instance of {@link Ports }
     * 
     */
    public Ports createPorts() {
        return new Ports();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GameApp }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "GameApp")
    public JAXBElement<GameApp> createGameApp(GameApp value) {
        return new JAXBElement<GameApp>(_GameApp_QNAME, GameApp.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ServerConfig }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "ServerConfig")
    public JAXBElement<ServerConfig> createServerConfig(ServerConfig value) {
        return new JAXBElement<ServerConfig>(_ServerConfig_QNAME, ServerConfig.class, null, value);
    }

}
