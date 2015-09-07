/*
 * Copyright (c) www.ultrapower.com.cn
 */

package pc.v1;

/**
 * Created by liekkas on 15/5/4.
 */
public class Depot {

    private int capacity;
    private int size;

    public Depot(int capacity) {
        this.capacity = capacity;
    }

    public synchronized void produce(int value){
        try {
            int left = value; //表示想要生产的数量（有可能生产数量太大，仓库放不下，需要多次生产）

            while (left > 0){
                //当仓库满了，等待消费者消费产品
                while (size >= capacity){
                    wait();
                }

                //如果库存+想要生产的数量大于总容量，则实际增量 = 总容量减去当前容量（此时将满库）
                //否则实际增量就等于想要生产的数量
                int inc = (size + left) > capacity ? (capacity - size) : left;
                size += inc;
                left -= inc;

                System.out.printf("%s produce(%3d) --> left=%3d,inc=%3d,size=%3d\n",
                        Thread.currentThread().getName(),value,left,inc,size);

                //通知消费者可以消费了
                notifyAll();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public synchronized void consume(int value){
        try {
            int left = value; //表示客户要消费的数量（有可能消费数量太大，库存不够，需要多次消费）
            while (left > 0){
                //仓库空了，等待生产者生产产品
                while (size <= 0){
                    wait();
                }

                //获取实际消费的数量
                int dec = (size < left) ? size : left;
                left -= dec;
                size -= dec;

                System.out.printf("%s consume(%3d) <-- left=%3d,dec=%3d,size=%3d\n",
                        Thread.currentThread().getName(),value,left,dec,size);

                //通知生产者可以生产了
                notifyAll();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
