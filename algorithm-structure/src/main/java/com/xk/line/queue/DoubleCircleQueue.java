package com.xk.line.queue;

/**
 * 循环双端队列
 * 注意：少用加减乘除运算，以及cpu运算。这些太消耗cpu
 * 优化公式
 * 当 n>=0,m>0
 * n%m 等价于n-(m>n?0:m)的前提条件是n<2m
 *
 * @param <E>
 */
public class DoubleCircleQueue<E> {

    /**
     * 存储对头元素的下标
     */
    private int front;

    private int size;

    private E[] elements;

    private static final int DEFAULT_CAPACITY = 10;

    public DoubleCircleQueue() {
        elements = (E[]) new Object[DEFAULT_CAPACITY];
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * 尾部添加
     *
     * @param element
     */
    public void enQueueRear(E element) {
        ensure(size + 1);
        elements[index(size)] = element;
        size++;
    }

    /**
     * 头部出队
     *
     * @return
     */
    public E deQueueFront() {
        E frontElement = elements[front];
        elements[front] = null;
        front = index(1);
        size--;
        return frontElement;
    }

    /**
     * 头部添加
     * 这里注意一下，如果是在头部添加则是-1 负数则是加上length就行了
     *
     * @param element
     */
    public void enQueueFront(E element) {
        ensure(size + 1);
        elements[index(-1)] = element;
        size++;
    }

    public E deQueueRear() {
        int index = index(size - 1);
        E rear = elements[index];
        elements[index] = null;
        size--;
        return rear;
    }

    public E front() {
        return elements[front];
    }

    public E rear() {
        return elements[index(size - 1)];
    }

    public void clear() {
        for (int i = 0; i < size; i++) {
            elements[index(i)] = null;
        }
        size = 0;
        front = 0;
    }

    /**
     * 处理传统意义上的索引和循环队列上的映射
     *
     * @param index
     * @return
     */
    private int index(int index) {
//        index = index + front;
        if (index < 0) {
            return index + elements.length;
        }
//        return index % elements.length;
        index += front;
        return index - (index >= elements.length ? elements.length : 0);
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
}
