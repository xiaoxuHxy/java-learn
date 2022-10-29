package com.xk.heap;

import com.xk.structure.tree.binary.printer.BinaryTreeInfo;

import java.util.Comparator;

/**
 * 堆顶部的元素的索引是0
 *
 * @param <E>
 */
public class BinaryHeap<E> implements BinaryTreeInfo {
    private E[] elements;

    private int size;

    private static final int DEFAULT_CAPACITY = 10;

    private Comparator comparator;

    public BinaryHeap() {
        this(null);
    }

    public BinaryHeap(Comparator<E> comparator) {
        this.comparator = comparator;
        this.elements = (E[]) new Object[DEFAULT_CAPACITY];
    }

    public void clear() {
        for (int i = 0; i < size; i++) {
            elements[i] = null;
        }
        size = 0;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public E remove() {
        E old = elements[0];
        elements[0] = elements[size - 1];
        elements[size - 1] = null;
        size--;
        siftDown(0);
        return old;
    }

    //删除一个顶部元素,并且插入顶部元素
    public void replace(E element) {
        elements[0] = element;
        siftDown(0);
    }


    private void siftDown(int index) {
//        叶子节点个数 n0 = floor( (n + 1) / 2 ) = ceiling( n / 2 2)
        //todo 完全二叉树 非叶子节点数量是 floor(n/2)  非叶子节点数量是 floor((n+1)/2) 叶子节点多
        //左边子树的索引是 index * 2 +1  右边子树索引则是index * 2+2
        E element = elements[index];
        int half = size >> 1;
        while (index < half) {
            int childIndex = (index << 1) + 1;  //上面限定过了,索引左边子树一定有值的

            E child = elements[childIndex];
            int right = (index << 1) + 2;
            if (right < size && compare(child, elements[right]) < 0) {
                childIndex = right;
                child = elements[right];
            }

            if (compare(element, child) >= 0) break;


            elements[index] = child;


            index = childIndex;
        }
        elements[index] = element;

    }

    //批量建堆,就是给你一个数组,直接变成堆
    //自上而下的上滤 相当于一个一个添加进去
    //自下而上的下滤
    public void batch() {

    }

    /**
     * 获取堆顶元素
     *
     * @return
     */
    public E get() {
        if (size == 0) {
            //todo 这里要注意
        }
        return elements[0];
    }

    /**
     * 先添加到索引最后面,
     * 然后上滤
     *
     * @param element
     */

    public void add(E element) {
        //先添加到最后面
        ensureCapacity(size + 1);
        elements[size] = element;
        size++;
        siftUp(size - 1);
    }

    private void siftUp(int index) {
        E e = elements[index];
        while (index > 0) {
            int pIndex = (index - 1) >> 1;
            E p = elements[pIndex];
            if (compare(e, p) <= 0) break;

            elements[index] = p;
            index = pIndex;
        }
        elements[index] = e;
    }


    private int compare(E e1, E e2) {
        if (comparator != null) return comparator.compare(e1, e2);
        return ((Comparable<E>) e1).compareTo(e2);
    }


    private void ensureCapacity(int capacity) {
        int oldCapacity = elements.length;
        if (oldCapacity >= capacity) return;
        int newCapacity = oldCapacity + (oldCapacity >> 1);//使用浮点数运算速度比较低，右边移动是除以2，
        E[] newElements = (E[]) new Object[newCapacity];
        for (int i = 0; i < size; i++) {
            newElements[i] = elements[i];
        }
        elements = newElements;
        System.out.println("扩容:" + "old = " + oldCapacity + ",new = " + newCapacity);
    }


    @Override
    public Object root() {
        return 0;
    }

    @Override
    public Object left(Object node) {
        int index = (int) node;
        int left = (index << 1) + 1;
        return left >= size ? null : left;
    }

    @Override
    public Object right(Object node) {
        int index = (int) node;
        int right = (index << 1) + 2;
        return right >= size ? null : right;
    }

    @Override
    public Object string(Object node) {
        int index = (int) node;
        return elements[index];
    }
}
