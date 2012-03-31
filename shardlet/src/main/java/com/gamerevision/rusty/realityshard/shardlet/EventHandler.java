/**
 * For copyright information see the LICENSE document.
 */

package com.gamerevision.rusty.realityshard.shardlet;

import java.lang.annotation.*;


/**
 * This annotation will mark the listener methods that the aggregator will look for
 * @author _rusty
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Inherited
public @interface EventHandler {}
