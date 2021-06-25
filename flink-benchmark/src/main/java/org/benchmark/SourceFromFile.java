package org.benchmark;

import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

public class SourceFromFile {
    public static void main(String[] args) throws Exception{
        // 创建执行环境
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        // 从文件中读取数据
        String inputPath = Thread.currentThread().getContextClassLoader().getResource("sensor.txt").getFile();

        DataStreamSource<String> stringDataSource = env.readTextFile(inputPath).setParallelism(2);
        stringDataSource.print().setParallelism(1);
        // 启动Job
        env.execute();
    }
}
