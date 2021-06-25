package org.example;

import org.apache.zookeeper.AsyncCallback;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.concurrent.CountDownLatch;

public class ZKExists {
    String IP = "10.10.108.72:2181";
    ZooKeeper zookeeper;
    @Before
    public void before() throws Exception {
        final CountDownLatch countDownLatch = new CountDownLatch(1);
            // arg1:zookeeper服务器的ip地址和端口号
            // arg2:连接的超时时间,以毫秒为单位
            // arg3:监听器对象
                zookeeper = new ZooKeeper(IP, 5000, new Watcher() {
            @Override
            public void process(WatchedEvent event) {
                if (event.getState() == Event.KeeperState.SyncConnected)
                {
                    System.out.println("连接创建成功!");
                    countDownLatch.countDown();
                }
            }
        });
        // 使主线程阻塞等待
        countDownLatch.await();
    }
    @After
    public void after() throws Exception {
        zookeeper.close();
    }

    @Test
    public void exists1() throws Exception {
        // arg1:节点的路径
        Stat stat = zookeeper.exists("/exists1", false);
        System.out.println(stat.getVersion());
    }

    @Test
    public void exists2() throws Exception {
        // 异步使用方式
        zookeeper.exists("/exists1", false, new
                AsyncCallback.StatCallback() {
                    @Override
                    public void processResult(int rc, String path, Object ctx, Stat stat) {
                        // 判断成功
                        System.out.println(rc);
                        // 路径
                        System.out.println(path);
                        // 上下文参数
                        System.out.println(ctx);
                        // null 节点不存在
                        System.out.println(stat.getVersion());
                    }
                }, "I am Context");
        Thread.sleep(5000);
        System.out.println("结束");
    }
}