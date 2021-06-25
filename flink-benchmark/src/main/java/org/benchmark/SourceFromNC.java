package org.benchmark;

import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

public class SourceFromNC {
    public static void main(String[] args) throws Exception{
        // 创建执行环境
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        // 设置并行度
        env.setParallelism(1);

        // kafka读取数据
        DataStreamSource<String> stringDataStreamSource = env.socketTextStream("localhost",7777);
        stringDataStreamSource.print();

        // 启动Job
        env.execute();
    }
}
