package com.xk.line.stack;

import com.xk.structure.line.List;
import com.xk.structure.line.array.ArrayList;

public class Stack<E> {

    private List<E> list = new ArrayList<>();

    public int size() {
        return list.size();
    }

    public boolean isEmpty() {
        return list.isEmpty();
    }

    public void push(E element) {
        list.add(element);
    }

    public E pop() {
        return list.remove(size() - 1);
    }

    public E top() {
        return list.get(size() - 1);
    }

}
