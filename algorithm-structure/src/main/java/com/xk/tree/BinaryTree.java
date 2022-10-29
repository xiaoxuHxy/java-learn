package com.xk.tree;

import com.xk.structure.tree.binary.printer.BinaryTreeInfo;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 放置二叉树的常见操作
 * size
 * isEmpty
 * clear
 * 遍历：
 * 传递处理逻辑
 * 高度
 * 是否是完全二叉树
 * <p>
 * 如果 a的x次方 = y   则用 x = log aY  这样形式
 */
public class BinaryTree<E> implements BinaryTreeInfo {

    /**
     * 根节点
     */
    protected Node<E> root;
    /**
     * 树的大小
     */
    protected int size;

    /**
     * 获取树的大小
     *
     * @return
     */
    public int size() {
        return size;
    }

    /**
     * 清空树
     */
    public void clear() {
        root = null;
        size = 0;
    }

    /**
     * 判断树是否是空的
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * 前序遍历 ：意思就是root在前面遍历
     */
    public void preOrder(Visitor<E> visitor) {
        if (visitor == null) return;
        preOrder(root, visitor);
    }

    private void preOrder(Node<E> node, Visitor<E> visitor) {
        if (node == null || visitor.flag) return;
        visitor.flag = visitor.visit(node.element);
        preOrder(node.left, visitor);
        preOrder(node.right, visitor);
    }

    /**
     * 中序遍历：就是root节点在中间遍历
     */
    public void inOrder(Visitor<E> visitor) {
        if (visitor == null) return;
        inOrder(root, visitor);
    }

    private void inOrder(Node<E> node, Visitor<E> visitor) {
        if (node == null || visitor.flag) return;
        inOrder(node.left, visitor);
        if (visitor.flag) {
            return;
        }
        visitor.flag = visitor.visit(node.element);
        inOrder(node.right, visitor);
    }

    /**
     * 后序遍历：就是跟节点在后面进行处理
     */
    public void postOrder(Visitor<E> visitor) {
        if (visitor == null) return;
        postOrder(root, visitor);
    }

    private void postOrder(Node<E> node, Visitor<E> visitor) {
        if (node == null || visitor.flag) return;
        postOrder(node.left, visitor);
        postOrder(node.right, visitor);
        if (visitor.flag) return;
        visitor.flag = visitor.visit(node.element);
    }

    /**
     * 层序遍历
     * 利用队列实现的遍历
     */
    public void levelOrder(Visitor<E> visitor) {
        Queue<Node<E>> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            Node<E> node = queue.poll();
            if (visitor.visit(node.element)) {
                return;
            }
            if (node.left != null) {
                queue.offer(node.left);
            }
            if (node.right != null) {
                queue.offer(node.right);
            }
        }
    }

    /**
     * 判断一棵树是不是二叉树
     * 1 如果是空的则返回false
     * 2 如果左边孩子为空，右边孩子不为空，则返回错
     * 3 如果右边孩子是空的，则后序的所有节点都是叶子节点    这一个限定了，所有叶子节点出现在最后两行
     */
    public boolean isComplete(Node<E> root) {
        if (root == null) return false;
        Queue<Node<E>> queue = new LinkedList<>();
        queue.offer(root);

        boolean leaf = false;

        while (!queue.isEmpty()) {
            Node<E> node = queue.poll();

            if (leaf && !node.isLeaf()) {
                return false;
            }

            if (node.left != null) {
                queue.offer(node.left);
            } else if (node.right != null) {
                return false;
            }

            if (node.right != null) {
                queue.offer(node.right);
            } else {
                leaf = true;
            }
        }
        return true;

    }

    /**
     * 获取树的高度
     *
     * @return
     */
    public int height() {
        return height(root);
    }

    private int height(Node<E> node) {
        if (node == null) return 0;
        return 1 + Math.max(height(node.right), height(node.left));
    }

    /**
     * 寻找某一个节点的前驱节点
     * left 不为空  则是左子树最右边的节点
     * left 为空 parent 为空
     * left为空 parent 不为空 当前节点一定在祖上节点的右子树中
     *
     * @param node
     * @return
     */
    public Node<E> preNode(Node<E> node) {
        if (node == null) return null;
        if (node.left != null) {
            Node<E> p = node.left;
            while (p.right != null) {
                p = p.right;
            }
            return p;
        }
        while (node.parent != null && node == node.parent.left) {
            node = node.parent;
        }
        return node.parent;
    }


    /**
     * 遍历时候传递的处理逻辑
     */
    public static abstract class Visitor<E> {
        /**
         * 暂停标志 判断是否暂停  true则表示暂停
         */
        boolean flag;

        /**
         * 具体的处理逻辑
         *
         * @param element
         * @return
         */
        public abstract boolean visit(E element);
    }


    /**
     * 节点类
     */
    protected static class Node<E> {
        /**
         * 存储元素的值
         */
        public E element;
        /**
         * 父节点
         */
        public Node<E> parent;
        /**
         * 左边子节点
         */
        public Node<E> left;
        /**
         * 右边子节点
         */
        public Node<E> right;

        /**
         * 构造函数
         * 没有传递进来左右子树，是因为这两个可能为空
         *
         * @param element 当前节点存储的值
         * @param parent  当前节点的父节点
         */
        public Node(E element, Node<E> parent) {
            this.element = element;
            this.parent = parent;
        }

        /**
         * 当前节点是否是叶子节点
         *
         * @return
         */
        public boolean isLeaf() {
            return left == null && right == null;
        }

        /**
         *
         */
        public boolean isHaveToChildren() {
            return left != null && right != null;
        }

        public boolean isLeftChild() {
            return parent != null && this == parent.left;
        }

        public boolean isRightChild() {
            return parent != null && this == parent.right;
        }

        public boolean haveLeft() {
            return left != null;
        }

        public boolean haveRight() {
            return right != null;
        }

        public Node<E> sibling() {
            if (isLeftChild()) {
                return parent.right;
            }
            if (isRightChild()) {
                return parent.left;
            }
            return null;
        }

        public Node<E> getReplace() {
            if (haveLeft()) {
                return left;
            }
            if (haveRight()) {
                return right;
            }
            return null;
        }

    }

    /**
     * 以下是打印树相关的代码
     */
    @Override
    public Object root() {
        return root;
    }

    @Override
    public Object left(Object node) {
        return ((Node<E>) node).left;
    }

    @Override
    public Object right(Object node) {
        return ((Node<E>) node).right;
    }

    @Override
    public Object string(Object node) {
        return ((Node<E>) node).toString();
    }

}
