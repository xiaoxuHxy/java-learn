package com.xk.line.queue;


import java.util.LinkedList;
import java.util.List;

/**
 * 队列
 * （可以想象成链表的开头和结尾）
 *
 * @param <E>
 */
public class Queue<E> {

    /**
     * 底层采用linkedList 因为这个是从头尾添加和删除的速度很快
     */
    private List<E> list = new LinkedList<E>();

    public int size() {
        return list.size();
    }

    public boolean isEmpty() {
        return list.isEmpty();
    }

    /**
     * 从尾部添加入队
     *
     * @param element
     */
    public void enQueue(E element) {
        list.add(element);
    }

    /**
     * 获取头部的元素
     *
     * @return
     */
    public E front() {
        return list.get(0);
    }

    /**
     * 从头部出队
     *
     * @return
     */
    public E deQueue() {
        return list.remove(0);
    }


}
