package com.xk.line.queue;

/**
 * 循环队列
 */
public class CircleQueue<E> {
    private int size;

    /**
     * 存储对头元素的下标
     */
    private int front;

    private E[] elements;

    private static final int DEFAULT_CAPACITY = 10;

    public CircleQueue() {
        elements = (E[]) new Object[DEFAULT_CAPACITY];
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void enQueue(E element) {
        ensure(size + 1);
        elements[index(size)] = element;
        size++;
    }

    /**
     * 传递之前的索引，返回循环队列上的索引
     *
     * @param index
     * @return
     */
    private int index(int index) {
        return (front + index) % elements.length;
    }

    private void ensure(int capacity) {
        int oldCapacity = elements.length;
        if (oldCapacity >= capacity) return;

        int newCapacity = oldCapacity + (oldCapacity >> 1);//使用浮点数运算速度比较低，右边移动是除以2，
        E[] newElements = (E[]) new Object[newCapacity];

        for (int i = 0; i < size; i++) {
            newElements[i] = elements[index(i)];
        }
        elements = newElements;
        System.out.println("扩容:" + "old = " + oldCapacity + ",new = " + newCapacity);
        front = 0;
    }

    /**
     * 注意取模运算  就是有循环的情况下
     *
     * @return
     */
    public E deQueue() {
        E frontElement = elements[front];
        elements[front] = null;
        front = index(1);
        size--;
        return frontElement;
    }

    /**
     * 返回对头元素
     *
     * @return
     */
    public E front() {
        return elements[front];
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[");
        for (int i = 0; i < elements.length; i++) {
            if (i != 0) {
                stringBuilder.append(",");
            }
            stringBuilder.append(elements[i]);
        }
        stringBuilder.append("]");
        return stringBuilder.toString();
    }
}
