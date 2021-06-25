package org.benchmark;

import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.ProcessFunction;
import org.apache.flink.util.Collector;
import org.apache.flink.util.OutputTag;

public class TransFormSideOutPut {
    public static void main(String[] args) throws Exception {

        //运行环境
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);

        //输入数据源
        DataStreamSource<Tuple3<Integer, String, String>> source = env.fromElements(
                new Tuple3<>(1, "1", "AAA"),
                new Tuple3<>(2, "2", "AAA"),
                new Tuple3<>(3, "3", "AAA"),
                new Tuple3<>(1, "1", "BBB"),
                new Tuple3<>(2, "2", "BBB"),
                new Tuple3<>(3, "3", "BBB")
        );

        //1、定义OutputTag,自动推断测流输出类型typeinformation，但是单个参数无法推断，因为泛型被抹去，需要使用匿名内部类来继承。
        OutputTag<Tuple3<Integer, String, String>> ATag = new OutputTag<Tuple3<Integer, String, String>>("A-tag") {};
        OutputTag<Tuple3<Integer, String, String>> BTag = new OutputTag<Tuple3<Integer, String, String>>("B-tag") {};

        // 其他非元组类型优先考虑这种方式
        OutputTag<String> A_TAG = new OutputTag<String>("A"){};
        OutputTag<String> B_TAG = new OutputTag<String>("B"){};

        //2、在ProcessFunction中处理主流和分流
        SingleOutputStreamOperator<Tuple3<Integer, String, String>> processedStream =
                source.process(new ProcessFunction<Tuple3<Integer, String, String>, Tuple3<Integer, String, String>>() {
                    @Override
                    public void processElement(Tuple3<Integer, String, String> value, Context ctx, Collector<Tuple3<Integer, String, String>> out) throws Exception {
                        //侧流-只输出特定数据
                        if (value.f2.equals("AAA")) {
                            ctx.output(ATag, value);
                        } else {
                            //主流
                            out.collect(value);
                        }
                    }
                });

        //获取主流
        processedStream.print("主流输出B：");
        System.out.println(env.getExecutionPlan());

        //获取侧流
        DataStream<Tuple3<Integer, String, String>> sideOutput = processedStream.getSideOutput(ATag);

        env.execute();
    }
}
