/**
 * For copyright information see the LICENSE document.
 */

package com.realityshard.entitysystem;

import java.util.UUID;


/**
 * Represents an entity (by its ID)
 * This interface is used for convienience. The component manager
 * uses it's ID to access its components.
 * 
 * This will not carry any references to its actual components for now. (Sep.2012)
 * 
 * @author _rusty
 */
public interface Entity
{
    
    /**
     * Getter.
     * 
     * @return      The unique ID of this entity. 
     */
    public UUID getUuid();
    
    
    /**
     * Performance:
     * Use the UUID as an identifier.
     * 
     * @return      The hash code of the uuid.
     */
    @Override
    public int hashCode();
    
    
    /**
     * Performance:
     * Use the UUID as an identifier.
     * 
     * @return      True if the UUID's hash code equals that of the given object.
     */
    @Override
    public boolean equals(Object obj);
}
