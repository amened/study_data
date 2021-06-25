package org.example;

import java.util.Scanner;
public class Main{
    private static int threadIndex = 0;
    private static volatile int count = 0;
    private static Object object = new Object();
    public static void main(String[] args) {
        // 测试部分
//        new Thread(() -> { printByOrder(0);} , "Thread" + 0).start();
//        new Thread(() -> { printByOrder(1);} , "Thread" + 1 ).start();
//        new Thread(() -> { printByOrder(2);} , "Thread" + 2 ).start();
//        new Thread(new MyRunnable(0 , 4) , "Thread" + 0 ).start();
//        new Thread(new MyRunnable(1 , 4) , "Thread" + 1 ).start();
//        new Thread(new MyRunnable(2 , 4) , "Thread" + 2 ).start();
//        new Thread(new MyRunnable(3 , 4) , "Thread" + 3 ).start();
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        for (int i = 0 ; i < n ; i ++){
            new Thread(new MyRunnable(i , n)).start();
        }
    }

    public static class MyRunnable implements Runnable{
        public int i;
        public int n;
        public MyRunnable (int i , int n) {
            this.i = i;
            this.n = n ;
        }

        @Override
        public void run() {
            printByOrder(i , n);
        }

        public static void printByOrder(int i , int n){
            while (count < 100){
                if (threadIndex %  n == i){
                    synchronized (object){
                        threadIndex ++;
                        System.out.println(Thread.currentThread().getName() + ":" + count);
                        count ++;
                    }
                }
            }
        }
    }
}


