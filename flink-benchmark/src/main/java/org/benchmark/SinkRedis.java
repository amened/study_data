package org.benchmark;

import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSink;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaProducer;
import org.apache.flink.streaming.connectors.redis.RedisSink;
import org.apache.flink.streaming.connectors.redis.common.config.FlinkJedisClusterConfig;
import org.apache.flink.streaming.connectors.redis.common.config.FlinkJedisPoolConfig;
import org.apache.flink.streaming.connectors.redis.common.mapper.RedisCommand;
import org.apache.flink.streaming.connectors.redis.common.mapper.RedisCommandDescription;
import org.apache.flink.streaming.connectors.redis.common.mapper.RedisMapper;

import java.util.ArrayList;
import java.util.Properties;

public class SinkRedis {
    public static void main(String[] args) throws Exception{
        // 创建执行环境
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);

        Properties properties = new Properties();
        properties.setProperty("bootstrap.servers", "10.10.108.71:9092");
        properties.setProperty("group.id", "consumer-group");
        properties.setProperty("key.deserializer",
                "org.apache.kafka.common.serialization.StringDeserializer");
        properties.setProperty("value.deserializer",
                "org.apache.kafka.common.serialization.StringDeserializer");
        properties.setProperty("auto.offset.reset", "latest");


        // 读取kafka数据
        DataStreamSource<String> stringDataStreamSource = env.addSource(new FlinkKafkaConsumer<String>("sensor", new SimpleStringSchema(), properties));


        // map操作
        DataStream<SensorReading2> map = stringDataStreamSource.map((String value) -> {
            String[] fields = value.split(",");
            return new SensorReading2(fields[0], new Long(fields[1]), new Double(fields[2]));
        });


        FlinkJedisPoolConfig config = new FlinkJedisPoolConfig.Builder()
                .setHost("10.10.108.71")
                .setPort(6379)
                .setPassword("123456")
                .build();


        DataStreamSink<SensorReading2> sink = map.addSink(new RedisSink<>(config, new RedisMapper<SensorReading2>() {
            // 定义保存命令到redis的命令，存成Hash表，hset sensor_temp id temperature
            @Override
            public RedisCommandDescription getCommandDescription() {
                return new RedisCommandDescription(RedisCommand.HSET,"sensor_tem");
            }

            @Override
            public String getKeyFromData(SensorReading2 data) {
                return data.getId();
            }

            @Override
            public String getValueFromData(SensorReading2 data) {
                return data.getTemperature().toString();
            }
        }));

        env.execute();
    }

}
