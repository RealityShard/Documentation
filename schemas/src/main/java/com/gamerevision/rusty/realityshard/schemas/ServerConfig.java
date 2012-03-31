//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.5-b10 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2012.03.31 at 05:05:26 PM CEST 
//


package com.gamerevision.rusty.realityshard.schemas;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ServerConfig complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ServerConfig">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ServerName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Description" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="GameAppBasePath" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="OpenPorts" type="{}Ports"/>
 *         &lt;element name="DataBase" type="{}DataBase"/>
 *         &lt;element name="ClusterInfo" type="{}ClusterInfo"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ServerConfig", propOrder = {
    "serverName",
    "description",
    "gameAppBasePath",
    "openPorts",
    "dataBase",
    "clusterInfo"
})
public class ServerConfig {

    @XmlElement(name = "ServerName", required = true)
    protected String serverName;
    @XmlElement(name = "Description", required = true)
    protected String description;
    @XmlElement(name = "GameAppBasePath", required = true)
    protected String gameAppBasePath;
    @XmlElement(name = "OpenPorts", required = true)
    protected Ports openPorts;
    @XmlElement(name = "DataBase", required = true)
    protected DataBase dataBase;
    @XmlElement(name = "ClusterInfo", required = true)
    protected ClusterInfo clusterInfo;

    /**
     * Gets the value of the serverName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getServerName() {
        return serverName;
    }

    /**
     * Sets the value of the serverName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setServerName(String value) {
        this.serverName = value;
    }

    /**
     * Gets the value of the description property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the value of the description property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescription(String value) {
        this.description = value;
    }

    /**
     * Gets the value of the gameAppBasePath property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGameAppBasePath() {
        return gameAppBasePath;
    }

    /**
     * Sets the value of the gameAppBasePath property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGameAppBasePath(String value) {
        this.gameAppBasePath = value;
    }

    /**
     * Gets the value of the openPorts property.
     * 
     * @return
     *     possible object is
     *     {@link Ports }
     *     
     */
    public Ports getOpenPorts() {
        return openPorts;
    }

    /**
     * Sets the value of the openPorts property.
     * 
     * @param value
     *     allowed object is
     *     {@link Ports }
     *     
     */
    public void setOpenPorts(Ports value) {
        this.openPorts = value;
    }

    /**
     * Gets the value of the dataBase property.
     * 
     * @return
     *     possible object is
     *     {@link DataBase }
     *     
     */
    public DataBase getDataBase() {
        return dataBase;
    }

    /**
     * Sets the value of the dataBase property.
     * 
     * @param value
     *     allowed object is
     *     {@link DataBase }
     *     
     */
    public void setDataBase(DataBase value) {
        this.dataBase = value;
    }

    /**
     * Gets the value of the clusterInfo property.
     * 
     * @return
     *     possible object is
     *     {@link ClusterInfo }
     *     
     */
    public ClusterInfo getClusterInfo() {
        return clusterInfo;
    }

    /**
     * Sets the value of the clusterInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link ClusterInfo }
     *     
     */
    public void setClusterInfo(ClusterInfo value) {
        this.clusterInfo = value;
    }

}
