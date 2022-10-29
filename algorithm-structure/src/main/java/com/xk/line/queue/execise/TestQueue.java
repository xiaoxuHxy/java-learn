package com.xk.line.queue.execise;

import java.util.Stack;

public class TestQueue<E> {
    /**
     * https://leetcode-cn.com/problems/yong-liang-ge-zhan-shi-xian-dui-lie-lcof/
     * 用两个栈实现队列
     * 准备两个栈：inStack 、outStack
     * 入队时：push到inStack中
     * 出队时”
     * 如果outStack是空的，将inStack所有元素逐一弹出，push到outStack中然后逐一弹出栈顶元素
     * 如果outStack不为空，outStack弹出栈顶元素
     */
    public static <E> void implementQueueByTwoStacks() {

        Stack<E> inStack = new Stack();
        Stack<E> outStack = new Stack<>();

    }
}
