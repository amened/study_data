package org.example;

import java.io.*;
import java.util.Properties;
import java.util.TreeMap;

/**
 * ---------------------1.store()----------
 * 一、2种不同的数据，Writer和InputStream,
 * comment 为注释,通常为 "" 空字符串
 * 1.void store(Writer writer ,String comment)
 *      写入字节，可不可中文，中文必然乱码
 * 2.void store(InputStream inputStream ,String comment)
 *      写入字符，可中文，推荐使用
 * 二、步骤
 * 1.创建对象
 * 2.添加数据 张三，李四，王五
 * 3.创建Writer对象
 * 4.使用 properties.store(writer,"");储存数据
 * 5.关闭writer.close()
 * ---------------------------2.load()--------------------------------
 * 一、 load(Reader reader)
 * 2种不同的数据，和 store使用一致，建议使用 Reader
 * 二、使用步骤：
 * 1.对象
 * 2.Reader
 * 3.load读取，键值文件。储值到对象里，不用返回
 * 4.Reader
 * 5.遍历properties输出
 * 三、注意：
 *   1.储值的文件中，键值的链接符号默认为，= 、空格
 *   2， # 井号为注释
 *   3.键值默认字符串，不用加 "" 引号
 *
 */

public class PropertyTest {
    public static void main(String[] args) throws IOException {
        TreeMap<Integer , String> stringIntegerTreeMap = new TreeMap<>();
        stringIntegerTreeMap.put(1,"1");
        stringIntegerTreeMap.put(23,"23");
        stringIntegerTreeMap.put(42,"42");
        stringIntegerTreeMap.put(12,"12");
        stringIntegerTreeMap.put(9,"9");

        for (Integer i : stringIntegerTreeMap.keySet()){
            System.out.println(stringIntegerTreeMap.get(i));
        }
    }
}

