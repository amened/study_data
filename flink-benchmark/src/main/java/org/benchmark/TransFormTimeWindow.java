package org.benchmark;

import org.apache.flink.api.common.functions.AggregateFunction;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.KeyedStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.assigners.SlidingProcessingTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.util.Collector;

public class TransFormTimeWindow {
    public static void main(String[] args) throws Exception {
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

        KeyedStream<SensorReading2, String> keyedStream = dataStream.
                keyBy((SensorReading2 value) -> {
            return value.getId();
        });



//        // 1.增量聚合函数
//        keyedStream
//        // 滑动计数窗口(数据窗口为10，滑动2个数据，数据窗口为10，滑动2个数据，数据窗口为10。。。)
//        // .countWindow(10,2);
//        //  滚动时间窗口
//        // .window(TumblingEventTimeWindows.of(Time.seconds(15))
//                .window(TumblingProcessingTimeWindows.of(Time.seconds(15)))
//        // .timeWindow(Time.seconds(15))
//        // 会话窗口
//            .window(EventTimeSessionWindows.withGap(Time.minutes(1)));

//        // 启动Job
//        .aggregate(new AggregateFunction<SensorReading2, Integer, Integer>() {
//            /*
//             * @param <IN> The type of the values that are aggregated (input values)
//             * @param <ACC> The type of the accumulator (intermediate aggregate state).
//             * @param <OUT> The type of the aggregated result
//             */
//
//            /*
//            创建累加器，给定初始值
//             */
//            @Override
//            public Integer createAccumulator() {
//                return 0;
//            }
//
//            /*
//            对数据进行聚合
//             */
//            @Override
//            public Integer add(SensorReading2 value, Integer accumulator) {
//                return accumulator + 1;
//            }
//
//            /*
//            累加器作为输出结果
//             */
//            @Override
//            public Integer getResult(Integer accumulator) {
//                return accumulator;
//            }
//
//            /*
//            一般不用于滚动和滑动窗口，而用于session窗口
//             */
//            @Override
//            public Integer merge(Integer a, Integer b) {
//                return a + b;
//            }
//        }).print();

//        // 2. 全窗口函数
//        keyedStream.window(TumblingProcessingTimeWindows.of(Time.seconds(15)))
//                /**
//                 * Evaluates the window and outputs none or several elements.
//                 *
//                 * @param key The key for which this window is evaluated.
//                 * @param window The window that is being evaluated.
//                 * @param input The elements in the window being evaluated.
//                 * @param out A collector for emitting elements.
//                 * @throws Exception The function may throw exceptions to fail the program and trigger recovery.
//                 */
//                .apply(new WindowFunction<SensorReading2, Tuple3<String,Long,Integer>, String, TimeWindow>() {
//                    @Override
//                    public void apply(String s, TimeWindow window, Iterable<SensorReading2> input, Collector<Tuple3<String,Long,Integer>> out) throws Exception {
//                        // 循环
////                        Integer count = 0;
////                        Iterator<SensorReading2> iterator = input.iterator();
////                        while (iterator.hasNext()){
////                            iterator.next();
////                            count ++ ;
////                        }
////                        out.collect(count);
//                        // IteratorUtils
////                        int count = IteratorUtils.toList(input.iterator()).size();
////                        out.collect(count);
//                        // window功能
//                        Integer count = IteratorUtils.toList(input.iterator()).size();
//                        Long end = window.getEnd();
//                        String key = s;
//                        out.collect(new Tuple3<>(s,end,count));
//                    }
//                }).print();

         // 3. 滑动窗口
        keyedStream.
                window(SlidingProcessingTimeWindows.of(Time.minutes(2),Time.minutes(1)))
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

    public static class MyFlatMapper implements FlatMapFunction<String, Tuple2<String, Integer>> {
        private static final long serialVersionUID = -5224012503623543819L;

        @Override
        public void flatMap(String value, Collector<Tuple2<String, Integer>> out) throws Exception{
            // 分词
            String[] words = value.split(" ");
            for (String word : words){
                out.collect(new Tuple2<String,Integer>(word,1));
            }
        }
    }

}
