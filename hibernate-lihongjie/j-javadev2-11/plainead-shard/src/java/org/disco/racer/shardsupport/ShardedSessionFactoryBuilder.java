package org.disco.racer.shardsupport;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.shards.ShardId;
import org.hibernate.shards.ShardedConfiguration;
import org.hibernate.shards.cfg.ConfigurationToShardConfigurationAdapter;
import org.hibernate.shards.cfg.ShardConfiguration;
import org.hibernate.shards.strategy.ShardStrategy;
import org.hibernate.shards.strategy.ShardStrategyFactory;
import org.hibernate.shards.strategy.ShardStrategyImpl;
import org.hibernate.shards.strategy.access.SequentialShardAccessStrategy;
import org.hibernate.shards.strategy.access.ShardAccessStrategy;
import org.hibernate.shards.strategy.resolution.AllShardsShardResolutionStrategy;
import org.hibernate.shards.strategy.resolution.ShardResolutionStrategy;
import org.hibernate.shards.strategy.selection.ShardSelectionStrategy;

import java.util.ArrayList;
import java.util.List;

public class ShardedSessionFactoryBuilder {

    private List<String> hibernateConfigurations;
    private List<String> resourceConfigurations;

    public ShardedSessionFactoryBuilder() {
    }

    /**
     *
     */
    public void setHibernateConfigurations(List<String> hibernateConfigurations) {
        this.hibernateConfigurations = hibernateConfigurations;
    }

    /**
     *
     */
    public void setResourceConfigurations(List<String> resourceConfigurations) {
        this.resourceConfigurations = resourceConfigurations;
    }

    /**
     *
     */
    public SessionFactory createSessionFactory() {
        Configuration prototypeConfig =
                this.getPrototypeConfig(this.hibernateConfigurations.get(0),
                        this.resourceConfigurations);

        List<ShardConfiguration> shardConfigs = new ArrayList<ShardConfiguration>();
        for (String hibconfig : this.hibernateConfigurations) {
            shardConfigs.add(buildShardConfig(hibconfig));
        }


        ShardStrategyFactory shardStrategyFactory = buildShardStrategyFactory();
        ShardedConfiguration shardedConfig = new ShardedConfiguration(
                prototypeConfig,
                shardConfigs,
                shardStrategyFactory);
        return shardedConfig.buildShardedSessionFactory();
    }

    /**
     *
     */
    private Configuration getPrototypeConfig(String hibernateFile, List<String> resourceFiles) {
        Configuration prototypeConfig = this.getConfiguration(hibernateFile);
        for (String res : resourceFiles) {
            prototypeConfig.addResource(res);
        }
        return prototypeConfig;
    }

    /**
     *
     */
    private Configuration getConfiguration(String file) {
        return new Configuration().configure(file);
    }

    /**
     *
     */
    private ShardStrategyFactory buildShardStrategyFactory() {
        ShardStrategyFactory shardStrategyFactory = new ShardStrategyFactory() {
            public ShardStrategy newShardStrategy(List<ShardId> shardIds) {
                ShardSelectionStrategy pss = new RacerShardSelectionStrategy();
                ShardResolutionStrategy prs = new AllShardsShardResolutionStrategy(shardIds);
                ShardAccessStrategy pas = new SequentialShardAccessStrategy();
                return new ShardStrategyImpl(pss, prs, pas);
            }
        };
        return shardStrategyFactory;
    }

    /**
     *
     */
    private ShardConfiguration buildShardConfig(String configFile) {
        Configuration config = new Configuration().configure(configFile);
        return new ConfigurationToShardConfigurationAdapter(config);
    }


}
