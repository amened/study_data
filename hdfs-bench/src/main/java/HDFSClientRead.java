import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class HDFSClientRead {
    public static void main(String[] args) throws Exception{
        Configuration conf = new Configuration();
        conf.set("fs.defaultFS","hdfs://192.168.142.10:9000"); // 通过此类设置NameNode的值
        FileSystem fs = FileSystem.get(conf);		// 通过配置类实现FileSystem

        Path file = new Path("test");  // HDFS中的user路径，也可以加默认的绝对路径。
        if (fs.exists(file)){
            FSDataInputStream getIt = fs.open(file);  // 打开输入流，创建了输入流对象
            BufferedReader d = new BufferedReader(new InputStreamReader(getIt));
            String content = d.readLine(); //读取文件一行
            System.out.println(content);
            d.close(); //关闭文件
        }else {
            System.out.println(file.toString() + "not exists");
        }
        fs.close(); //关闭hdfs
    }
}

