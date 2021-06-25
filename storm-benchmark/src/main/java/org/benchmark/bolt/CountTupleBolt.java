package org.benchmark.bolt;

import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseBasicBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

import java.util.HashMap;
import java.util.Map;

public class CountTupleBolt extends BaseBasicBolt {
        Map<String, Integer> counts = new HashMap<String, Integer>();
        Integer i = 0 ;
        @Override
        public void execute(Tuple tuple, BasicOutputCollector collector) {
            collector.emit(new Values(tuple.getString(0)));
        }

        @Override
        public void declareOutputFields(OutputFieldsDeclarer declarer) {
            declarer.declare(new Fields("count"));
        }
}
