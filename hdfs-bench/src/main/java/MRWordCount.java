import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import java.io.IOException;
import java.util.Iterator;
import java.util.StringTokenizer;

public class MRWordCount {
    public MRWordCount() {
    }

    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();
        conf.set("fs.defaultFS","hdfs://192.168.142.10:9000");
        // 给一次MapReduce作业创建作业对象
        Job job = Job.getInstance(conf);

        // 给作业传入Map处理逻辑
        job.setMapperClass(MRWordCount.TokenizerMapper.class);
        // 给作业传入Reduce处理逻辑
        job.setReducerClass(MRWordCount.IntSumReducer.class);
        // 给作业设置key的输出类型
        job.setOutputKeyClass(Text.class);
        // 给作业设置value的输出类型
        job.setOutputValueClass(IntWritable.class);

        // 整个作业的在HDFS上的输入源位置，可以是具体路径，具体路径代表该路径下的所有文件，也可以是具体文件
        FileInputFormat.addInputPath(job, new Path("input"));
        // 整个作业的输出位置
        FileOutputFormat.setOutputPath(job, new Path("output"));
        // 提交这次作业到集群上
        // job.waitForCompletion(true);
    }

    // Reduce逻辑就是把这些1求和
    // 通过继承Reducer<KEYIN,VALUEIN,KEYOUT,VALUEOUT>并且重写reduce(KEYIN key, VALUEIN value, Context context)来实现每一个Reduce任务的逻辑
    // shuffle的Reduce任务得到了<单词，<1,1,1,1>>的<单词内容，1的list>
    // Reduce任务输出Text不变，但是IntWritable累加，需要在每一次的逻辑中设置输出值
    // 每一次的Reduce任务可以看成是一个继承Reducer对象运行了一次reduce函数，所以要通过设置MapReduce作业的Class来达到目的job.setReducerClass(WordCount.IntSumReducer.class);
    public static class IntSumReducer extends Reducer<Text, IntWritable, Text, IntWritable> {
        private IntWritable result = new IntWritable(); // Reduce任务的输出值，作为VALUEOUT

        public IntSumReducer() {
        }

        public void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
            int sum = 0;
            IntWritable val;
            // Iterator是shuffle之后value-list表中的迭代器
            for(Iterator shuffle_valuelist = values.iterator(); shuffle_valuelist.hasNext(); sum += val.get()) {
                val = (IntWritable)shuffle_valuelist.next(); // 迭代器指向下一个value-list的内容
            }
            // 每一次循环，设置Reduce任务的VALUEOUT
            this.result.set(sum);
            // 每一次循环，将<单词内容，出现和>输出保存起来，直到Reduce任务执行一次，得到结果。
            context.write(key, this.result);
        }
    }

    // 通过继承Mapper<KEYIN,VALUEIN,KEYOUT,VALUEOUT>并且重写map(Object key, Text value, Context context)来实现每一个Map任务的逻辑
    // 文本的每一行分成splite
    // map任务的输入为<行号,内容>,也就是<Object ,Text >，实际用不到行号
    // map任务的输出为<单词内容,1>，也就是<Text,IntWritable>因为Map只要统计每一次单词出现的个数作为输出，剩下交给shuffle来完成归并为<单词内容，1的list>
    // Text,IntWritable相当于java中String和Integer，只不过在MapReduce计算过程中的中间结果都是用的MapReduce自己定义的数据类型
    // 每一次的Map任务可以看成是一个继承Mapper对象运行了一次map函数，所以要通过设置MapReduce作业的Class来达到目的job.setMapperClass(WordCount.TokenizerMapper.class)
    public static class TokenizerMapper extends Mapper<Object, Text, Text, IntWritable> {
        private static final IntWritable one = new IntWritable(1);	// 每一次map单词出现的个数，也就是1，表示这个单词出现过，作为VALUEOUT
        private Text word = new Text();		// 每一次运行map结果存放的单词，作为KEYOUT
        public TokenizerMapper() {
        }
        public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
            // JDK自带分词器API，将输入的每一行作为字符串创建分词器对象
            StringTokenizer itr = new StringTokenizer(value.toString());
            // 判断这个字符串中是否有下一个单词
            while(itr.hasMoreTokens()) {
                // 每一次循环，设置输出key的内容
                this.word.set(itr.nextToken());
                // 每一次循环，将map函数的内容直接输出为<Text,IntWritable>作为中间结果，保存起来，Context是内部类，直接使用
                context.write(this.word, one);
            }
        }
    }
}

