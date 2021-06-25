package org.benchmark;

import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSink;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.source.SourceFunction;
import org.apache.flink.streaming.connectors.redis.RedisSink;
import org.apache.flink.streaming.connectors.redis.common.config.FlinkJedisPoolConfig;
import org.apache.flink.streaming.connectors.redis.common.mapper.RedisCommand;
import org.apache.flink.streaming.connectors.redis.common.mapper.RedisCommandDescription;
import org.apache.flink.streaming.connectors.redis.common.mapper.RedisMapper;

import java.util.Random;

public class DiamondTopology {
    public static void main(String[] args)throws Exception {
        // 创建执行环境
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.disableOperatorChaining();

        DataStream<String> stringDataSource = env.addSource(new SourceFunction<String>() {
            private boolean running = true;

            @Override
            public void run(SourceContext<String> ctx) throws Exception {
                // 定义一个随机数发生器
                Random random = new Random();
                while (running){
                    String[] sentences = new String[]{
                            sentence("Using artificial intelligence to solve global atmosphere and ocean science problems and improve prediction accuracy of extreme disastrous weather has been popular these days ENSO EL Nino Southern Oscillation in the tropical Pacific Ocean is the strongest and most obvious yearly climate signal on earth It will cause extreme disasters like flood drought high temperature and snow disaster etc Extremely cold winter in 2020 in China also has something to do with ENSO Climate Dynamical Model costs much computing resource and does not work well in Spring With spatiotemporal series meteorological factor at T moment in last 12 months and historical climate observation and stimulation data you can build a deep learning model to predict ENSO and Nino 3.4 index in future 1-24 months This can help a lot to predict extreme weather and climate Historical Climate Observation and Stimulation Dataset is provided by Institute for Climate and Application Research ICAR There is historical stimulation data by CMIP5/6 mode and historical assimilated observation data by American SODA mode in nearly 100 years Each sample has following weather and spatiotemporal variableabnormal sea surface temperature SST abnormal thermal content T300, abnormal zonal wind Ua, abnormal meridional wind Va and data dimension including year month lat and lon Train set includes label data of Nino3.4 index in matching month Test set has 12 time series made of n sections randomly selected from multiple global sea data It is saved as NPY file"),
                            sentence("Using artificial intelligence to solve global atmosphere and ocean science problems and improve prediction accuracy of extreme disastrous weather has been popular these days ENSO EL Nino Southern Oscillation in the tropical Pacific Ocean is the strongest and most obvious yearly climate signal on earth It will cause extreme disasters like flood drought high temperature and snow disaster etc Extremely cold winter in 2020 in China also has something to do with ENSO Climate Dynamical Model costs much computing resource and does not work well in Spring With spatiotemporal series meteorological factor at T moment in last 12 months and historical climate observation and stimulation data you can build a deep learning model to predict ENSO and Nino 3.4 index in future 1-24 months This can help a lot to predict extreme weather and climate Historical Climate Observation and Stimulation Dataset is provided by Institute for Climate and Application Research ICAR There is historical stimulation data by CMIP5/6 mode and historical assimilated observation data by American SODA mode in nearly 100 years Each sample has following weather and spatiotemporal variableabnormal sea surface temperature SST abnormal thermal content T300, abnormal zonal wind Ua, abnormal meridional wind Va and data dimension including year month lat and lon Train set includes label data of Nino3.4 index in matching month Test set has 12 time series made of n sections randomly selected from multiple global sea data It is saved as NPY file"),
                            sentence("Using artificial intelligence to solve global atmosphere and ocean science problems and improve prediction accuracy of extreme disastrous weather has been popular these days ENSO EL Nino Southern Oscillation in the tropical Pacific Ocean is the strongest and most obvious yearly climate signal on earth It will cause extreme disasters like flood drought high temperature and snow disaster etc Extremely cold winter in 2020 in China also has something to do with ENSO Climate Dynamical Model costs much computing resource and does not work well in Spring With spatiotemporal series meteorological factor at T moment in last 12 months and historical climate observation and stimulation data you can build a deep learning model to predict ENSO and Nino 3.4 index in future 1-24 months This can help a lot to predict extreme weather and climate Historical Climate Observation and Stimulation Dataset is provided by Institute for Climate and Application Research ICAR There is historical stimulation data by CMIP5/6 mode and historical assimilated observation data by American SODA mode in nearly 100 years Each sample has following weather and spatiotemporal variableabnormal sea surface temperature SST abnormal thermal content T300, abnormal zonal wind Ua, abnormal meridional wind Va and data dimension including year month lat and lon Train set includes label data of Nino3.4 index in matching month Test set has 12 time series made of n sections randomly selected from multiple global sea data It is saved as NPY file"),
                            sentence("Using artificial intelligence to solve global atmosphere and ocean science problems and improve prediction accuracy of extreme disastrous weather has been popular these days ENSO EL Nino Southern Oscillation in the tropical Pacific Ocean is the strongest and most obvious yearly climate signal on earth It will cause extreme disasters like flood drought high temperature and snow disaster etc Extremely cold winter in 2020 in China also has something to do with ENSO Climate Dynamical Model costs much computing resource and does not work well in Spring With spatiotemporal series meteorological factor at T moment in last 12 months and historical climate observation and stimulation data you can build a deep learning model to predict ENSO and Nino 3.4 index in future 1-24 months This can help a lot to predict extreme weather and climate Historical Climate Observation and Stimulation Dataset is provided by Institute for Climate and Application Research ICAR There is historical stimulation data by CMIP5/6 mode and historical assimilated observation data by American SODA mode in nearly 100 years Each sample has following weather and spatiotemporal variableabnormal sea surface temperature SST abnormal thermal content T300, abnormal zonal wind Ua, abnormal meridional wind Va and data dimension including year month lat and lon Train set includes label data of Nino3.4 index in matching month Test set has 12 time series made of n sections randomly selected from multiple global sea data It is saved as NPY file"),
                            sentence("Using artificial intelligence to solve global atmosphere and ocean science problems and improve prediction accuracy of extreme disastrous weather has been popular these days ENSO EL Nino Southern Oscillation in the tropical Pacific Ocean is the strongest and most obvious yearly climate signal on earth It will cause extreme disasters like flood drought high temperature and snow disaster etc Extremely cold winter in 2020 in China also has something to do with ENSO Climate Dynamical Model costs much computing resource and does not work well in Spring With spatiotemporal series meteorological factor at T moment in last 12 months and historical climate observation and stimulation data you can build a deep learning model to predict ENSO and Nino 3.4 index in future 1-24 months This can help a lot to predict extreme weather and climate Historical Climate Observation and Stimulation Dataset is provided by Institute for Climate and Application Research ICAR There is historical stimulation data by CMIP5/6 mode and historical assimilated observation data by American SODA mode in nearly 100 years Each sample has following weather and spatiotemporal variableabnormal sea surface temperature SST abnormal thermal content T300, abnormal zonal wind Ua, abnormal meridional wind Va and data dimension including year month lat and lon Train set includes label data of Nino3.4 index in matching month Test set has 12 time series made of n sections randomly selected from multiple global sea data It is saved as NPY file")
                    };
                    final String sentence = sentences[random.nextInt(sentences.length)];
                    ctx.collect(sentence);
                }
                //控制输出频率
                Thread.sleep(1000);
            }

            protected String sentence(String input) {
                return input;
            }

            @Override
            public void cancel() {
                this.running = false;
            }
        }).setParallelism(1);

        // map操作
        SingleOutputStreamOperator<String> stringSingleOutputStreamOperator1 = stringDataSource.map((String value) -> {
            return value + "!!!";
        }).setParallelism(1);

        SingleOutputStreamOperator<String> stringSingleOutputStreamOperator2 = stringDataSource.map((String value) -> {
            return value + "!!!";
        }).setParallelism(2);

        SingleOutputStreamOperator<String> stringSingleOutputStreamOperator3 = stringDataSource.map((String value) -> {
            return value + "!!!";
        }).setParallelism(3);

        DataStream<String> union = stringSingleOutputStreamOperator1.union(stringSingleOutputStreamOperator2).union(stringSingleOutputStreamOperator3);

        SingleOutputStreamOperator<String> map = union.map((String value) -> {
            return value;
        }).setParallelism(1);

        FlinkJedisPoolConfig config = new FlinkJedisPoolConfig.Builder()
                .setHost("10.10.108.71")
                .setPort(6379)
                .setPassword("123456")
                .build();

        map.addSink(new RedisSink<>(config, new RedisMapper<String>() {

            // 定义保存命令到redis的命令，存成Hash表，hset sensor_temp id temperature
            @Override
            public RedisCommandDescription getCommandDescription() {
                return new RedisCommandDescription(RedisCommand.ZADD, "flink_wordcount");
            }

            @Override
            public String getKeyFromData(String data) {
                if (data != null){
                    return String.valueOf(String.valueOf(System.currentTimeMillis()) + ":" + StreamWordCount.count++);
                }else {
                    return null;
                }
            }

            @Override
            public String getValueFromData(String data) {
                if(data != null) {
                    return String.valueOf(System.currentTimeMillis());
                }else{
                    return null;
                }
            }
        })).setParallelism(1);

        env.execute();
    }
}
