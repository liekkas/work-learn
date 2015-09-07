/*
 * Copyright (c) www.ultrapower.com.cn
 */

package part4;

/**
 * Created by liekkas on 15/5/5.
 * 它是线程安全的无序的集合，可以将它理解成线程安全的HashSet。有意思的是，CopyOnWriteArraySet和HashSet虽然
 * 都继承于共同的父类AbstractSet；但是，HashSet是通过“散列表(HashMap)”实现的，而CopyOnWriteArraySet则是
 * 通过“动态数组(CopyOnWriteArrayList)”实现的，并不是散列表。

 和CopyOnWriteArrayList类似，CopyOnWriteArraySet具有以下特性：
 1. 它最适合于具有以下特征的应用程序：Set 大小通常保持很小，只读操作远多于可变操作，需要在遍历期间防止线程间的冲突。
 2. 它是线程安全的。
 3. 因为通常需要复制整个基础数组，所以可变操作（add()、set() 和 remove() 等等）的开销很大。
 4. 迭代器支持hasNext(), next()等不可变操作，但不支持可变 remove()等 操作。
 5. 使用迭代器进行遍历的速度很快，并且不会与其他线程发生冲突。在构造迭代器时，迭代器依赖于不变的数组快照
 */
import java.util.*;
import java.util.concurrent.*;

/*
 *   CopyOnWriteArraySet是“线程安全”的集合，而HashSet是非线程安全的。
 *
 *   下面是“多个线程同时操作并且遍历集合set”的示例
 *   (01) 当set是CopyOnWriteArraySet对象时，程序能正常运行。
 *   (02) 当set是HashSet对象时，程序会产生ConcurrentModificationException异常。
 *
 * @author skywang
 */
public class CopyOnWriteArraySetTest {

    // TODO: set是HashSet对象时，程序会出错。
//    private static Set<String> set = new HashSet<String>();
    private static Set<String> set = new CopyOnWriteArraySet<String>();
    public static void main(String[] args) {

        // 同时启动两个线程对set进行操作！
        new MyThread("ta").start();
        new MyThread("tb").start();
    }

    private static void printAll() {
        String value = null;
        Iterator iter = set.iterator();
        while(iter.hasNext()) {
            value = (String)iter.next();
            System.out.print(value+", ");
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
            while (i++ < 10) {
                // “线程名” + "-" + "序号"
                String val = Thread.currentThread().getName() + "-" + (i%6);
                set.add(val);
                // 通过“Iterator”遍历set。
                printAll();
            }
        }
    }
}
