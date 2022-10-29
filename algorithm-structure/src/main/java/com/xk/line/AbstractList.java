package com.xk.line;

/**
 * 无论list还是linked都是具有一些公共的接口这个则是放置一些公共的接口
 *
 * @param <E>
 */
public abstract class AbstractList<E> implements List<E> {

    protected static final int ELEMENT_NOT_FOUND = -1;
    /**
     * 元素的大小
     */
    protected int size;

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean contains(E element) {
        return indexOf(element) != ELEMENT_NOT_FOUND;
    }

    /**
     * 先思考从0开始的
     *
     * @param element
     */
    public void add(E element) {
        add(size, element);
    }

    protected void rangeCheckForAdd(int index) {
        if (index < 0 || index > size) {
            outOfBounds(index);
        }
    }

    protected void rangeCheck(int index) {
        if (index < 0 || index >= size) {
            outOfBounds(index);
        }
    }

    protected void outOfBounds(int index) {
        throw new IndexOutOfBoundsException("index =" + index + ",+ size = " + size);
    }
}
