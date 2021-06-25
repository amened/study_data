package org.benchmark;

import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;
import java.util.Properties;

public class SourceFromKafka {
    public static void main(String[] args)throws Exception {
        // 创建执行环境
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        Properties properties = new Properties();
        properties.setProperty("bootstrap.servers", "192.168.142.10:9092");
        properties.setProperty("group.id", "consumer-group");
        properties.setProperty("key.deserializer",
                "org.apache.kafka.common.serialization.StringDeserializer");
        properties.setProperty("value.deserializer",
                "org.apache.kafka.common.serialization.StringDeserializer");
        properties.setProperty("auto.offset.reset", "latest");

        // 设置并行度
        env.setParallelism(1);

        // kafka读取数据
        DataStreamSource<String> stringDataStreamSource = env.addSource(new FlinkKafkaConsumer<String>("first", new SimpleStringSchema(), properties)).setParallelism(2);
        stringDataStreamSource.print();

        // 启动Job
        env.execute();
    }
}
