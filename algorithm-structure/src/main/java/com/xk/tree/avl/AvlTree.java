package com.xk.tree.avl;

import com.xk.structure.tree.BalanceBinarySearchTree;

import java.util.Comparator;

/**
 * avl树
 * <p>
 * 添加思路：
 * 找到最低的失衡的父节点，然后恢复平衡就可以了
 * ll 右旋转
 * rr 左旋转
 * lr 先左旋转后右边旋转
 * rl 先右边旋转然后再左边
 * 旋转都是成为局部根节点
 *
 * @param <E>
 */
public class AvlTree<E> extends BalanceBinarySearchTree<E> {

    public AvlTree() {
        this(null);
    }

    /**
     * 有参构造函数
     *
     * @param comparator 比较器
     */
    public AvlTree(Comparator<E> comparator) {
        super(comparator);
    }

    @Override
    protected void afterAdd(Node<E> node) {
        while ((node = node.parent) != null) {
            if (isBalanced(node)) {
                //更新高度
                updateHeight(node);

            } else {
                //恢复平衡  当前节点一定是不平衡的，一定是平衡最低的
                rebalance(node);
                break;
            }

        }
    }

    private void rotate(
            Node<E> r,
            Node<E> a, Node<E> b, Node<E> c,
            Node<E> d,
            Node<E> e, Node<E> f, Node<E> g) {
        d.parent = r.parent;
        if (((AvlNode<E>) r).isLeftChild()) {
            r.parent.left = d;
        } else if (((AvlNode<E>) r).isRightChild()) {
            r.parent.right = d;
        } else {
            root = d;
        }

        //a - b - c
        b.left = a;
        if (a != null) {
            a.parent = b;
        }
        if (c != null) {
            c.parent = b;
        }
        b.right = c;

        updateHeight(b);

        //e - f -g
        f.left = e;
        if (e != null) {
            e.parent = f;
        }
        if (g != null) {
            g.parent = f;
        }
        f.right = g;

        updateHeight(f);
        //b-d-f
        d.left = b;
        d.right = f;
        b.parent = d;
        f.parent = d;
        updateHeight(d);
    }

    private void rebalance(Node<E> grand) {
        // 确定node parent grand  Parent 节点是 比较高的那个
        Node<E> parent = ((AvlNode<E>) grand).tallerChild();
        Node<E> node = ((AvlNode<E>) parent).tallerChild();
        //判断方向
        if (((AvlNode<E>) parent).isLeftChild()) {
            if (((AvlNode<E>) node).isLeftChild()) { //LL
//                rotateRight(grand);
                rotate(grand, node.left, node, node.right, parent, parent.right, grand, grand.right);
            } else { //LR
//                rotateLeft(parent);
//                rotateRight(grand);

                rotate(grand, grand.left, parent, node.left, node, node.right, grand, grand.right);
            }
        } else {
            if (((AvlNode<E>) parent).isLeftChild()) { //RL
//                rotateRight(parent);
//                rotateLeft(grand);

                rotate(grand, grand.left, grand, node.left, node, node.right, parent, parent.right);
            } else { //RR
//                rotateLeft(grand);
                rotate(grand, grand.left, grand, parent.left, parent, node.left, node, node.right);
            }

        }

    }

    @Override
    protected void afterRemove(Node<E> node, Node<E> replacement) {
        while ((node = node.parent) != null) {
            if (isBalanced(node)) {
                //更新高度
                updateHeight(node);

            } else {
                //恢复平衡  当前节点一定是不平衡的，一定是平衡最低的
                rebalance(node);
            }

        }
    }


    protected void afterRotate(Node<E> grand, Node<E> parent, Node<E> child) {
        //让parent成为根节点
        super.afterRotate(grand, parent, child);

        //更新高度 先更新矮的
        updateHeight(grand);
        updateHeight(parent);
    }

    protected Node<E> createNode(E element, Node<E> parent) {
        return new AvlNode<>(element, parent);
    }


    private boolean isBalanced(Node<E> node) {

        return Math.abs(((AvlNode<E>) node).balanceFactor()) <= 1;
    }

    private void updateHeight(Node<E> node) {
        ((AvlNode<E>) node).updateHeight();
    }


    private static class AvlNode<E> extends Node<E> {

        int height = 1;

        /**
         * 构造函数
         * 没有传递进来左右子树，是因为这两个可能为空
         *
         * @param element 当前节点存储的值
         * @param parent  当前节点的父节点
         */
        public AvlNode(E element, Node<E> parent) {
            super(element, parent);
        }

        public int balanceFactor() {
            int leftHeight = left == null ? 0 : ((AvlNode<E>) left).height;
            int rightHight = right == null ? 0 : ((AvlNode<E>) right).height;
            return leftHeight - rightHight;
        }

        public void updateHeight() {
            int leftHeight = left == null ? 0 : ((AvlNode<E>) left).height;
            int rightHight = right == null ? 0 : ((AvlNode<E>) right).height;
            height = 1 + Math.max(leftHeight, rightHight);
        }

        /**
         * 返回左右子树中比较高的
         * 如果相同高度，则返回相同方向的
         *
         * @return
         */
        public Node<E> tallerChild() {
            int leftHeight = left == null ? 0 : ((AvlNode<E>) left).height;
            int rightHight = right == null ? 0 : ((AvlNode<E>) right).height;
            if (leftHeight > rightHight) return left;
            if (leftHeight < rightHight) return right;
            return isLeftChild() ? left : right;
        }

        public boolean isLeftChild() {
            return parent != null && this == parent.left;
        }

        public boolean isRightChild() {
            return parent != null && this == parent.right;
        }
    }
}
