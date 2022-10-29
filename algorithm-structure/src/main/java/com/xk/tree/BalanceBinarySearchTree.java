package com.xk.tree;

import com.xk.structure.tree.binary.BinarySearchTree;

import java.util.Comparator;

public class BalanceBinarySearchTree<E> extends BinarySearchTree<E> {


    public BalanceBinarySearchTree() {
        this(null);
    }

    /**
     * 有参构造函数
     *
     * @param comparator 比较器
     */
    public BalanceBinarySearchTree(Comparator<E> comparator) {
        super(comparator);
    }

    protected void rotateLeft(Node<E> grand) {
        Node<E> parent = grand.right;
        Node<E> child = parent.left;
        grand.right = child;
        parent.left = grand;

        afterRotate(grand, parent, child);

    }

    protected void rotateRight(Node<E> grand) {
        Node<E> parent = grand.left;
        Node<E> child = parent.right;

        grand.left = child;
        parent.right = grand;
        afterRotate(grand, parent, child);
    }

    protected void afterRotate(Node<E> grand, Node<E> parent, Node<E> child) {
        //让parent成为根节点
        parent.parent = grand.parent;
        if (grand.isLeftChild()) {
            grand.parent.left = parent;
        } else if (grand.isRightChild()) {
            grand.parent.right = parent;
        } else {//这个是根节点
            root = parent;
        }
        //更新child的parent
        if (child != null) {
            child.parent = grand;
        }

        //更新grand的parent
        grand.parent = parent;

        //更新高度 先更新矮的
//        updateHeight(grand);
//        updateHeight(parent);
    }


}
