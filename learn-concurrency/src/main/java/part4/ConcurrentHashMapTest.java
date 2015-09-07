/*
 * Copyright (c) www.ultrapower.com.cn
 */

package part4;

/**
 * Created by liekkas on 15/5/5.
 *
 * ConcurrentHashMap是线程安全的哈希表。HashMap, Hashtable, ConcurrentHashMap之间的关联如下：
 　　HashMap是非线程安全的哈希表，常用于单线程程序中。
 　　Hashtable是线程安全的哈希表，它是通过synchronized来保证线程安全的；即，多线程通过同一个“对象的同步锁”来
 实现并发控制。Hashtable在线程竞争激烈时，效率比较低(此时建议使用ConcurrentHashMap)！因为当一个线程访问
 Hashtable的同步方法时，其它线程就访问Hashtable的同步方法时，可能会进入阻塞状态。
 　　ConcurrentHashMap是线程安全的哈希表，它是通过“锁分段”来保证线程安全的。ConcurrentHashMap将哈希表分
 成许多片段(Segment)，每一个片段除了保存哈希表之外，本质上也是一个“可重入的互斥锁”(ReentrantLock)。多线程对
 同一个片段的访问，是互斥的；但是，对于不同片段的访问，却是可以同步进行的。


 */
import java.util.*;
import java.util.concurrent.*;

/*
 *   ConcurrentHashMap是“线程安全”的哈希表，而HashMap是非线程安全的。
 *
 *   下面是“多个线程同时操作并且遍历map”的示例
 *   (01) 当map是ConcurrentHashMap对象时，程序能正常运行。
 *   (02) 当map是HashMap对象时，程序会产生ConcurrentModificationException异常。
 *
 * @author skywang
 */
public class ConcurrentHashMapTest {

    // TODO: map是HashMap对象时，程序会出错。
//    private static Map<String, String> map = new HashMap<String, String>();
    private static Map<String, String> map = new ConcurrentHashMap<String, String>();
    public static void main(String[] args) {

        // 同时启动两个线程对map进行操作！
        new MyThread("ta").start();
        new MyThread("tb").start();
    }

    private static void printAll() {
        String key, value;
        Iterator iter = map.entrySet().iterator();
        while(iter.hasNext()) {
            Map.Entry entry = (Map.Entry)iter.next();
            key = (String)entry.getKey();
            value = (String)entry.getValue();
            System.out.print(key+" - "+value+", ");
        }
        System.out.println();
    }

    private static class MyThread extends Thread {
        MyThread(String name) {
            super(name);
        }
        @Override
        public void run() {
            int i = 0;
            while (i++ < 6) {
                // “线程名” + "-" + "序号"
                String val = Thread.currentThread().getName()+i;
                map.put(String.valueOf(i), val);
                // 通过“Iterator”遍历map。
                printAll();
            }
        }
    }
}
