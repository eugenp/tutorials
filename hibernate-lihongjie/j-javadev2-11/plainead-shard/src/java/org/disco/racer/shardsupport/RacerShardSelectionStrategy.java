package org.disco.racer.shardsupport;

import org.disco.racer.domain.Race;
import org.disco.racer.domain.Runner;
import org.hibernate.shards.ShardId;
import org.hibernate.shards.strategy.selection.ShardSelectionStrategy;

public class RacerShardSelectionStrategy implements ShardSelectionStrategy {
    /**
     *
     */
    public ShardId selectShardIdForNewObject(Object obj) {
        if (obj instanceof Race) {
            Race rce = (Race) obj;
            return this.determineShardId(rce.getDistance());
        } else if (obj instanceof Runner) {
            Runner runnr = (Runner) obj;
            if (runnr.getRaces().isEmpty()) {
                throw new IllegalArgumentException("runners must have at least one race");
            } else {
                double dist = 0.0;
                for (Race rce : runnr.getRaces()) {
                    dist = rce.getDistance();
                    break;
                }
                return this.determineShardId(dist);
            }
        } else {
            throw new IllegalArgumentException("Woah, something went wrong");
        }
    }

    /**
     *
     */
    private ShardId determineShardId(double distance){
        if (distance > 10.0) {
                return new ShardId(1);
            } else {
                return new ShardId(0);
            }
    }
}
