package org.benchmark;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSink;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.source.SourceFunction;
import org.apache.flink.streaming.connectors.redis.RedisSink;
import org.apache.flink.streaming.connectors.redis.common.config.FlinkJedisPoolConfig;
import org.apache.flink.streaming.connectors.redis.common.mapper.RedisCommand;
import org.apache.flink.streaming.connectors.redis.common.mapper.RedisCommandDescription;
import org.apache.flink.streaming.connectors.redis.common.mapper.RedisMapper;
import org.apache.flink.util.Collector;

import java.util.Random;
import java.util.TreeMap;

public class StreamWordCount {
    public  static Integer count=0;
    public static void main(String[] args)throws Exception {
        // 创建执行环境
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        
        // 设置并行度
        // env.setParallelism(5);
        // 从文件中读取数据
        // String inputPath = Thread.currentThread().getContextClassLoader().getResource("hello.txt").getFile();
        // DataStreamSource<String> stringDataSource = env.readTextFile(inputPath);
         //  用parameter tool工具从程序启动参数提取数据
//        ParameterTool parameterTool = ParameterTool.fromArgs(args);
//        String host  = parameterTool.get("host");
//        int port = parameterTool.getInt("port");


        // 使用nc提供一个server，从server中提取一个文本流
//       DataStream<String> stringDataSource = env.socketTextStream(host, port);
         // 对数据集进行处理，按空格分词展开，转换成(word, 1)二元组进行统计
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

        // 数据处理
        SingleOutputStreamOperator<Tuple2<String, Integer>> sum = stringDataSource
                .flatMap(new MyFlatMapper()).setParallelism(2)
                .keyBy((Tuple2<String, Integer> value) -> {
                    return value.f0;
                })
                .sum(1).setParallelism(3);

        FlinkJedisPoolConfig config = new FlinkJedisPoolConfig.Builder()
                .setHost("10.10.108.71")
                .setPort(6379)
                .setPassword("123456")
                .build();

        DataStreamSink<Tuple2<String, Integer>> flink_wordcount = sum.addSink(new RedisSink<>(config, new RedisMapper<Tuple2<String, Integer>>() {

            // 定义保存命令到redis的命令，存成Hash表，hset sensor_temp id temperature
            @Override
            public RedisCommandDescription getCommandDescription() {
                return new RedisCommandDescription(RedisCommand.ZADD, "flink_wordcount");
            }

            @Override
            public String getKeyFromData(Tuple2<String, Integer> data) {
                if (data.f1 != null){
                    return String.valueOf(String.valueOf(System.currentTimeMillis()) + ":" + StreamWordCount.count++);
                }else {
                    return null;
                }
            }

            @Override
            public String getValueFromData(Tuple2<String, Integer> data) {
                if(data.f0 != null) {
                    return String.valueOf(System.currentTimeMillis());
                }else{
                    return null;
                }
            }
        })).setParallelism(1);

//        sum.print();
        // 启动Job
        env.execute();
    };

    public static class MyFlatMapper implements FlatMapFunction<String, Tuple2<String, Integer>>{
        private static final long serialVersionUID = 7883096705374505894L;
        @Override
        public void flatMap(String value, Collector<Tuple2<String, Integer>> out) throws Exception{
            // 分词
            String[] words = value.split(" ");
            for (String word : words){
                out.collect(new Tuple2<String,Integer>(word,1));
            }
        }
    }

    public static class MyRedisMapper implements RedisMapper<Tuple2<String, Integer>>{
        public static Integer count;

        @Override
        public RedisCommandDescription getCommandDescription() {
            return new RedisCommandDescription(RedisCommand.ZADD, "flink_wordcount");
        }

        @Override
        public String getKeyFromData(Tuple2<String, Integer> data) {
            if (data.f1 != null){
                return String.valueOf(String.valueOf(System.currentTimeMillis()) + ":" + MyRedisMapper.count++);
            }else {
                return null;
            }
        }

        @Override
        public String getValueFromData(Tuple2<String, Integer> data) {
            if(data.f0 != null) {
                return String.valueOf(System.currentTimeMillis());
            }else{
                return null;
            }
        }
    }
}
