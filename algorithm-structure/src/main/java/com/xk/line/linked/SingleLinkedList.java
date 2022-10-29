package com.xk.line.linked;

import com.xk.structure.line.AbstractList;

/**
 * 链表使用过程中注意开头和结尾的两个节点  就是 0 和size 以及size -1
 * <p>
 * <p>
 * linkedList 没有构造函数  arrayList有构造函数的目的是传递进入容量
 *
 * @param <E>
 */
public class SingleLinkedList<E> extends AbstractList<E> {
    /**
     * 第一个节点
     */
    private Node<E> first;

    /**
     * 获取某个节点对应的值
     *
     * @param index
     * @return
     */
    @Override
    public E get(int index) {
        return node(index).elemenet;
    }

    @Override
    public E set(int index, E element) {

        Node<E> node = node(index);
        E old = node.elemenet;
        node.elemenet = element;
        return old;
    }

    @Override
    public int indexOf(E element) {
        Node<E> node = first;
        if (element == null) {
            for (int i = 0; i < size; i++) {
                if (node.elemenet == null) return i;
                node = node.next;
            }
        } else {
            for (int i = 0; i < size; i++) {
                if (element.equals(node.elemenet)) return i;
                node = node.next;
            }
        }
        return ELEMENT_NOT_FOUND;
    }

    @Override
    public void clear() {
        size = 0;
        first = null;
    }

    @Override
    public E remove(int index) {
        rangeCheck(index);
        Node<E> node = node = first;
        if (index == 0) {
            first = first.next;
        } else {
            Node<E> prev = node(index - 1);
            node = prev.next;
            prev.next = node.next;
        }
        size--;
        return node.elemenet;
    }

    /**
     * 这个位置有0的情况比较特殊
     *
     * @param index
     * @param element
     */
    @Override
    public void add(int index, Object element) {
        if (index == 0) {
            first = new Node(element, first);
        } else {
            Node<E> prev = node(index - 1);
            Node<E> now = new Node(element, prev.next);
            prev.next = now;
        }
        size++;
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

    /**
     * 获取某个位置的节点
     */
    private Node<E> node(int index) {
        rangeCheck(index);
        Node<E> node = first;
        for (int i = 0; i < index; i++) {
            node = node.next;
        }
        return node;
    }


    private static class Node<E> {
        E elemenet;
        Node<E> next;

        public Node(E elemenet, Node<E> next) {
            this.next = next;
            this.elemenet = elemenet;
        }
    }

}
