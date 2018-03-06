package test;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.shards.ShardId;
import org.hibernate.shards.ShardedConfiguration;
import org.hibernate.shards.cfg.ConfigurationToShardConfigurationAdapter;
import org.hibernate.shards.strategy.ShardStrategy;
import org.hibernate.shards.strategy.ShardStrategyFactory;
import org.hibernate.shards.strategy.ShardStrategyImpl;
import org.hibernate.shards.strategy.access.SequentialShardAccessStrategy;
import org.hibernate.shards.strategy.access.ShardAccessStrategy;
import org.hibernate.shards.strategy.resolution.ShardResolutionStrategy;
import org.hibernate.shards.strategy.selection.ShardSelectionStrategy;

import java.util.ArrayList;
import java.util.List;

public class HibernateShardUtil {

    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    static {
        try {
            Configuration config = new Configuration();
            config.configure("hibernate.cfg.xml");
            config.addResource("User.hbm.xml");
            List shardConfigs = new ArrayList();
            shardConfigs.add(new ConfigurationToShardConfigurationAdapter(new Configuration().configure("hibernate0.cfg.xml")));
            shardConfigs.add(new ConfigurationToShardConfigurationAdapter(new Configuration().configure("hibernate1.cfg.xml")));
            ShardStrategyFactory shardStrategyFactory = buildShardStrategyFactory();
            ShardedConfiguration shardedConfig = new ShardedConfiguration(config, shardConfigs, shardStrategyFactory);
            sessionFactory = shardedConfig.buildShardedSessionFactory();
        } catch (Throwable ex) {
            ex.printStackTrace();
            sessionFactory = null;
        }
    }

    static ShardStrategyFactory buildShardStrategyFactory() {
        ShardStrategyFactory shardStrategyFactory = new ShardStrategyFactory() {
            public ShardStrategy newShardStrategy(List<ShardId> shardIds) {
                ShardSelectionStrategy pss = new UserShardSelectionStrategy();
                ShardResolutionStrategy prs = new UserShardResolutionStrategy(shardIds);
                ShardAccessStrategy pas = new SequentialShardAccessStrategy();
                return new ShardStrategyImpl(pss, prs, pas);
            }
        };
        return shardStrategyFactory;
    }
}