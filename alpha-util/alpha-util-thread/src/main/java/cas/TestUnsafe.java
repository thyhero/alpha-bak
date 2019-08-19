package cas;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * Created by TangHaiyang on 2019/8/19.
 */
public class TestUnsafe {

    public static void main(String[] args) {
        Node node = new Node();
        node.seq=10;

        Node node2 = new Node();
        node2.seq=20;

        Node node3 = new Node();
        node3.seq=20;

        System.out.println(node2.equals(new Node()));
        System.out.println(node2.equals(node3));

        /**
         * 原子操作, 通过CAS方法更新node的next属性
         * 是否等于null，等于则用new node()替换并返回true，否则不做任何操作并返回false
         * 在jdk源码的下面几个集合中广泛用到了compareAndSwapObject，通常是跟null对比然后替换为指定的对象
         * ConcurrentHashMap
         * ConcurrentLinkedQueue
         * ConcurrentSkipListMap
         * ConcurrentLinkedDeque
         * SynchronousQueue
         */
        boolean flag1 = node.casNext(null,new Node());
        boolean flag2 = node.casNext(null,new Node());
        boolean flag3 = node.casNext(node2,new Node());
        System.out.println(flag1);
        System.out.println(flag2);
        System.out.println(flag3);
    }

    public static class Node{
        volatile Node next;
        volatile int seq;
        private static final sun.misc.Unsafe UNSAFE;
        private static final long nextOffset;
        static {
            try {
                UNSAFE = getUnsafe();
                Class<?> k = Node.class;
                nextOffset = UNSAFE.objectFieldOffset(k.getDeclaredField("next"));
            } catch (Exception e) {
                throw new Error(e);
            }
        }

        /**
         * 使用Unsafe CAS方法
         * @param cmp 目标值与cmp比较equal方法，如果相等就更新返回true；如果不相等就不更新返回false；
         * @param val 需要更新的值；
         * @return
         */
        boolean casNext(Node cmp, Node val) {
            /**
             * compareAndSwapObject(Object var1, long var2, Object var3, Object var4)
             * var1 操作的对象
             * var2 操作的对象属性
             * var3 var2与var3比较，相等才更新
             * var4 更新值
             */
            return UNSAFE.compareAndSwapObject(this, nextOffset, cmp, val);
        }

        /**
         * 获取Unsafe的方法
         * 获取了以后就可以愉快的使用CAS啦
         * @return
         */
        private static Unsafe getUnsafe() {
            try {
                Field f = Unsafe.class.getDeclaredField("theUnsafe");
                f.setAccessible(true);
                return (Unsafe)f.get(null);
            } catch (Exception e) {
                return null;
            }
        }
    }
}