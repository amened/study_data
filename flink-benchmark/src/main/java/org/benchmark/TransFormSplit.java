package org.benchmark;

import org.apache.flink.api.common.functions.ReduceFunction;
import org.apache.flink.api.common.functions.RichMapFunction;
import org.apache.flink.streaming.api.datastream.*;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.co.CoFlatMapFunction;
import org.apache.flink.streaming.api.functions.co.CoMapFunction;
import org.apache.flink.util.Collector;

import java.io.Serializable;

public class TransFormSplit {
    public static void main(String[] args) throws Exception{
        // 创建执行环境
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);


        // 读取文件数据
        String inputPath = Thread.currentThread().getContextClassLoader().getResource("sensor.txt").getFile();
        DataStreamSource<String> stringDataStreamSource = env.readTextFile(inputPath);

        // map操作
        DataStream<SensorReading2> map = stringDataStreamSource.map((String value) -> {
            String[] fields = value.split(",");
            return new SensorReading2(fields[0], new Long(fields[1]), new Double(fields[2]));
        }).setParallelism(2);

        // map1操作
        SingleOutputStreamOperator<SensorReading2> map1 = stringDataStreamSource.map((String value) -> {
            String[] fields = value.split(",");
            return new SensorReading2(fields[0], new Long(fields[1]), new Double(fields[2]));
        }).setParallelism(3);

        // union可以合并多个相同的流
        // DataStream<SensorReading2> union = map.union(map1);


        map.print("map");
        map1.print("map1");
        env.execute();
    }
}


