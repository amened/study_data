package org.example;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.concurrent.CountDownLatch;
public class ZKSet {
    String IP = "10.10.108.71:2181";
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
    public void set1() throws Exception {
    // arg1:节点的路径
    // arg2:节点修改的数据
    // arg3:更新操作中的版本参数如果为-1，则表示更新操作针对任何版本均可。当更新版本不为-1，且不等于节点的目前版本，则更新失败。
        Stat stat=zookeeper.setData("/set/node1","node13".getBytes(),-1);
    // 节点的版本号
        System.out.println(stat.getVersion());
    // 节点的创建时间
        System.out.println(stat.getCtime());
    }

    @Test
    public void set2() throws Exception {
    // 异步方式修改节点
        zookeeper.setData("/set/node2", "node21".getBytes(), -1,
                new AsyncCallback.StatCallback() {
                    @Override
                    public void processResult(int rc, String path, Object ctx,
                                              Stat stat) {
                            // 0 代表修改成功
                        System.out.println(rc);
                            // 修改节点的路径
                        System.out.println(path);
                            // 上线文的参数对象
                        System.out.println(ctx);
                            // 的属性信息
                        System.out.println(stat.getVersion());
                    }
                },"I am Context");
        Thread.sleep(50000);
        System.out.println("结束");
    }
}
