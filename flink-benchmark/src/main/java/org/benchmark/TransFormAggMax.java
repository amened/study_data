package org.benchmark;

import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.KeyedStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
public class TransFormAggMax {
    public static void main(String[] args) throws Exception{
        // 创建执行环境
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);

        // 读取文件数据
        String inputPath = Thread.currentThread().getContextClassLoader().getResource("sensor.txt").getFile();
        DataStreamSource<String> stringDataStreamSource = env.readTextFile(inputPath);

        // map操作
        SingleOutputStreamOperator<SensorReading2> map = stringDataStreamSource.map((String value) -> {
            String[] fields = value.split(",");
            return new SensorReading2(fields[0], new Long(fields[1]), new Double(fields[2]));
        }).setParallelism(1);

        // 分类操作
        KeyedStream<SensorReading2, String> sensorReading2StringKeyedStream = map.keyBy((SensorReading2 value) -> {
            return value.getId();
        });

        // 滚动聚合,参数就是POJO的字段
        SingleOutputStreamOperator<SensorReading2> temperature = sensorReading2StringKeyedStream.max("temperature");

        // 输出
        temperature.print();
        // 启动Job
        env.execute();
    }
}



