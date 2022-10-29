package com.xk.line.linked;

import com.xk.structure.line.AbstractList;

/**
 * 双向循环链表
 * 新增方法发挥威力
 *
 * @param <E>
 */
public class DoubleCircleLinkedList<E> extends AbstractList<E> {

    private Node<E> first;

    private Node<E> last;


    /**
     * 引入三个函数一个变量解决约瑟夫环问题
     * current : 用于指向某个节点
     * reset():让current指向头部节点
     * next():让current往后面走一步，即代表current =current.next()
     * remove() 删除current指向的节点。
     *
     * @param index
     * @return
     */
    private Node<E> current;

    public void reset() {
        current = first;
    }

    public E next() {
        if (current == null) return null;
        current = current.next;
        return current.elemenet;
    }

    public E remove() {
        if (current == null) return null;
        Node<E> next = current.next;
        E element = remove(current);
        if (size == 0) {
            current = null;
        } else {
            current = next;
        }
        return element;
    }

    private E remove(Node<E> node) {
        if (size == 1) {//只有一个元素的情况
            first = null;
            last = null;
        } else {
            Node<E> prev = node.prev;
            Node<E> next = node.next;
            prev.next = next;
            next.prev = prev;
            if (node == first) { //index =0
                first = next;
            }
            if (node == last) { //index =size -1
                last = prev;
            }
        }
        size--;
        return node.elemenet;
    }


    @Override
    public E get(int index) {
        rangeCheck(index);
        return node(index).elemenet;
    }

    @Override
    public E set(int index, E element) {
        rangeCheck(index);
        Node<E> node = node(index);
        E oldElement = node.elemenet;
        node.elemenet = element;
        return oldElement;
    }

    @Override
    public int indexOf(E element) {
        return 0;
    }

    //todo gc root
    @Override
    public void clear() {
        size = 0;
        first = null;
        last = null;

    }

    @Override
    public E remove(int index) {
        rangeCheck(index);
        return remove(node(index));
    }

    @Override
    public void add(int index, E element) {
        rangeCheckForAdd(index);
        if (index == size) {//这个是往最后一个添加
            Node<E> oldLast = last;
            last = new Node<>(element, oldLast, first);
            if (oldLast == null) {//添加链表的第一个元素
                first = last;
                first.next = first;
                first.prev = first;
            } else {
                oldLast.next = last;
                first.prev = last;
            }
        } else {
            Node<E> next = node(index);
            Node<E> prev = next.prev;
            Node<E> node = new Node<>(element, prev, next);
            next.prev = node;
            prev.next = node;
            if (index == 0) {//0的位置添加
                first = node;
            }
        }
        size++;
    }

    private Node<E> node(int index) {
        Node<E> node = null;
        if (index < (size >> 1)) {   //向右移动一位则代表是除以2
            node = first;
            for (int i = 0; i < index; i++) {
                node = node.next;
            }
        } else {
            node = last;
            for (int i = size - 1; i > index; i--) {
                node = node.prev;
            }
        }
        return node;

    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("[");
        Node<E> node = first;
        for (int i = 0; i < size; i++) {
            if (i != 0) {
                stringBuilder.append(",");
            }
            stringBuilder.append(node.elemenet);
            node = node.next;
        }
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    private static class Node<E> {
        E elemenet;
        Node<E> prev;
        Node<E> next;

        public Node(E elemenet, Node<E> prev, Node<E> next) {
            this.next = next;
            this.prev = prev;
            this.elemenet = elemenet;
        }
    }
}
