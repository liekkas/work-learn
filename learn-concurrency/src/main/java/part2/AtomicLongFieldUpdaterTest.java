/*
 * Copyright (c) www.ultrapower.com.cn
 */

package part2;

import java.util.concurrent.atomic.AtomicLongFieldUpdater;

/**
 * Created by liekkas on 15/5/4.
 * AtomicIntegerFieldUpdater, AtomicLongFieldUpdater和AtomicReferenceFieldUpdater
 * 这3个修改类的成员的原子类型的原理和用法相似。本章以对基本类型的原子类进行介绍
 *
 * AtomicLongFieldUpdater可以对指定"类的 'volatile long'类型的成员"进行原子更新。它是基于反射原理实现的。
 */
public class AtomicLongFieldUpdaterTest {
    public static void main(String[] args) {

        // 获取Person的class对象
        Class cls = Person2.class;
        // 新建AtomicLongFieldUpdater对象，传递参数是“class对象”和“long类型在类中对应的名称”
        AtomicLongFieldUpdater mAtoLong = AtomicLongFieldUpdater.newUpdater(cls, "id");
        Person2 person = new Person2(12345678L);

        // 比较person的"id"属性，如果id的值为12345678L，则设置为1000。
        mAtoLong.compareAndSet(person, 12345678L, 1000);
        System.out.println("id="+person.getId());
    }
}

class Person2 {
    volatile long id;
    public Person2(long id) {
        this.id = id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public long getId() {
        return id;
    }
}

