package com.xk.tree.rb;

import com.xk.structure.tree.BalanceBinarySearchTree;

import java.util.Comparator;

/**
 * 添加和删除之后保证是红黑树 就可以了
 *
 * @param <E>
 */
public class RBTree<E> extends BalanceBinarySearchTree<E> {

    private static final boolean RED = false;

    private static final boolean BLACK = true;

    public RBTree() {
        this(null);
    }

    public RBTree(Comparator<E> comparator) {
        super(comparator);
    }


    /**
     * 十二个位置，大致分为三种情况
     * 1 父节点是black
     * 2 叔父节点不是红色
     * 3 叔父节点是red 父节点 和叔父节点都是染色成black 祖父节点上溢染色成红色
     *
     * @param node 新添加的节点
     */
    @Override
    protected void afterAdd(Node<E> node) {
        Node<E> parent = node.parent;
        //添加根节点
        if (parent == null) {
            black(node);
            return;
        }
        //父节点是黑色，不用处理
        if (isBlack(parent)) return;
        Node<E> grand = parent.parent;
        Node<E> uncle = parent.sibling();
        if (isRed(uncle)) {//叔父节点是红色
            black(uncle);
            black(parent);
            red(grand);
            afterAdd(grand);
            return;
        }
        //叔父节点不是红色
        if (!isRed(uncle)) {
            if (parent.isLeftChild()) {
                red(grand);
                if (grand.isLeftChild()) { //ll
                    black(parent);
                } else {//lr
                    black(node);
                    rotateLeft(parent);
                }
                rotateRight(grand);
            } else {
                red(grand);
                if (grand.isLeftChild()) {//rl
                    black(node);
                    rotateRight(parent);
                } else {//rr
                    black(parent);
                }
                rotateLeft(grand);
            }
        }
    }

    /**
     * 只有在添加的里面才有叔父节点
     *
     * @param node
     * @param replacement
     */

    @Override
    protected void afterRemove(Node<E> node, Node<E> replacement) {
        //如果是红色节点 或者根节点直接返回
//        if(isRed(node))return;
        //黑色节点
        //用来替代的节点是红色
//        Node<E> replace = node.getReplace();
        if (isRed(node)) {
            black(node);
            return;
        }
        Node<E> parent = node.parent;
        if (parent == null) return;//根节点
        //todo 黑色叶子节点其父亲一定是度为2的节点，不然不满足红黑树性质
        boolean left = parent.left == null || node.isLeftChild();//这个是设置递归的问题的
        Node<E> sibing = left ? parent.right : parent.left;
        //左右方向不一样的。
        if (left) {

            //兄弟是红色节点 然后转换为兄弟是黑色节点
            if (isRed(sibing)) {
                black(sibing);
                red(parent);
                rotateLeft(parent);
                sibing = parent.right;
            }
            //兄弟是黑色
            if (isBlack(sibing.left) && isBlack(sibing.right)) {//兄弟不存在红色节点
                boolean isBlack = isBlack(parent);
                red(sibing);//这两步是为了平衡红黑树
                black(parent);
                if (isBlack) {//如果父节点是黑色，连环上溢出
                    afterRemove(parent, null);
                }

            } else {//兄弟至少一个红色子节点
                if (!isRed(sibing.right)) {  //先处理其中一种lr情况// ，然后重复使用后面两种就行了
                    rotateRight(sibing);
                    sibing = parent.right;
                }
                color(sibing, colorOf(parent));
                black(sibing.right);
                black(parent);
                rotateLeft(parent);
            }
        } else {
            //兄弟是红色节点 然后转换为兄弟是黑色节点
            if (isRed(sibing)) {
                black(sibing);
                red(parent);
                rotateRight(parent);
                sibing = parent.left;
            }
            //兄弟是黑色
            if (isBlack(sibing.left) && isBlack(sibing.right)) {//兄弟不存在红色节点
                red(sibing);//这两步是为了平衡红黑树
                black(parent);
                if (isBlack(parent)) {//如果父节点是黑色，连环上溢出
                    afterRemove(parent, null);
                }

            } else {//兄弟至少一个红色子节点
                if (!isRed(sibing.left)) {  //先处理其中一种lr情况// ，然后重复使用后面两种就行了
                    rotateLeft(sibing);
                    sibing = parent.left;
                }
                color(sibing, colorOf(parent));
                black(sibing.left);
                black(parent);
                rotateRight(parent);
            }
        }
    }

    private Node<E> red(Node<E> node) {
        return color(node, RED);
    }

    private Node<E> black(Node<E> node) {
        return color(node, BLACK);
    }

    private boolean isBlack(Node<E> node) {
        return colorOf(node) == BLACK;
    }

    private boolean isRed(Node<E> node) {
        return colorOf(node) == RED;
    }

    @Override
    protected Node<E> createNode(E element, Node<E> parent) {
        return new RBNode<>(element, parent);
    }

    /**
     * 查看一个节点的颜色
     *
     * @param node
     * @return
     */
    private boolean colorOf(Node<E> node) {
        return node == null ? BLACK : ((RBNode<E>) node).color;
    }

    /**
     * 染色
     *
     * @param node
     * @param color
     */
    private Node<E> color(Node<E> node, boolean color) {
        if (node == null) return node;
        ((RBNode<E>) node).color = color;
        return node;
    }

    private static class RBNode<E> extends Node<E> {

        boolean color;

        /**
         * 构造函数
         * 没有传递进来左右子树，是因为这两个可能为空
         *
         * @param element 当前节点存储的值
         * @param parent  当前节点的父节点
         */
        public RBNode(E element, Node<E> parent) {
            super(element, parent);
        }

        @Override
        public String toString() {
            String str = "";
            if (color == RED) {
                str = "R_";
            }
            return str + element.toString();
        }
    }


}
