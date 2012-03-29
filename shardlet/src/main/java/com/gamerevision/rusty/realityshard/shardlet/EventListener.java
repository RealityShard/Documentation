/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gamerevision.rusty.realityshard.shardlet;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation will mark the listener methods that the aggregator will look for
 * @author felix
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface EventListener {}
