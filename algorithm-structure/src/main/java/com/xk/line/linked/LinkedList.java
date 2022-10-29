package com.xk.line.linked;

import com.xk.structure.line.AbstractList;

public class LinkedList<E> extends AbstractList<E> {

    private Node<E> first;

    private Node<E> last;

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

    //todo gc root
    @Override
    public void clear() {
        size = 0;
        first = null;
        last = null;

    }

    @Override
    public E remove(int index) {
        Node<E> node = node(index);
        Node<E> prev = node.prev;
        Node<E> next = node.next;
        if (prev == null) { //index =0
            first = next;
        } else {
            prev.next = next;
        }
        if (next == null) { //index =size -1
            last = prev;
        } else {
            next.prev = prev;
        }
        size--;
        return node.elemenet;
    }

    @Override
    public void add(int index, E element) {
        rangeCheckForAdd(index);
        if (index == size) {//这个是往最后一个添加
            Node<E> oldLast = last;
            last = new Node<>(element, oldLast, null);
            if (oldLast == null) {
                first = last;
            } else {
                oldLast.next = last;
            }
        } else {
            Node<E> next = node(index);
            Node<E> prev = next.prev;
            Node<E> node = new Node<>(element, prev, next);
            next.prev = node;
            if (prev == null) {
                first = node;
            } else {
                prev.next = node;
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
