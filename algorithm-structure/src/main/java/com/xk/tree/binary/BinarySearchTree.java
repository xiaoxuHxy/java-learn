package com.xk.tree.binary;

import com.xk.structure.tree.BinaryTree;

import java.util.Comparator;

/**
 * 二叉搜索树
 *
 * @param <E>
 */
public class BinarySearchTree<E> extends BinaryTree<E> {

    private Comparator<E> comparator;

    /**
     * 无参构造函数
     */
    public BinarySearchTree() {
        this(null);
    }

    /**
     * 有参构造函数
     *
     * @param comparator 比较器
     */
    public BinarySearchTree(Comparator<E> comparator) {
        this.comparator = comparator;
    }

    /**
     * 添加元素
     * 一如果root是空的，就添加到root的位置
     * 二 找父节点
     * 三 判断在父节点的哪个位置
     * 四 size加1
     *
     * @param element 需要添加的值
     */
    public void add(E element) {
        //如果root是null
        if (root == null) {
            root = createNode(element, null);
            size++;
            afterAdd(root);
            return;
        }

        //找到父节点
        Node<E> node = root;
        Node<E> parent = root;
        int cmp = 0;
        while (node != null) {
            parent = node;
            cmp = compare(element, node.element);
            if (cmp > 0) {
                node = node.right;
            } else if (cmp < 0) {
                node = node.left;
            } else {
                node.element = element;
                return;
            }
        }
        //判断添加在父节点的哪个位置
        Node<E> newNode = createNode(element, parent);
        if (cmp > 0) {
            parent.right = newNode;
        } else {
            parent.left = newNode;
        }
        afterAdd(newNode);
        //尺寸加1
        size++;

    }


    /**
     * 新添加节点之后的调整
     *
     * @param node 新添加的节点
     */
    protected void afterAdd(Node<E> node) {

    }

    protected Node<E> createNode(E element, Node<E> parent) {
        return new Node<>(element, parent);
    }

    /**
     * 比较新曾元素的大小
     *
     * @param e1 新增元素
     * @param e2 已有元素
     * @return int 如果大于零，则表示新增元素大
     */
    private int compare(E e1, E e2) {
        if (comparator != null) return comparator.compare(e1, e2);
        return ((Comparable<E>) e1).compareTo(e2);
    }

    /**
     * 删除节点
     * 1 如果是叶子节点：直接为空就行了
     * 2 如果节点的度为1 ，直接使用子节点代替就行了
     * 3 如果是度为2 的，先用前驱节点或者后驱节点覆盖原来的值，然后删除原来的节点
     *
     * @param element
     */
    public void remove(E element) {
        remove(node(element));
    }

    private void remove(Node<E> node) {
        /**
         * 如果度为2
         */
        if (node.isHaveToChildren()) {
            Node<E> pre = preNode(node);
            node.element = pre.element;
            node = pre;//这一步是方便后面删除
        }
        /**
         * 删除node节点，其度必然是0或者1
         */
        Node<E> replacement = node.left != null ? node.left : node.right;
        if (replacement != null) {
            replacement.parent = node.parent;
            if (node.parent == null) {
                root = replacement;
                size--;
                afterRemove(node, replacement);
                return;
            }

            setSonValue(node, replacement);
//            afterRemove(node, replacement);
            afterRemove(replacement, replacement);
        } else {//这里是叶子节点
            if (node.parent == null) { //这里删除的是根节点
                root = null;
                size--;
                afterRemove(node, null);
                return;
            }
            setSonValue(node, null);
            afterRemove(node, null);
        }
        size--;
    }

    protected void afterRemove(Node<E> node, Node<E> replacement) {

    }

    /**
     * 是否包含某个元素
     *
     * @param element 传递的值
     * @return true 或者false
     */
    public boolean contains(E element) {
        return node(element) != null;
    }

    /**
     * 清空树
     */
    public void clear() {
        root = null;
        size = 0;
    }

    private void setSonValue(Node<E> node, Node<E> value) {
        if (node == node.parent.left) { //node.parent可能为空
            node.parent.left = value;
        } else {
            node.parent.right = value;
        }
    }

    /**
     * 根据元素获取节点
     *
     * @param element 元素的值
     * @return 节点
     */
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


}
