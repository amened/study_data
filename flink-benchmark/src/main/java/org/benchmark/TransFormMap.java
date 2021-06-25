package org.benchmark;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.util.Collector;

public class TransFormMap {
    public static void main(String[] args) throws Exception{
        // 创建执行环境
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        // 设置并行度
        env.setParallelism(1);

        // 从文件中读取数据
        String inputPath = Thread.currentThread().getContextClassLoader().getResource("sensor.txt").getFile();
        DataStreamSource<String> stringDataSource = env.readTextFile(inputPath);

        SingleOutputStreamOperator<Integer> map = stringDataSource.map((String value) -> {
            return value.length();
        }).setParallelism(2);

        SingleOutputStreamOperator<String> stringSingleOutputStreamOperator = stringDataSource.flatMap(new FlatMapFunction<String, String>() {
            @Override
            public void flatMap(String value, Collector<String> out) throws Exception {
                String[] split = value.split(",");
                for (String s : split){
                    out.collect(s);
                }
            }
        }).setParallelism(3);


        SingleOutputStreamOperator<String> sensor_1 = stringDataSource.filter((String value) -> {
            if (value.startsWith("sensor_1")) {
                return true;
            } else {
                return false;
            }
        }).setParallelism(4);

        map.print("map");
        stringSingleOutputStreamOperator.print("flatmap");
        sensor_1.print("filter");

        // 打印拓扑图
        // System.out.println(env.getExecutionPlan());
        // 启动Job
        env.execute();
    }
}
