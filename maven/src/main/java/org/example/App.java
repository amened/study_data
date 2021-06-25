package org.example;
import redis.clients.jedis.Jedis;
/**
 * Java客户端Jedis连接Redis数据库
 *
 * @author liuhefei
 * 2018年9月16日
 */
public class App {
    public static void main(String[] args) {
        //创建Jedis实例，连接本地Redis服务
        Jedis jedis = new Jedis("10.10.108.71",6379);
        jedis.auth("123456");
        System.out.println("连接成功");
        //查看服务是否运行
        System.out.println("服务正在运行: "+jedis.ping());
    }
}