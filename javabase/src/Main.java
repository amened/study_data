import javax.swing.*;
import java.util.concurrent.FutureTask;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.List;
import java.util.ArrayList;
/*
 * 使用wait方法和notify方法实现生产者消费者模式
 * 生产线程负责生产 , 消费线程负责消费
 * 生产线程和消费线程要达到均衡
 *
 * wait和notify方法不是线程对象的方法,是普通java对象都有的方法
 * wait方法和notify方法建立在线程同步的基础之上.因为线程同时操作一个仓库.有线程安全问题
 * wait方法作用:o.wait()是让正在o对象上活动的线程t进入等待状态,并且释放掉t线程之前占有的o对象的锁
 * notify方法的作用:o.notify()让正在o对象上等待的线程唤醒,只是通知,不会释放o对象上之前占有的锁
 *
 *
 * 模拟:
 * 仓库用List集合
 * 1个代表仓库满了,0表示仓库空了
 */
public class Main {
    public static void main(String[] args){
        List list = new ArrayList();

        Thread t1 = new Thread(new Prodeucer(list));
        Thread t2 = new Thread(new Consumer(list));
        t1.setName("生产者线程");
        t2.setName("消费者线程");
        t1.start();
        t2.start();
    }
}

class Prodeucer implements Runnable{
    private List list;
    public Prodeucer(List list) {
        this.list = list;
    }
    @Override
    public void run() {
        int i = 0;
        while(i <= 10) {
            synchronized (list) {
                if(list.size() > 0 ) {		// 仓库中有元素
                    try {
                        // 满了,需要等待,并且释放掉Producer之前占有的list锁
                        list.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                // 到这里残酷肯定是空,要进行生产
                Object obj = new Object();
                list.add(obj);
                System.out.println(Thread.currentThread().getName()+ "--->" + obj);
                // 唤醒消费者进行消费
                list.notifyAll();
            }
            i++;
        }
    }
}

class Consumer implements Runnable{
    private List list;
    public Consumer(List list) {
        this.list = list;
    }
    @Override
    public void run() {
        int i = 0;
        while(i <= 10) {
            synchronized (list) {
                if(list.size()== 0) {
                    try {
                        list.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                Object obj = list.remove(0);
                System.out.println(Thread.currentThread().getName()+ "--->" + obj);
                // 唤醒生产者生产
                list.notifyAll();
                // list.notifyAll();
            }
            i++;
        }
    }
}