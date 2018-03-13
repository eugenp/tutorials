package test;

import org.hibernate.shards.ShardId;
import org.hibernate.shards.strategy.resolution.AllShardsShardResolutionStrategy;
import org.hibernate.shards.strategy.selection.ShardResolutionStrategyData;

import java.util.List;

public class UserShardResolutionStrategy extends AllShardsShardResolutionStrategy {

    public UserShardResolutionStrategy(List<ShardId> shardIds) {
        super(shardIds);
    }

    public List<ShardId> selectShardIdsFromShardResolutionStrategyData(ShardResolutionStrategyData srsd) {
        return super.selectShardIdsFromShardResolutionStrategyData(srsd);
    }
}
