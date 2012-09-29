/**
 * For copyright information see the LICENSE document.
 */

package com.realityshard.entitysystem;

import java.util.UUID;


/**
 * Implementation of the Entity interface.
 * 
 * @author _rusty
 */
public class GenericEntity implements Entity
{
    
    private final UUID uuid;
    
    
    /**
     * Constructor.
     * 
     * @param       uuid                    The unique ID of this entity.
     * @param       name                    The name of the entity-type 
     *                                      (e.g. "Player" or "Mob" or "Signpost" etc.)
     */
    public GenericEntity(UUID uuid)
    {
        this.uuid = uuid;
    }

    
    /**
     * Getter.
     * 
     * @return      The Entity-ID of this Entity.
     */
    @Override
    public UUID getUuid() 
    {
        return uuid;
    }
    
    
    /**
     * Performance:
     * Use the UUID as an identifier.
     *  
     * @return      The hash code of the uuid.
     */
    @Override
    public int hashCode()
    {
        return this.uuid.hashCode();
    }

    
    /**
     * Performance:
     * Use the UUID as an identifier.
     * 
     * @return      True if the UUID's equals(obj) returned true.
     */
    @Override
    public boolean equals(Object obj) 
    {
        // auto-generated comparison stuff...
        
        if (obj == null) 
        {
            return false;
        }
        
        if (getClass() != obj.getClass()) 
        {
            return false;
        }
        
        final GenericEntity other = (GenericEntity) obj;
        
        if (this.uuid != other.uuid && (this.uuid == null || !this.uuid.equals(other.uuid))) 
        {
            return false;
        }
        
        return true;
    }
}
