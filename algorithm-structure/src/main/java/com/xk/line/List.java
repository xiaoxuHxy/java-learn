package com.xk.line;

/**
 * 线性数据结构的接口设计
 *
 * @param <E>
 */
public interface List<E> {
    /**
     * 为什么放在list里面则是因为接口是对外面公开的，外界需要访问的，则是要放在接口里面，而抽象类是对外界不可见的
     */

    public int size();

    public boolean isEmpty();

    public E get(int index);

    public E set(int index, E element);

    public int indexOf(E element);

    public boolean contains(E element);

    public void clear();

    /**
     * 先思考从0开始的
     *
     * @param element
     */
    public void add(E element);

    /**
     * 最后面一个元素不用处理，让他们访问不到就行了
     *
     * @param index
     * @return
     */
    public E remove(int index);

    public void add(int index, E element);
}
