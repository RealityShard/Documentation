/**
 * For copyright information see the LICENSE document.
 */

package com.realityshard.shardlet;


/**
 * Defines the transitions between encryption states.
 * Actually, this does not define real transitions, because
 * an encryption state may be introduced by e.g. a protocol filter,
 * not by the state object itself.
 * 
 * This could maybe be called delegation pattern as well, because
 * the session delegates encryption calls to the inner encryption state object
 * that implements this interface.
 * 
 * @author _rusty
 */
public interface SessionEncryptionState 
{
    /**
     * Encrypts an action.
     * A concrete state should define how the session should act when
     * this method is called.
     * 
     * @param       action                  The action that's gonna be encrypted
     */
    public void encrypt(ShardletAction action);
    
    
    /**
     * Decrypts an action.
     * A concrete state should define how the session should act when
     * this method is called.
     * 
     * @param       action                  The action that's gonna be decrypted
     */
    public void decrypt(ShardletAction action);

    
    /**
     * A default implementation of the encryption state.
     * This simply does nothing with provided actions.
     * (Meaning there's nothing to en/decrypt here)
     */
    public final class Unencrypted implements SessionEncryptionState
    {

        /**
         * Do nothing
         * 
         * @param       action 
         */
        @Override
        public void encrypt(ShardletAction action) {}

        
        /**
         * Do nothing
         * 
         * @param       action 
         */
        @Override
        public void decrypt(ShardletAction action) {}
        
    }
}
