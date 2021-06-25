package org.example;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import java.util.concurrent.CountDownLatch;

public class ZookeeperConnect {
    public static void main(String[] args) {
        try {
            // 计数器对象
            final CountDownLatch countDownLatch=new CountDownLatch(1);
            // arg1:服务器的ip和端口
            // arg2:客户端与服务器之间的会话超时时间,以毫秒为单位
            // arg3:监视器对象，捕获客户端，因为调用ZooKeeper构造函数和process方法被执行是在两个线程中，需要利用信号量来同步两个操作。
            ZooKeeper zooKeeper=new ZooKeeper("10.10.108.71:2181",
                    6000, new Watcher() {
                @Override
                public void process(WatchedEvent event) {
                    if(event.getState()==Event.KeeperState.SyncConnected)
                    {
                        System.out.println("连接创建成功!");
                        countDownLatch.countDown();
                    }
                }
            });
            // 主线程阻塞等待连接对象的创建成功
            countDownLatch.await();
            // 会话编号
            System.out.println(zooKeeper.getSessionId());
            zooKeeper.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
