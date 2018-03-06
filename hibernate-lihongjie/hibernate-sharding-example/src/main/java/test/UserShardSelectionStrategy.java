package test;

import org.hibernate.shards.ShardId;
import org.hibernate.shards.strategy.selection.ShardSelectionStrategy;

public class UserShardSelectionStrategy implements ShardSelectionStrategy {

    public ShardId selectShardIdForNewObject(Object obj) {
        if (obj instanceof User) {
            int shardId;
            String country = ((User) obj).getCountry();
            if (country.equalsIgnoreCase("India"))
                shardId = 0;
            else
                shardId = 1;
            return new ShardId(shardId);
        }
        throw new IllegalArgumentException();
    }
}
