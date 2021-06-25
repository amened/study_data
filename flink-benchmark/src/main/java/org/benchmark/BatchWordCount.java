package org.benchmark;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.operators.AggregateOperator;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.util.Collector;
import org.junit.Test;
import org.apache.flink.api.java.operators.DataSource;
import org.apache.flink.api.java.ExecutionEnvironment;

// 批处理word count
public class BatchWordCount {
    public static void main(String[] args)throws Exception {
        // 创建执行环境
        ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();

        // 从文件中读取数据
        String inputPath = Thread.currentThread().getContextClassLoader().getResource("hello.txt").getFile();
        DataSource<String> stringDataSource = env.readTextFile(inputPath);

        // 对数据集进行处理，按空格分词展开，转换成(word, 1)二元组进行统计
        AggregateOperator<Tuple2<String, Integer>> sum = stringDataSource
                .flatMap(new MyFlatMapper())
                .groupBy(0)
                .sum(1);
        sum.print();
    }

    public static class MyFlatMapper implements FlatMapFunction<String, Tuple2<String, Integer>>{
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

    @Test
    public void test() {
        System.out.println(Thread.currentThread().getContextClassLoader().getResource("hello.txt").getFile());
    }
}