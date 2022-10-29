package com.xk.line.queue.execise;

public class TestCircleQueue<E> {
    private int size;

    private E[] elements;

    private int front;

    private static final int DEFAULT_CAPACITY = 10;

    public TestCircleQueue() {
        elements = (E[]) new Object[DEFAULT_CAPACITY];
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void enqueue(E element) {
        ensure(size + 1);
        elements[index(size - 1)] = element;
        size++;
    }

    public E dequeue() {
        E element = elements[front];
        front = index(1);
        elements[front] = null;
        size--;
        return element;
    }

    private void ensure(int capacity) {
        if (capacity < elements.length) {
            return;
        } else {
            int newCapacity = elements.length + elements.length >> 1;
            E[] newElements = (E[]) new Object[newCapacity];
            for (int i = 0; i < size; i++) {
                newElements[i] = elements[index(i)];
            }
            elements = newElements;
            front = 0;
        }
    }

    private int index(int index) {
        index = index + front;
        return index > elements.length ? index - elements.length : index;

    }


}
