import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import java.io.IOException;

public class HDFSClientWrite {
    public static void main(String[] args) throws IOException {
            Configuration conf = new Configuration();
            conf.set("fs.defaultFS","hdfs://192.168.142.10:9000");
            FileSystem fs = FileSystem.get(conf);	// 获取配置信息，创建FileSystem对象

            byte[] buff = "This is write file operating".getBytes(); // 要写入的内容
            String filename = "/test.txt"; //要写入的文件名
            FSDataOutputStream os = fs.create(new Path(filename));	// FileSystem创建输出流对象
            os.write(buff);
            System.out.println("Create:"+ filename);
            os.close();
            fs.close();
    }
}
