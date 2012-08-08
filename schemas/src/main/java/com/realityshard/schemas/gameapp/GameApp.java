//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.5-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2012.08.08 at 04:50:15 PM CEST 
//


package com.realityshard.schemas.gameapp;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="AppInfo" type="{}AppInfo"/>
 *         &lt;element name="Shardlet" type="{}ShardletConfig" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "appInfo",
    "shardlet"
})
@XmlRootElement(name = "GameApp")
public class GameApp {

    @XmlElement(name = "AppInfo", required = true)
    protected AppInfo appInfo;
    @XmlElement(name = "Shardlet", required = true)
    protected List<ShardletConfig> shardlet;

    /**
     * Gets the value of the appInfo property.
     * 
     * @return
     *     possible object is
     *     {@link AppInfo }
     *     
     */
    public AppInfo getAppInfo() {
        return appInfo;
    }

    /**
     * Sets the value of the appInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link AppInfo }
     *     
     */
    public void setAppInfo(AppInfo value) {
        this.appInfo = value;
    }

    /**
     * Gets the value of the shardlet property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the shardlet property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getShardlet().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ShardletConfig }
     * 
     * 
     */
    public List<ShardletConfig> getShardlet() {
        if (shardlet == null) {
            shardlet = new ArrayList<ShardletConfig>();
        }
        return this.shardlet;
    }

}
