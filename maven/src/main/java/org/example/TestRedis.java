package org.example;

import redis.clients.jedis.Jedis;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Date: 2017/3/4.
 * Time: 下午 1:21.
 * Explain:Java操作Redis测试
 */
public class TestRedis {

    private Jedis jedis;

    public static void main(String[] args) {
        TestRedis testRedis = new TestRedis();
        testRedis.setJedis();
        testRedis.testMap();
        testRedis.testSet();
    }

    public void setJedis() {
        //连接redis服务器(在这里是连接本地的)
        jedis = new Jedis("10.10.108.71", 6379);
        //权限认证
        jedis.auth("123456");
        System.out.println("连接服务成功");
    }

    /**
     * Redis操作字符串
     */
    public void testString() {
        //添加数据
        jedis.set("name", "chx"); //key为name放入value值为chx
        System.out.println("拼接前:" + jedis.get("name"));//读取key为name的值

        //向key为name的值后面加上数据 ---拼接
        jedis.append("name", " is my name;");
        System.out.println("拼接后:" + jedis.get("name"));

        //删除某个键值对
        jedis.del("name");
        System.out.println("删除后:" + jedis.get("name"));

        //s设置多个键值对
        jedis.mset("name", "chenhaoxiang", "age", "20", "email", "chxpostbox@outlook.com");
        jedis.incr("age");//用于将键的整数值递增1。如果键不存在，则在执行操作之前将其设置为0。 如果键包含错误类型的值或包含无法表示为整数的字符串，则会返回错误。此操作限于64位有符号整数。
        System.out.println(jedis.get("name") + " " + jedis.get("age") + " " + jedis.get("email"));
    }

    public void testMap() {
        //添加数据
        Map<String, String> map = new HashMap<String, String>();
        for (int i = 0 ; i < 10 ; i++){
            map.put(String.valueOf(i) , String.valueOf(System.currentTimeMillis()));
            System.out.println(map.get(String.valueOf(i)));
        }
        jedis.hmset("time", map);
        System.out.println("========end=========");
    }

    /**
     * jedis操作List
     */
    public void testList(){
        //移除javaFramwork所所有内容
        jedis.del("javaFramwork");
        //存放数据
        jedis.lpush("javaFramework","spring");
        jedis.lpush("javaFramework","springMVC");
        jedis.lpush("javaFramework","mybatis");
        //取出所有数据,jedis.lrange是按范围取出
        //第一个是key，第二个是起始位置，第三个是结束位置
        System.out.println("长度:"+jedis.llen("javaFramework"));
        //jedis.llen获取长度，-1表示取得所有
        System.out.println("javaFramework:"+jedis.lrange("javaFramework",0,-1));

        jedis.del("javaFramework");
        System.out.println("删除后长度:"+jedis.llen("javaFramework"));
        System.out.println(jedis.lrange("javaFramework",0,-1));
    }

    /**
     * jedis操作Set
     */
    public void testSet(){
        //添加
        jedis.zadd("user",0,"chen");
        jedis.zadd("user",1,"chenhaoxiang");
        jedis.zadd("user",2,"hu");
        jedis.zadd("user",3,"chen");
        jedis.zadd("user",4,"chen4");
        jedis.zadd("user",5,"chen5");
        jedis.zadd("user",6,"chen6");
        jedis.zadd("user",7,"chen7");
    }

    public void testSortSet(){
        //添加
        jedis.sadd("user","chenhaoxiang");
        jedis.sadd("user","hu");
        jedis.sadd("user","chen");
        jedis.sadd("user","xiyu");
        jedis.sadd("user","chx");
        jedis.sadd("user","are");
        jedis.sadd("user","are");
        jedis.sadd("user","are");
    }

    /**
     * 排序
     */
    public void test(){
        jedis.del("number");//先删除数据，再进行测试
        jedis.rpush("number","4");//将一个或多个值插入到列表的尾部(最右边)
        jedis.rpush("number","5");
        jedis.rpush("number","3");

        jedis.lpush("number","9");//将一个或多个值插入到列表头部
        jedis.lpush("number","1");
        jedis.lpush("number","2");
        System.out.println(jedis.lrange("number",0,jedis.llen("number")));
        System.out.println("排序:"+jedis.sort("number"));
        System.out.println(jedis.lrange("number",0,-1));//不改变原来的排序
        jedis.del("number");//测试完删除数据
    }


}