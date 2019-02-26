package demo;

import java.lang.reflect.Proxy;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * （描述）
 *
 * @author tangzw
 * @date 2019-02-18
 * @since (版本)
 */
public class Demo {

//    static {
//        System.out.println("static");
//    }
//
//    {
//        System.out.println("sd");
//    }
//
//    public Demo(){
//        System.out.println("demo");
//    }

    ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();
    Lock readLock = reentrantReadWriteLock.readLock();
    Lock writeLock = reentrantReadWriteLock.writeLock();
    Lock lock = new ReentrantLock();
    Lock lock2 = new ReentrantLock();
    Condition condition1 = lock.newCondition();
    Condition condition2 = lock.newCondition();

    public synchronized void say(){
        System.out.println("say");
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public synchronized void sql(){
        System.out.println("sql");
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void runExecutorService(){
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                runLock();
            }
        };

        Runnable runnable2 = new Runnable() {
            @Override
            public void run() {
                runLock2();
            }
        };

        ExecutorService executorService = Executors.newCachedThreadPool();

        for (int i=0;i<1;i++){
            executorService.execute(runnable2);
        }
        sleep(2000);
        System.out.println("~~~~~~~~~~~~~~");
        for (int i=0;i<1;i++){
            executorService.execute(runnable);
        }
    }




    public void runLock(){
        String name = Thread.currentThread().getName();

        try {
            readLock.lock();
            lock.lock();
            System.out.println(name + "~读锁：已经锁住");
//            runLock2();
//            condition1.await();
//            System.out.println(name + "~读锁：已经await");
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            System.out.println(name + "~读锁：已经释放");
            readLock.unlock();
        }

    }

    public void runLock2(){
        String name = Thread.currentThread().getName();

        try {
            lock.lock();

            System.out.println(name + "~写锁：已经锁住");
//            runLock();
            sleep(100000);
//            condition1.signal();
//            System.out.println(name + "~读锁：已经signal");
        }finally {
            System.out.println(name + "~写锁：已经释放");
            writeLock.unlock();
        }

    }

    public void concurrentHashMap(){
        ConcurrentHashMap map = new ConcurrentHashMap();
        map.put("1","1");

        map.get("");
        map.remove("");
        map.size();
        map.entrySet();
        ConcurrentLinkedDeque list = new ConcurrentLinkedDeque();
        list.add("");
        list.getFirst();
        list.poll();
        list.remove();
        LinkedBlockingDeque blockingDeque = new LinkedBlockingDeque();
        blockingDeque.add("");
        blockingDeque.pollFirst();

    }

    public void myInvocationHandler(){
        MyInvocationHandler handler = new MyInvocationHandler(new HelloService());
        Hello hello = (Hello) Proxy.newProxyInstance(getClass().getClassLoader(),HelloService.class.getInterfaces(),handler);

        hello.say();

    }

    public void match(){
        String regex = "(\\w+)(3)c";
        String str = "bc hel mc3c";


        Pattern pattern = Pattern.compile(regex);

        Matcher matcher = pattern.matcher(str);

        while (matcher.find()){
            System.out.println(matcher.group());
            System.out.println(matcher.group(1));
            System.out.println(matcher.group(2));
        }

    }

    public StringBuilder getNum(){
        StringBuilder stringBuilder = new StringBuilder("sd");
        try {
            return stringBuilder;
        }finally {
            stringBuilder.append("sd52");
        }
    }
    public static void main(String[] args) {
        Demo demo = new Demo();

        System.out.println(demo.getNum());

    }

    public static void sleep(int millis){
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
