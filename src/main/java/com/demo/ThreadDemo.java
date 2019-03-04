package com.demo;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * （描述）
 *
 * @author tangzw
 * @date 2019-02-22
 * @since (版本)
 */
public class ThreadDemo {

    public static void main(String[] args) {

        ThreadDemo threadDemo = new ThreadDemo();
        threadDemo.runCountDownLatch();
    }

    public void runCountDownLatch() {
        CountDownLatch countDownLatch = new CountDownLatch(4);
        CyclicBarrier cyclicBarrier = new CyclicBarrier(4);
        Exchanger exchanger = new Exchanger();
        Semaphore semaphore = new Semaphore(4);

        Runnable runnable = getRunnable(semaphore,2);
        ExecutorService executorService = Executors.newCachedThreadPool();

        for (int i = 0; i < 4; i++) {
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    runSyn();
                }
            });
        }

        System.out.println("执行结束");


    }

    public void runSyn()  {
        String str = "sd";

        synchronized (str){
            String threadName = Thread.currentThread().getName();

            System.out.println(threadName);
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println(threadName);
        }
    }

    public void runTimeThread(){
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                System.out.println(1);
            }
        };

        Timer timer = new Timer();

        timer.schedule(timerTask,1);

        ExecutorService executorService = Executors.newFixedThreadPool(1);
    }
    public Runnable getRunnable(Semaphore semaphore, int n) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                String name = Thread.currentThread().getName();

                System.out.println(name + " : start");

                try {
                    semaphore.acquire(n);
                    System.out.println(name + "获取了" + n + "个信号量");
                    Thread.sleep(1000);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    semaphore.release(n);
                }

            }
        };

        return runnable;
    }

    public Runnable getRunnable(Exchanger exchanger) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                String name = Thread.currentThread().getName();

                System.out.println(name + " : start");

                try {
                    String targetName = (String) exchanger.exchange(name);
                    System.out.println(name + "接受到了：" + targetName);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        };

        return runnable;
    }

    public Runnable getRunnable(CyclicBarrier cyclicBarrier) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                String name = Thread.currentThread().getName();

                System.out.println(name + " : start");

                try {
                    cyclicBarrier.await();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                System.out.println(name + " : end");
            }
        };

        return runnable;
    }

    public Runnable getRunnable(CountDownLatch countDownLatch) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                String name = Thread.currentThread().getName();

                System.out.println(name + " : start");

                try {
                    countDownLatch.countDown();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                System.out.println(name + " : end");
            }
        };

        return runnable;
    }


}
