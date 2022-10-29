package com.xk.line.queue.execise;

import java.util.Stack;

/**
 * 用栈实现队列
 *
 * @param <E>
 */
public class StackForQueue<E> {

    private Stack<E> inStack = new Stack();

    private Stack<E> outStack = new Stack();

    /**
     * 入栈
     *
     * @param element
     */
    public void enQueue(E element) {
        inStack.push(element);
    }

    /**
     * 出栈
     *
     * @return
     */
    public E deQueue() {
        if (outStack.isEmpty()) {
            while (!inStack.isEmpty()) {
                outStack.push(inStack.pop());
            }
        }
        return outStack.pop();
    }

    /**
     * 获取队头元素
     *
     * @return
     */
    public E front() {
        if (outStack.isEmpty()) {
            while (!inStack.isEmpty()) {
                outStack.push(inStack.pop());
            }
        }
        return outStack.peek();
    }

    public boolean isEmpty() {
        return outStack.isEmpty() && inStack.isEmpty();
    }

}
