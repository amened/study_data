package org.benchmark.bolt;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

import java.util.Arrays;
import java.util.Map;

public class SortNumberStringBolt extends BaseRichBolt {
    private OutputCollector _collector;

    public SortNumberStringBolt() {
        //Empty
    }

    @Override
    public void prepare(Map conf, TopologyContext context, OutputCollector collector) {
        _collector = collector;
    }

    @Override
    public void execute(Tuple tuple) {
        //字符串对象
        String str= tuple.getString(0);
        StringBuilder sBuilder = new StringBuilder();
        //新数组—用于存储字符串中数字
        int[] arr=new int[str.length()];
        //表示新数组下标
        int index=0;
        //遍历字符串
        for(int i=0;i<str.length();i++){
            //获取每一个字符
            char c=str.charAt(i);
            System.out.print(c + " ");

            //判断是否是数字
            if( c>= '0' && c <= '9'){
                //把符合条件的数字字符赋值给新数组
                arr[index++]=c-'0';
            }
        }
        System.out.println();
        //缩容—保证只排序字符串中的数字
        arr= Arrays.copyOf(arr,index);
        //排序
        Arrays.sort(arr);
        for (int number : arr ){
            sBuilder.append(number);
        }
        String stringSort = sBuilder.toString();
        _collector.emit(tuple, new Values(stringSort));
        _collector.ack(tuple);
    }

    @Override
    public void cleanup() {
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("message"));
    }
}

