/*
 * Copyright (c) www.ultrapower.com.cn
 */

package part4;

/**
 * Created by liekkas on 15/5/5.
 * ConcurrentSkipListMap是线程安全的有序的哈希表，适用于高并发的场景。
 ConcurrentSkipListMap和TreeMap，它们虽然都是有序的哈希表。但是，第一，它们的线程安全机制不同，
 TreeMap是非线程安全的，而ConcurrentSkipListMap是线程安全的。第二，ConcurrentSkipListMap是通过跳表实现的，
 而TreeMap是通过红黑树实现的。

 关于跳表(Skip List)，它是平衡树的一种替代的数据结构，但是和红黑树不相同的是，跳表对于树的平衡的实现是基于一种随机化
 的算法的，这样也就是说跳表的插入和删除的工作是比较简单的。
 */
import java.util.*;
import java.util.concurrent.*;

/*
 *   ConcurrentSkipListMap是“线程安全”的哈希表，而TreeMap是非线程安全的。
 *
 *   下面是“多个线程同时操作并且遍历map”的示例
 *   (01) 当map是ConcurrentSkipListMap对象时，程序能正常运行。
 *   (02) 当map是TreeMap对象时，程序会产生ConcurrentModificationException异常。
 *
 * 结果说明：
示例程序中，启动两个线程(线程a和线程b)分别对ConcurrentSkipListMap进行操作。以线程a而言，
它会先获取“线程名”+“序号”，然后将该字符串作为key，将“0”作为value，插入到ConcurrentSkipListMap中；
接着，遍历并输出ConcurrentSkipListMap中的全部元素。 线程b的操作和线程a一样，只不过线程b的名字和线程a的名字不同。
当map是ConcurrentSkipListMap对象时，程序能正常运行。如果将map改为TreeMap时，
程序会产生ConcurrentModificationException异常。


 * @author skywang
 */
public class ConcurrentSkipListMapTest {

    // TODO: map是TreeMap对象时，程序会出错。
//    private static Map<String, String> map = new TreeMap<String, String>();
    private static Map<String, String> map = new ConcurrentSkipListMap<String, String>();
    public static void main(String[] args) {

        // 同时启动两个线程对map进行操作！
        new MyThread("a").start();
        new MyThread("b").start();
    }

    private static void printAll() {
        String key, value;
        Iterator iter = map.entrySet().iterator();
        while(iter.hasNext()) {
            Map.Entry entry = (Map.Entry)iter.next();
            key = (String)entry.getKey();
            value = (String)entry.getValue();
            System.out.print("("+key+", "+value+"), ");
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
                // “线程名” + "序号"
                String val = Thread.currentThread().getName()+i;
                map.put(val, "0");
                // 通过“Iterator”遍历map。
                printAll();
            }
        }
    }
}
