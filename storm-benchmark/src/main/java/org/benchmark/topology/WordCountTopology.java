package org.benchmark.topology;

import org.apache.storm.LocalCluster;
import org.apache.storm.redis.bolt.RedisStoreBolt;
import org.apache.storm.redis.common.config.JedisPoolConfig;
import org.apache.storm.redis.common.mapper.RedisDataTypeDescription;
import org.apache.storm.redis.common.mapper.RedisStoreMapper;
import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.ConfigurableTopology;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.topology.base.BaseBasicBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.ITuple;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;
import org.benchmark.bolt.WordCountBolt;
import org.benchmark.spout.RandomSentenceSpout;
import java.util.StringTokenizer;


public class WordCountTopology extends ConfigurableTopology {
    public static void main(String[] args) throws Exception {
        ConfigurableTopology.start(new WordCountTopology(), args);

    }

    @Override
    protected int run(String[] args) throws Exception {

        TopologyBuilder builder = new TopologyBuilder();
        JedisPoolConfig poolConfig = new JedisPoolConfig.Builder()
                .setHost("10.10.108.71").setPort(6379).setPassword("123456").build();
        RedisStoreMapper storeMapper = setupStoreMapper();
        RedisStoreBolt storeBolt = new RedisStoreBolt(poolConfig, storeMapper);

        builder.setSpout("spout", new RandomSentenceSpout(), 1);
        builder.setBolt("split", new SplitSentence(), 2).shuffleGrouping("spout");
        builder.setBolt("count", new WordCountBolt(), 3).fieldsGrouping("split", new Fields("word"));
        builder.setBolt("store", storeBolt).shuffleGrouping("count");

        conf.setDebug(true);
        String topologyName = "line-topology";
        conf.setNumWorkers(3);
        if (args != null && args.length > 0) {
            topologyName = args[0];
        }
        conf.setNumWorkers(3);
        return submit(topologyName, conf, builder);
    }

    public static class SplitSentence extends BaseBasicBolt{

        private static final long serialVersionUID = 2682020698588300442L;

        @Override
        public void execute(Tuple tuple, BasicOutputCollector collector) {
            String sentence = tuple.getString(0);
            StringTokenizer it = new StringTokenizer(sentence);
            while(it.hasMoreElements()) {
                collector.emit(new Values(it.nextElement()));
            }
        }

        @Override
        public void declareOutputFields(OutputFieldsDeclarer declarer) {
            declarer.declare(new Fields("word"));
        }
    }

    private static RedisStoreMapper setupStoreMapper() {
        return new WordCountStoreMapper();
    }

    private static class WordCountStoreMapper implements RedisStoreMapper {
        private static final long serialVersionUID = -9072833260244749046L;
        public static int count = 0;
        private final RedisDataTypeDescription description;

        WordCountStoreMapper() {
            String sortSetKey = "wordcount";
            description = new RedisDataTypeDescription(
                    RedisDataTypeDescription.RedisDataType.SORTED_SET, sortSetKey);
        }

        @Override
        public RedisDataTypeDescription getDataTypeDescription() {
            return description;
        }

        @Override
        public String getKeyFromTuple(ITuple tuple) {
            if (tuple.getStringByField("count") != null){
                return String.valueOf(String.valueOf(System.currentTimeMillis()) + ":" +WordCountStoreMapper.count++);
            }else {
                return null;
            }
        }

        @Override
        public String getValueFromTuple(ITuple tuple) {
            if(tuple.getStringByField("word") != null) {
                return String.valueOf(System.currentTimeMillis());
            }else{
                return null;
            }
        }
    }
}

