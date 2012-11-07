//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.5-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2012.11.07 at 02:47:47 PM CET 
//


package com.realityshard.schemas.serverconfig;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Ports complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Ports">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Port" type="{}Port" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="PortRange" type="{}PortRange" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="cluster" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" default="6211" />
 *       &lt;attribute name="rmi" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" default="6212" />
 *       &lt;attribute name="maintenance" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" default="6213" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Ports", propOrder = {
    "port",
    "portRange"
})
public class Ports {

    @XmlElement(name = "Port")
    protected List<Port> port;
    @XmlElement(name = "PortRange")
    protected List<PortRange> portRange;
    @XmlAttribute(name = "cluster")
    @XmlSchemaType(name = "unsignedInt")
    protected Long cluster;
    @XmlAttribute(name = "rmi")
    @XmlSchemaType(name = "unsignedInt")
    protected Long rmi;
    @XmlAttribute(name = "maintenance")
    @XmlSchemaType(name = "unsignedInt")
    protected Long maintenance;

    /**
     * Gets the value of the port property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the port property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPort().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Port }
     * 
     * 
     */
    public List<Port> getPort() {
        if (port == null) {
            port = new ArrayList<Port>();
        }
        return this.port;
    }

    /**
     * Gets the value of the portRange property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the portRange property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPortRange().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PortRange }
     * 
     * 
     */
    public List<PortRange> getPortRange() {
        if (portRange == null) {
            portRange = new ArrayList<PortRange>();
        }
        return this.portRange;
    }

    /**
     * Gets the value of the cluster property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public long getCluster() {
        if (cluster == null) {
            return  6211L;
        } else {
            return cluster;
        }
    }

    /**
     * Sets the value of the cluster property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setCluster(Long value) {
        this.cluster = value;
    }

    /**
     * Gets the value of the rmi property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public long getRmi() {
        if (rmi == null) {
            return  6212L;
        } else {
            return rmi;
        }
    }

    /**
     * Sets the value of the rmi property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setRmi(Long value) {
        this.rmi = value;
    }

    /**
     * Gets the value of the maintenance property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public long getMaintenance() {
        if (maintenance == null) {
            return  6213L;
        } else {
            return maintenance;
        }
    }

    /**
     * Sets the value of the maintenance property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setMaintenance(Long value) {
        this.maintenance = value;
    }

}
