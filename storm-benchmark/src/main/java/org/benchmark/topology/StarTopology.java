package org.benchmark.topology;

import org.apache.storm.redis.bolt.RedisStoreBolt;
import org.apache.storm.redis.common.config.JedisPoolConfig;
import org.apache.storm.redis.common.mapper.RedisDataTypeDescription;
import org.apache.storm.redis.common.mapper.RedisStoreMapper;
import org.apache.storm.topology.ConfigurableTopology;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.tuple.ITuple;
import org.benchmark.bolt.SortNumberStringBolt;
import org.benchmark.spout.RandomNumberSentenceSpout;

public class StarTopology extends ConfigurableTopology {
    public static void main(String[] args) throws Exception {
        ConfigurableTopology.start(new StarTopology(), args);
    }

    @Override
    protected int run(String[] args) throws Exception {
        TopologyBuilder builder = new TopologyBuilder();
        JedisPoolConfig poolConfig1 = new JedisPoolConfig.Builder()
                .setHost("10.10.108.71").setPort(6379).setPassword("123456").build();
        RedisStoreMapper storeMapper1 = setupStoreMapper("star1");
        RedisStoreMapper storeMapper2 = setupStoreMapper("star2");
        RedisStoreMapper storeMapper3 = setupStoreMapper("star3");
        RedisStoreBolt storeBolt1 = new RedisStoreBolt(poolConfig1, storeMapper1);
        RedisStoreBolt storeBolt2 = new RedisStoreBolt(poolConfig1, storeMapper2);
        RedisStoreBolt storeBolt3 = new RedisStoreBolt(poolConfig1, storeMapper3);

        builder.setSpout("spout1", new RandomNumberSentenceSpout(100 , false), 5);
        builder.setSpout("spout2", new RandomNumberSentenceSpout(100 , false), 5);
        builder.setSpout("spout3", new RandomNumberSentenceSpout(100 , false), 5);
        builder.setBolt("sort", new SortNumberStringBolt(), 16).shuffleGrouping("spout1").shuffleGrouping("spout2").shuffleGrouping("spout3");
        builder.setBolt("store1", storeBolt1 ,10).shuffleGrouping("sort");
        builder.setBolt("store2", storeBolt2 ,10).shuffleGrouping("sort");
        builder.setBolt("store3", storeBolt3 ,10).shuffleGrouping("sort");

        conf.setDebug(true);

        String topologyName = "star-topology";

        conf.setNumWorkers(25);

        if (args != null && args.length > 0) {
            topologyName = args[0];
        }
        return submit(topologyName, conf, builder);
    }

    private static RedisStoreMapper setupStoreMapper(String keyName) {
        return new StarStoreMapper(keyName);
    }

    private static class StarStoreMapper implements RedisStoreMapper {
        private String keyName;
        private RedisDataTypeDescription description;

        public StarStoreMapper(String keyName) {
            description = new RedisDataTypeDescription(RedisDataTypeDescription.RedisDataType.LIST);
            this.keyName = keyName;
        }

        @Override
        public RedisDataTypeDescription getDataTypeDescription() {
            return description;
        }

        @Override
        public String getKeyFromTuple(ITuple tuple) {
            return this.keyName;
        }
        @Override
        public String getValueFromTuple(ITuple tuple) {
            return tuple.getStringByField("message");
        }
    }
}
