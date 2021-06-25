package org.benchmark;

import org.apache.flink.api.common.functions.AggregateFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.KeyedStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

public class TransFormCountWindow {
    public static void main(String[] args) throws Exception{
        // 创建执行环境
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);

        // 从nc中读取数据
        DataStreamSource<String> stringDataStreamSource = env.socketTextStream("localhost", 7777);

        // map操作
        SingleOutputStreamOperator<SensorReading2> dataStream = stringDataStreamSource.map((String value) -> {
            String[] fields = value.split(",");
            return new SensorReading2(fields[0], new Long(fields[1]), new Double(fields[2]));
        }).setParallelism(1);

        KeyedStream<SensorReading2, String> keyedStream = dataStream.keyBy((SensorReading2 value) -> {
            return value.getId();
        });

        keyedStream
                .countWindow(5,2)
                .aggregate(new AggregateFunction<SensorReading2, Tuple2<Double,Integer>, Double>() {
                    @Override
                    public Tuple2<Double, Integer> createAccumulator() {
                        return new Tuple2<Double, Integer> (0.0 ,0);
                    }

                    @Override
                    public Tuple2<Double, Integer> add(SensorReading2 value, Tuple2<Double, Integer> accumulator) {
                        return new Tuple2<Double,Integer>(value.getTemperature() + accumulator.f0 , accumulator.f1 + 1);
                    }

                    @Override
                    public Double getResult(Tuple2<Double, Integer> accumulator) {
                        return accumulator.f0 / accumulator.f1;
                    }

                    @Override
                    public Tuple2<Double, Integer> merge(Tuple2<Double, Integer> a, Tuple2<Double, Integer> b) {
                        return null;
                    }
                }).setParallelism(2).
                print();

        System.out.println(env.getExecutionPlan());
                   

        env.execute();
    }
}
