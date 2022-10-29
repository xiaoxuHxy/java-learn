package com.xk.line.linked.exercise;

import com.xk.structure.line.AbstractList;

public class TestLinkedList<E> extends AbstractList<E> {

    private Node<E> first;

    private Node<E> last;


    @Override
    public E get(int index) {
        rangeCheck(index);
        Node<E> node = node(index);
        return node.element;
    }

    @Override
    public E set(int index, E element) {
        rangeCheck(index);
        Node<E> node = node(index);
        E oldElement = node.element;
        node.element = element;
        return oldElement;
    }

    @Override
    public int indexOf(E element) {
        Node<E> node = first;
        if (element == null) {
            for (int i = 0; i < size; i++) {
                if (node.element == null) return i;
                node = node.next;
            }
        } else {
            for (int i = 0; i < size; i++) {
                if (element.equals(node.element)) return i;
                node = node.next;
            }
        }
        return ELEMENT_NOT_FOUND;
    }

    @Override
    public void clear() {
        first = null;
        last = null;
        size = 0;

    }

    @Override
    public E remove(int index) {
        rangeCheck(index);
        //头部删除

        Node<E> node = null;
        node = node(index);
        Node<E> prev = node.prev;
        Node<E> next = node.next;

        if (index == 0) {
            node = first;
            first = first.next;
        } else if (index == size - 1) {//尾部删除
            node = last;
            last = last.prev;
        } else {

            prev.next = next;
            next.prev = prev;
        }
        size--;
        return node.element;
    }

    @Override
    public void add(int index, E element) {
        //头部添加
        if (index == 0) {
            if (size == 0) {//第一个元素
                first = new Node<>(element, null, null);
                last = first;
            } else {
                Node<E> newNode = new Node<>(element, null, first);
                first.prev = newNode;
                first = newNode;
            }
        } else if (index == size) {//最后面添加
            Node<E> node = new Node<>(element, last, null);
            last.next = node;
            last = node;
        } else {//中间添加
            Node<E> next = node(index);
            Node<E> prev = next.prev;
            Node<E> newNode = new Node<>(element, prev, next);
            prev.next = newNode;
            next.prev = newNode;
        }
        size++;
    }

    private Node<E> node(int index) {
        Node<E> node = null;
        if (index < (size >> 1)) {
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
            stringBuilder.append(node.element);
            node = node.next;
        }
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    private static class Node<E> {
        E element;
        Node<E> prev;
        Node<E> next;

        public Node(E element, Node<E> prev, Node<E> next) {
            this.element = element;
            this.prev = prev;
            this.next = next;

        }
    }
}
