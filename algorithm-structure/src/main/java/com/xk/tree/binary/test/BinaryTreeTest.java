package com.xk.tree.binary.test;

import com.xk.structure.tree.binary.printer.BinaryTreeInfo;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 二叉树公共的一些方法
 * size
 * isEmpty
 * clear
 * 遍历方法
 * isComplete()
 * height()
 *
 * @param <E>
 */
public class BinaryTreeTest<E> implements BinaryTreeInfo {
    protected Node<E> root;//根节点

    protected int size;

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void clear() {
        root = null;
        size = 0;
    }

    protected static class Node<E> {
        E element;
        Node<E> left;
        Node<E> right;
        Node<E> parent;

        public Node(E element, Node<E> parent) {
            this.element = element;
            this.parent = parent;
        }

        public boolean isLeaf() {
            return left == null && right == null;
        }

        public boolean hasTwoChildren() {
            return left != null && right != null;
        }
    }

    /**
     * 前序遍历
     */
    public void preOrder(Visitor<E> visitor) {
        if (visitor == null) return;
        preOrder(root, visitor);
    }

    private void preOrder(Node<E> node, Visitor<E> visitor) {

        if (node == null || visitor.flag) return; //这个是终止递归的调用 还有一个终止传递的逻辑的执行
//        if(visitor.flag)return; 在前序遍历里面可以省略，因为这里是紧挨着的，其他的遍历则是需要的
        visitor.flag = visitor.visit(node.element);
        preOrder(node.left, visitor);
        preOrder(node.right, visitor);
    }

    /**
     * 中序遍历
     */
    public void inorder(Visitor<E> visitor) {
        if (visitor == null) return;
        inorder(root, visitor);
    }

    private void inorder(Node<E> node, Visitor<E> visitor) {
        if (node == null || visitor.flag) return;
        inorder(node.left, visitor);
        if (visitor.flag) return;
        visitor.flag = visitor.visit(node.element);
        inorder(node.right, visitor);
    }


    /**
     * 后序遍历
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
     * 现在用队列做的，用栈行不行？
     */
    public void levelOrder(Visitor<E> visitor) {
        if (root == null) return;
        Queue<Node<E>> queue = new LinkedList<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            Node<E> node = queue.poll();
//            System.out.println(node.element);
            if (visitor.visit(node.element)) return;
            if (node.left != null) {
                queue.offer(node.left);
            }
            if (node.right != null) {
                queue.offer(node.right);
            }
        }
    }

    /**
     * 获取二叉树的高度
     *
     * @return
     */
    public int height() {
        return height(root);
    }

    /**
     * 获取某个节点的高度
     *
     * @param node
     * @return
     */
    public int height(Node<E> node) {
        if (node == null) return 0;
        return 1 + Math.max(height(node.left), height(node.right));

    }

    /**
     * 判断是否是完全二叉树\
     * 1 树是空的
     * 2 左孩子为空，右边孩子不为空，错误
     * 3 如果右边孩子是空的，那么后边的所有队列中的都是叶子节点   这一条件限制了叶子节点出现在最后边两行
     */
    public boolean isComplete() {
        if (root == null) return false;
        Queue<Node<E>> queue = new LinkedList<>();
        queue.offer(root);
        boolean leaf = false;
        while (!queue.isEmpty()) {
            Node<E> node = queue.poll();
            if (leaf && !node.isLeaf()) return false;

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
     * 前驱节点
     *
     * @param
     */
    protected Node<E> predeccessor(Node<E> node) {
        if (node == null) return null;
        //前驱在左子树
        Node<E> p = node.left;
        if (node.left != null) {

            while (p.right != null) {
                p = p.right;
            }
            return p;
        }
        //从父节点中开始寻找
        while (node.parent != null && node == node.parent.left) {
            node = node.parent;
        }
        //终止条件
//        node.parent == null;
//        node == node.parent.right;
        return node.parent;
    }


    public static abstract class Visitor<E> {
        boolean flag;

        public abstract boolean visit(E element);
    }


    /**
     * 打印二叉树需要的东西
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
        return ((Node<E>) node).element;
    }

}
