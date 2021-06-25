package org.benchmark.topology;

import org.apache.storm.redis.bolt.RedisStoreBolt;
import org.apache.storm.redis.common.config.JedisPoolConfig;
import org.apache.storm.redis.common.mapper.RedisDataTypeDescription;
import org.apache.storm.redis.common.mapper.RedisStoreMapper;
import org.apache.storm.topology.ConfigurableTopology;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.tuple.ITuple;
import org.benchmark.bolt.CountTupleBolt;
import org.benchmark.bolt.ExclamationBolt;
import org.benchmark.spout.RandomSentenceSpout;

public class DiamondTopology extends ConfigurableTopology {
    public static void main(String[] args) throws Exception {
        ConfigurableTopology.start(new DiamondTopology(), args);
    }

    @Override
    protected int run(String[] args) throws Exception {
        TopologyBuilder builder = new TopologyBuilder();
        JedisPoolConfig poolConfig = new JedisPoolConfig.Builder()
                .setHost("10.10.108.71").setPort(6379).setPassword("123456").build();
        RedisStoreMapper storeMapper = setupStoreMapper();
        RedisStoreBolt storeBolt = new RedisStoreBolt(poolConfig, storeMapper);

        builder.setSpout("spout", new RandomSentenceSpout(), 1);
        builder.setBolt("exclam1", new ExclamationBolt() , 1).shuffleGrouping("spout");
        builder.setBolt("exclam2", new ExclamationBolt() , 2).shuffleGrouping("spout");
        builder.setBolt("exclam3", new ExclamationBolt() , 3).shuffleGrouping("spout");
        builder.setBolt("counter" , new CountTupleBolt() , 1).shuffleGrouping("exclam1").shuffleGrouping("exclam2").shuffleGrouping("exclam3");
        builder.setBolt("store", storeBolt,1).shuffleGrouping("counter");

        conf.setDebug(true);

        String topologyName = "diamond-topology";

        conf.setNumWorkers(3);

        if (args != null && args.length > 0) {
            topologyName = args[0];
        }
        return submit(topologyName, conf, builder);
    }

    private static RedisStoreMapper setupStoreMapper() {
        return new DiamondStoreMapper();
    }

    private static class DiamondStoreMapper implements RedisStoreMapper {
        private RedisDataTypeDescription description;
        public static int count = 0;

        public DiamondStoreMapper() {
            String sortSetKey = "diamond";
            description = new RedisDataTypeDescription(RedisDataTypeDescription.RedisDataType.SORTED_SET,sortSetKey);
        }

        @Override
        public RedisDataTypeDescription getDataTypeDescription() {
            return description;
        }

        @Override
        public String getKeyFromTuple(ITuple tuple) {
            if (tuple.getStringByField("count") != null){
                return String.valueOf(String.valueOf(System.currentTimeMillis()) + ":" + DiamondStoreMapper.count++);
            }else {
                return null;
            }
        }

        @Override
        public String getValueFromTuple(ITuple tuple) {
            if(tuple.getStringByField("count") != null) {
                return String.valueOf(System.currentTimeMillis());
            }else{
                return null;
            }
        }

    }
}
