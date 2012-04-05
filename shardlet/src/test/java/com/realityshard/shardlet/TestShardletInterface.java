/**
 * Distributed under the GNU GPL v.3
 */

package com.realityshard.shardlet;

import com.realityshard.shardlet.Shardlet;
import com.realityshard.shardlet.EventAggregator;


/**
 *
 * @author _rusty
 */
public interface TestShardletInterface extends Shardlet
{
    public void setAggregator(EventAggregator aggregator);
}
