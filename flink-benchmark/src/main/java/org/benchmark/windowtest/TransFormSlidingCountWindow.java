package org.benchmark.windowtest;

import org.apache.flink.api.common.functions.AggregateFunction;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.KeyedStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.benchmark.SensorReading2;

public class TransFormSlidingCountWindow {
    public static void main(String[] args)throws Exception {
        // 创建执行环境
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);

        // 从nc中读取数据
        DataStreamSource<String> stringDataStreamSource = env.socketTextStream("localhost", 7777);

        // map操作
        SingleOutputStreamOperator<SensorReading2> dataStream = stringDataStreamSource.map((String value) -> {
            String[] fields = value.split(",");
            return new SensorReading2(fields[0], new Long(fields[1]), new Double(fields[2]));
        }).setParallelism(2);

        KeyedStream<SensorReading2, String> keyedStream = dataStream.keyBy((SensorReading2 value) -> {
            return value.getId();
        });

        keyedStream
                // 初始状态就是5个，对这5个数据进行计算输出，之后每个窗口就是10个，对诗歌数据进行计算
                .countWindow(10,5)
                // 增量聚合函数,对数据个数进行统计
                .aggregate(new AggregateFunction<SensorReading2, Integer, Integer>() {
                    /*
                     * @param <IN> The type of the values that are aggregated (input values)
                     * @param <ACC> The type of the accumulator (intermediate aggregate state).
                     * @param <OUT> The type of the aggregated result
                     */

                    /*
                    创建累加器，给定初始值
                     */
                    @Override
                    public Integer createAccumulator() {
                        return 0;
                    }

                    /*
                    对数据进行聚合
                     */
                    @Override
                    public Integer add(SensorReading2 value, Integer accumulator) {
                        return accumulator + 1;
                    }

                    /*
                    累加器作为输出结果
                     */
                    @Override
                    public Integer getResult(Integer accumulator) {
                        return accumulator;
                    }

                    /*
                    一般不用于滚动和滑动窗口，而用于session窗口
                     */
                    @Override
                    public Integer merge(Integer a, Integer b) {
                        return a + b;
                    }
                }).print();
        env.execute();
    }
}
