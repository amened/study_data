package org.benchmark;

import org.apache.flink.api.common.functions.ReduceFunction;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.KeyedStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

public class TransFormReduce {
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
        }).setParallelism(2);

        // 分类操作
        KeyedStream<SensorReading2, String> sensorReading2StringKeyedStream = map.keyBy((SensorReading2 value) -> {
            return value.getId();
        });
        SingleOutputStreamOperator<SensorReading2> reduce = sensorReading2StringKeyedStream.reduce(new ReduceFunction<SensorReading2>() {
            @Override
            public SensorReading2 reduce(SensorReading2 value1, SensorReading2 value2) throws Exception {
                // 第一次执行reduce操作的时候value1就是第一个流进来的数据，value2就是第二个流进来的数据
                // 从第二次开始value1就是上一次操作的结果，value2就是第三个流进来的数据，以此类推
                System.out.println(value1 + ":" + value2);
                return new SensorReading2(value2.getId(), value2.getTimeStamp(), Math.max(value1.getTemperature(), value2.getTemperature()));
            }
        });



        reduce.print();

        // 启动Job
        env.execute();
    }
}
