package com.xk.tree.binary.test;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.Queue;

/**
 * 二叉树
 * <p>
 * 接口设计
 * 添加 add
 * remove
 * 叶子节点：直接置空就行了
 * 度为1 的节点 用子节点替代就行了
 * child.parent = node.parent node.parent.left = child
 * 如果node是根节点  root = child 以及 child.parent = null
 * 度为2的节点
 * 先用前驱或者后驱节点的值覆盖原来节点的值 然后删除相应的前驱或者后驱节点。
 * 如果一个节点的度是2   那么它的前驱节点以及后驱节点的度只可能是 0 和 1
 * <p>
 * size
 * isEmpty
 * <p>
 * 遍历  前、中、后就是根节点的遍历次序。根节点在哪个位置开始遍历
 * 前序遍历 先访问根节点，然后左子树，然后右子树     树状结构展示
 * 中序遍历 先访问左子树，然后根节点，然后右子树  遍历完之后编程有顺序的了 从小到大
 * 后序遍历 先访问左子树，然后访问右子树，然后根节点  适用于先子后父的做事情案例
 * 层序遍历 从上到下，从左到右开始遍历  计算二叉树的高度，判断是否是二叉树
 * <p>
 * 对外设计遍历接口：
 * 让外界传递进来逻辑，然后进行处理,设置终止条件等等
 * toString
 * <p>
 * 层序遍历的方式添加就还原了二叉树
 * <p>
 * 二叉树的操作，主要就是几种遍历手段
 * <p>
 * 前驱节点以及后驱节点
 * <p>
 * 做作业
 *
 * @param <E>
 */
public class BinarySearchTreeTestTest<E> extends BinaryTreeTest<E> {


    private Comparator<E> comparator;

    public BinarySearchTreeTestTest() {
        this(null);
    }

    public BinarySearchTreeTestTest(Comparator<E> comparator) {
        this.comparator = comparator;
    }


    public void add(E element) {
        elementNotNullCheck(element);
        if (root == null) {
            root = new Node<>(element, null);
            size++;
            return;
        }
        //添加的不是第一个节点
        Node<E> parent = root;
        Node<E> node = root;
        int cmp = 0;
        while (node != null) {
            cmp = compare(element, node.element);
            parent = node;
            if (cmp > 0) {
                node = node.right;
            } else if (cmp < 0) {
                node = node.left;
            } else {
                node.element = element;
                return;
            }
        }
        //插入到父节点的哪个位置
        Node<E> newNode = new Node<>(element, parent);
        if (cmp > 0) {
            parent.right = newNode;
        } else {
            parent.left = newNode;
        }

        size++;
    }


    public void remove(E element) {
        remove(node(element));
    }

    private void remove(Node<E> node) {
        if (node == null) return;
        //先判断度是2的情况
        if (node.hasTwoChildren()) {
            //找到前驱节点
            Node<E> pre = predeccessor(node);
            node.element = pre.element;
            node = pre;
        }
        //删除node节点 node的度必然是0或者1
        Node<E> replacement = node.left != null ? node.left : node.right;
        if (replacement != null) { //则说明度为1
            //更改parent
            replacement.parent = node.parent;
            //父节点的左边或者右边指向子节点
            if (node.parent == null) {//node是度为1的节点并且是根节点
                root = replacement;
            } else if (node == node.parent.left) {
                node.parent.left = replacement;
            } else if (node == node.parent.right) {
                node.parent.right = replacement;
            }

        } else if (node.parent == null) { // node 是叶子节点并且是根节点
            root = null;
        } else { // node 是叶子节点，但并不是根节点
            if (node == node.parent.right) {
                node.parent.right = null;
            } else {
                node.parent.left = null;
            }

        }


        size--;

    }

    private Node<E> node(E element) {
        Node<E> node = root;
        while (node != null) {
            int cmp = compare(element, node.element);
            if (cmp == 0) return node;
            if (cmp > 0) {
                node = node.right;
            } else {
                node = node.left;
            }

        }
        return null;
    }


    public boolean contains(E element) {
        return node(element) != null;
    }


    public static abstract class Visitor<E> {
        boolean flag;

        public abstract boolean visit(E element);
    }


    /**
     * 迭代法获取二叉树的高度
     *
     * @return
     */
    public int heightForLevel() {
        if (root == null) return 0;
        int height = 0;
        int levelSize = 1; // 第一层只有一个
        Queue<Node<E>> queue = new LinkedList<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            Node<E> node = queue.poll();
            levelSize--;
            if (node.left != null) {
                queue.offer(node.left);
            }
            if (node.right != null) {
                queue.offer(node.right);
            }

            if (levelSize == 0) {//意味着即将访问下一层
                levelSize = queue.size();
                height++;


            }
        }
        return height;
    }

    /**
     * 判断是否是完全二叉树
     *
     * @return
     */
//    public boolean isComplete(){
//        if(root == null) return false;
//        Queue<Node<E>> queue = new LinkedList<>();
//        queue.offer(root);
//        boolean leaf = false;
//        while (!queue.isEmpty()){
//            Node<E> node = queue.poll();
//            if(leaf && !node.isLeaf())return false;
//            if(node.hasTwoChildren()){
//                queue.offer(node.left);
//                queue.offer(node.right);
//            }else if(node.left == null && node.right != null){
//                return false;
//            }else {
//                leaf = true;
//                if(node.left != null){
//                    queue.offer(node.left);
//                }
//            }
//        }
//        return true;
//    }
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        toString(root, stringBuilder, "");
        return stringBuilder.toString();
    }

    private void toString(Node<E> node, StringBuilder stringBuilder, String prefix) {
        if (node == null) return;
        stringBuilder.append(prefix).append(node.element).append("\n");
        toString(node.left, stringBuilder, prefix + "L--");
        toString(node.right, stringBuilder, prefix + "R--");
    }


    /**
     * 兼容传递比较器和不传递比较器
     *
     * @param e1
     * @param e2
     * @return
     */
    private int compare(E e1, E e2) {
        if (comparator != null) {
            return comparator.compare(e1, e2);
        }
        return ((Comparable<E>) e1).compareTo(e2);
    }

    private void elementNotNullCheck(E element) {
        if (element == null) {
            throw new RuntimeException("element is not allow null");
        }
    }

    /**
     * leetcode题目 翻转二叉树
     * 可以使用前中后遍历
     * 还有层序遍历
     */
    public Node<E> invertTree(Node<E> root) {
        if (root == null) return root;

        Node<E> temp = root.left;
        root.left = root.right;
        root.right = temp;
        invertTree(root.left);
        invertTree(root.right);
        return root;

    }

}
