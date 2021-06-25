package org.benchmark;

import org.apache.flink.api.common.functions.RichMapFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

public class TransFormRichFunction {
    public static void main(String[] args) throws Exception{
        // 创建执行环境
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(2);

        // 读取文件数据
        String inputPath = Thread.currentThread().getContextClassLoader().getResource("sensor.txt").getFile();
        DataStreamSource<String> stringDataStreamSource = env.readTextFile(inputPath);

        // map操作
        SingleOutputStreamOperator<SensorReading2> map = stringDataStreamSource.map((String value) -> {
            String[] fields = value.split(",");
            return new SensorReading2(fields[0], new Long(fields[1]), new Double(fields[2]));
        }).setParallelism(2);

        map.map(new RichMapFunction<SensorReading2, Object>() {
            @Override
            public Object map(SensorReading2 value) throws Exception {
                // 返回任务序号
                return new Tuple2<>(value.getId(), getRuntimeContext().getIndexOfThisSubtask());
            }

            @Override
            public void open(Configuration parameters) throws Exception {
                // 初始化工作，一般是定义状态，或者建立数据库连接
                // 返回子任务的索引
                System.out.println(getRuntimeContext().getIndexOfThisSubtask() + "open");
            }

            @Override
            public void close() throws Exception {
                // 关闭连接，清空状态等收尾操作
                // 返回子任务的索引
                System.out.println(getRuntimeContext().getIndexOfThisSubtask() + "close");
            }
        }).print();


        // 启动Job
        env.execute();
    }
}
