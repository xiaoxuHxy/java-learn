package com.xk.line.array;

import com.xk.structure.line.AbstractList;

/**
 * 动态数组
 * 接口设计
 * int size()
 * int size();size();// 元素的数量
 * boolean isEmpty();isEmpty();// 是否为空
 * boolean contains(E elementelement); // 是否包含某个元素
 * void add(E elementelement); // 添加元素到最后面
 * E get( int indexindex); // 返回 index 位置对应的元素
 * E set( int index , E elementelement); // 设置 index 位置的元素
 * void add( int index , E elementelement); // 往index 位置添加元素
 * E remove( int indexindex); // 删除 index 位置对应的元素
 * int indexOf(E elementelement); // 查看元素的位置
 * void clear();clear();// 清除所有元素
 */
public class ArrayList<E> extends AbstractList<E> {

    private E[] elements;

    private static final int DEFAULT_CAPACITY = 10;

    public ArrayList(int capaticy) {
        capaticy = (capaticy < 10) ? DEFAULT_CAPACITY : capaticy;
        elements = (E[]) new Object[capaticy];
    }

    public ArrayList() {
        this(DEFAULT_CAPACITY);
    }

    public E get(int index) {
        rangeCheck(index);
        return elements[index];
    }

    public E set(int index, E element) {
        rangeCheck(index);
        E old = elements[index];
        elements[index] = element;
        return old;
    }

    public int indexOf(E element) {
        if (element == null) {
            for (int i = 0; i < size; i++) {
                if (elements[i] == null) {
                    return i;
                }
            }
        } else {
            for (int i = 0; i < size; i++) {
                if (element.equals(elements[i])) {
                    return i;
                }
            }
        }
        return ELEMENT_NOT_FOUND;
    }

    /**
     * 将栈中的指向设置为null，就会垃圾回收了
     */
    public void clear() {
        for (int i = 0; i < size; i++) {
            elements[i] = null;
        }
        size = 0;
        elements = (E[]) new Object[DEFAULT_CAPACITY];
    }

    /**
     * 最后面一个元素不用处理，让他们访问不到就行了
     *
     * @param index
     * @return
     */
    public E remove(int index) {
        rangeCheck(index);
        E old = elements[index];
        for (int i = index; i < size - 1; i++) {
            elements[i] = elements[i + 1];
        }
        size--;
        elements[size] = null;//清除最后一个
        trimCapacity();
        return old;
    }

    public void add(int index, E element) {
        rangeCheckForAdd(index);

        //扩容
        ensureCapacity(size + 1);


        for (int i = size - 1; i >= index; i--) {
            elements[i + 1] = elements[i];
        }
        elements[index] = element;
        size++;
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

    private void trimCapacity() {
        int oldCapacity = elements.length;
        if (size >= (oldCapacity >> 1) || oldCapacity <= DEFAULT_CAPACITY) return;
        int newCapacity = (oldCapacity >> 1);//使用浮点数运算速度比较低，右边移动是除以2，
        E[] newElements = (E[]) new Object[newCapacity];
        for (int i = 0; i < size; i++) {
            newElements[i] = elements[i];
        }
        elements = newElements;

        System.out.println("缩容 oldCapacity :" + oldCapacity + "newCapacity" + newCapacity);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[");
        for (int i = 0; i < size; i++) {
            if (i != 0) {
                stringBuilder.append(",");
            }
            stringBuilder.append(elements[i]);
        }
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

}
