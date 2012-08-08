/**
 * For copyright information see the LICENSE document.
 */

package com.realityshard.shardlet;

import java.lang.annotation.*;


/**
 * This annotation will mark the handler methods that the event-aggregator will look for
 * @author _rusty
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Inherited
public @interface EventHandler 
{
    
    /**
     * This annotation parameter can be used to force registration of a handler
     * without a special context.
     * E.g. Imagine you have a class that implements several handlers X, Y and Z,
     * and the Z handler has <code>@EventHandler(forceGlobal = false)</code>.
     * Now you want to register these with a special context Object obj.
     * The event-aggregator will only associate X and Y with obj, and Z will be regarded as
     * a handler that is not context bound.
     * 
     * However, if you register handlers explicitly without a context object, and their
     * annotation says 'global = false' then that is simply ignored. Thats why global
     * is false by default.
     */
    boolean forceGlobal() default false;
}
