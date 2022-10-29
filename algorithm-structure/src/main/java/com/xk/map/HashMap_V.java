package com.xk.map;

import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;

public class HashMap_V<K, V> {
    private static final boolean RED = false;
    private static final boolean BLACK = true;
    private int size;
    private static final int DEFAULT_CAPACITY = 1 << 4;
    private Node<K, V>[] table;


    public HashMap_V() {
        table = new Node[DEFAULT_CAPACITY];
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void clear() {
        if (size == 0) return;
        size = 0;
        for (int i = 0; i < table.length; i++) {
            table[i] = null;
        }
    }

    public V get(K key) {
        Node<K, V> node = node(key);
        return node != null ? node.value : null;
    }

    public boolean containKey(K key) {
        return node(key) != null;
    }

    public V remove(K key) {
        return remove(node(key));
    }

    private Node<K, V> node(K k1) {
        Node<K, V> root = table[index(k1)];
        return root == null ? null : node(root, k1);
    }


    private Node<K, V> node(Node<K, V> node, K k1) {
        int h1 = k1 == null ? 0 : k1.hashCode();
        //存储查找结果
        int cmp = 0;
        Node<K, V> result = null;
        while (node != null) {
            int h2 = node.hash;
            K k2 = node.key;
            if (h1 > h2) {//根据新添加的hash值判断放在左边还是右边
                node = node.right;
            } else if (h1 < h2) {
                node = node.left;
            } else if (Objects.equals(k1, k2)) {
                return node;
            } else if (k1 != null && k2 != null
                    && k1 instanceof Comparable
                    && k1.getClass() == k2.getClass()
                    && (cmp = ((Comparable) k1).compareTo(k2)) != 0) {
                node = cmp > 0 ? node.right : node.left;
            } else if (node.right != null && (result = node(node.right, k1)) != null) {
                return result;
            } else {
                node = node.left;
            }
        }
        return null;
    }

    private void resize() {
        if (size / table.length <= 0.75) return;
        Node<K, V>[] oldTable = table;
        table = new Node[table.length << 1];
        Queue<Node<K, V>> queue = new LinkedList<>();
        for (int i = 0; i < table.length; i++) {
            if (table[i] == null) continue;
            queue.offer(table[i]);
            while (!queue.isEmpty()) {
                Node<K, V> node = queue.poll();

                if (node.left != null) {
                    queue.offer(node.left);
                }
                if (node.right != null) {
                    queue.offer(node.right);
                }
                moveNode(node);
            }
        }
    }

    private void moveNode(Node<K, V> newNode) {
        newNode.parent = null;
        newNode.left = null;
        newNode.right = null;
        newNode.color = false;
        //获取索引
        int index = index(newNode);
        //取出index位置上的节点
        Node<K, V> root = table[index];
        if (root == null) {
            root = newNode;
            table[index] = root;
            afterPut(root);
            return;
        }
        //添加新的节点到红黑树上面去
        // 添加的不是第一个节点
        // 找到父节点
        Node<K, V> parent = root;
        Node<K, V> node = root;
        int cmp = 0;
        K k1 = newNode.key;
        int h1 = k1 == null ? 0 : k1.hashCode();
        //到这里说明根节点不是空，不用直接就判断
        do {
            parent = node;
            K k2 = node.key;
            int h2 = node.hash;

            if (h1 > h2) {//根据新添加的hash值判断放在左边还是右边
                cmp = 1;
            } else if (h1 < h2) {
                cmp = -1;
            } else if (k1 != null && k2 != null
                    && k1 instanceof Comparable
                    && k1.getClass() == k2.getClass()
                    && (cmp = ((Comparable) k1).compareTo(k2)) != 0) {
            } else {
                cmp = System.identityHashCode(k1) - System.identityHashCode(k2);

            }
            if (cmp > 0) {
                node = node.right;
            } else {
                node = node.left;
            }
        } while (node != null);

        // 看看插入到父节点的哪个位置
        if (cmp > 0) {
            parent.right = newNode;
        } else {
            parent.left = newNode;
        }
        newNode.parent = parent;
        // 新添加节点之后的处理
        afterPut(newNode);
    }


    public V put(K key, V value) {
        resize();
        //获取索引
        int index = index(key);
        //取出index位置上的节点
        Node<K, V> root = table[index];
        if (root == null) {
            root = new Node<>(key, value, null);
            table[index] = root;
            size++;
            afterPut(root);
            return null;
        }
//哈希冲突了
        //添加新的节点到红黑树上面去
        // 添加的不是第一个节点
        // 找到父节点
        boolean searched = false;//说明已经搜索过了
        Node<K, V> parent = root;
        Node<K, V> node = root;
        Node<K, V> result = null;
        int cmp = 0;
        int h1 = key == null ? 0 : key.hashCode();
        K k1 = key;
        //到这里说明根节点不是空，不用直接就判断
        do {
            parent = node;
            K k2 = node.key;
            int h2 = node.hash;

            if (h1 > h2) {//根据新添加的hash值判断放在左边还是右边
                cmp = 1;
            } else if (h1 < h2) {
                cmp = -1;
            } else if (Objects.equals(k1, k2)) {
                cmp = 0;
            } else if (k1 != null && k2 != null
                    && k1 instanceof Comparable
                    && k1.getClass() == k2.getClass()
                    && (cmp = ((Comparable) k1).compareTo(k2)) != 0) {
            } else if (searched) {
//                cmp = 1; //todo 新添加都往右边去
                cmp = System.identityHashCode(k1) - System.identityHashCode(k2);
            } else {
                //先扫描,然后根据内存地址大小决定左右
                if (node.left != null && (result = node(node.left, k1)) != null || (result = node(node.right, k2)) != null) {
                    node = result;
                    cmp = 0;
                } else { //不存在这个key
                    searched = true;
//                    cmp = 1;
                    cmp = System.identityHashCode(k1) - System.identityHashCode(k2);
                    //todo 注意,添加的时候可以根据内存地址进行添加,但是找的时候不能根据内存地址进行找,这个是有问题的
                }
            }

            if (cmp > 0) {
                node = node.right;
            } else if (cmp < 0) {
                node = node.left;
            } else { // 相等
                node.key = key;
                V oldValue = node.value;
                node.value = value;
                node.hash = h1;
                return oldValue;
            }
        } while (node != null);

        // 看看插入到父节点的哪个位置
        Node<K, V> newNode = new Node<>(key, value, parent);
        if (cmp > 0) {
            parent.right = newNode;
        } else {
            parent.left = newNode;
        }
        size++;
        // 新添加节点之后的处理
        afterPut(newNode);
        return null;
    }


    public boolean containsValue(V value) {
        if (size == 0) return false;
        Queue<Node<K, V>> queue = new LinkedList<>();
        for (int i = 0; i < table.length; i++) {
            if (table[i] == null) continue;
            queue.offer(table[i]);
            while (!queue.isEmpty()) {
                Node<K, V> node = queue.poll();
                if (Objects.equals(value, node.value)) return true;
                if (node.left != null) {
                    queue.offer(node.left);
                }
                if (node.right != null) {
                    queue.offer(node.right);
                }
            }
        }
        return false;
    }

    //    private int index(K key) {
//        if (key == null) return 0;
//        int hash = key.hashCode();//这是别人实现的hashCode 不确定别人实现的是否均匀
////        hash = hash^(hash >>> 16); //高低十六位
//        return (hash ^ (hash >>> 16)) & (table.length - 1);
//    }
//    node.hash ^ (node.hash >> 16) 这个是扰动运算
    private int index(Node<K, V> node) {
//        hash = hash^(hash >>> 16); //高低十六位
        return (node.hash ^ (node.hash >>> 16)) & (table.length - 1);
    }

    private int index(K key) {
        if (key == null) return 0;
        int hash = key.hashCode();
        return (hash ^ (hash >>> 16)) & (table.length - 1);
    }


    //======================================================================================
    private V remove(Node<K, V> node) {
        if (node == null) return null;
        size--;
        V oldValue = node.value;

        if (node.hasTwoChildren()) { // 度为2的节点
            // 找到后继节点
            Node<K, V> s = successor(node);
            // 用后继节点的值覆盖度为2的节点的值
            node.key = s.key;
            node.value = s.value;
            node.hash = s.hash;
            // 删除后继节点
            node = s;
        }

        // 删除node节点（node的度必然是1或者0）
        Node<K, V> replacement = node.left != null ? node.left : node.right;

        if (replacement != null) { // node是度为1的节点
            // 更改parent
            replacement.parent = node.parent;
            // 更改parent的left、right的指向
            if (node.parent == null) { // node是度为1的节点并且是根节点
                table[index(node.key)] = replacement;
            } else if (node == node.parent.left) {
                node.parent.left = replacement;
            } else { // node == node.parent.right
                node.parent.right = replacement;
            }

            // 删除节点之后的处理
            afterRemove(replacement);
        } else if (node.parent == null) { // node是叶子节点并且是根节点
            table[index(node.key)] = null;
        } else { // node是叶子节点，但不是根节点
            if (node == node.parent.left) {
                node.parent.left = null;
            } else { // node == node.parent.right
                node.parent.right = null;
            }

            // 删除节点之后的处理
            afterRemove(node);
        }

        return oldValue;
    }

    private void afterRemove(Node<K, V> node) {
        // 如果删除的节点是红色
        // 或者 用以取代删除节点的子节点是红色
        if (isRed(node)) {
            black(node);
            return;
        }

        Node<K, V> parent = node.parent;
        if (parent == null) return;

        // 删除的是黑色叶子节点【下溢】
        // 判断被删除的node是左还是右
        boolean left = parent.left == null || node.isLeftChild();
        Node<K, V> sibling = left ? parent.right : parent.left;
        if (left) { // 被删除的节点在左边，兄弟节点在右边
            if (isRed(sibling)) { // 兄弟节点是红色
                black(sibling);
                red(parent);
                rotateLeft(parent);
                // 更换兄弟
                sibling = parent.right;
            }

            // 兄弟节点必然是黑色
            if (isBlack(sibling.left) && isBlack(sibling.right)) {
                // 兄弟节点没有1个红色子节点，父节点要向下跟兄弟节点合并
                boolean parentBlack = isBlack(parent);
                black(parent);
                red(sibling);
                if (parentBlack) {
                    afterRemove(parent);
                }
            } else { // 兄弟节点至少有1个红色子节点，向兄弟节点借元素
                // 兄弟节点的左边是黑色，兄弟要先旋转
                if (isBlack(sibling.right)) {
                    rotateRight(sibling);
                    sibling = parent.right;
                }

                color(sibling, colorOf(parent));
                black(sibling.right);
                black(parent);
                rotateLeft(parent);
            }
        } else { // 被删除的节点在右边，兄弟节点在左边
            if (isRed(sibling)) { // 兄弟节点是红色
                black(sibling);
                red(parent);
                rotateRight(parent);
                // 更换兄弟
                sibling = parent.left;
            }

            // 兄弟节点必然是黑色
            if (isBlack(sibling.left) && isBlack(sibling.right)) {
                // 兄弟节点没有1个红色子节点，父节点要向下跟兄弟节点合并
                boolean parentBlack = isBlack(parent);
                black(parent);
                red(sibling);
                if (parentBlack) {
                    afterRemove(parent);
                }
            } else { // 兄弟节点至少有1个红色子节点，向兄弟节点借元素
                // 兄弟节点的左边是黑色，兄弟要先旋转
                if (isBlack(sibling.left)) {
                    rotateLeft(sibling);
                    sibling = parent.left;
                }

                color(sibling, colorOf(parent));
                black(sibling.left);
                black(parent);
                rotateRight(parent);
            }
        }
    }

    private Node<K, V> predecessor(Node<K, V> node) {
        if (node == null) return null;

        // 前驱节点在左子树当中（left.right.right.right....）
        Node<K, V> p = node.left;
        if (p != null) {
            while (p.right != null) {
                p = p.right;
            }
            return p;
        }

        // 从父节点、祖父节点中寻找前驱节点
        while (node.parent != null && node == node.parent.left) {
            node = node.parent;
        }

        // node.parent == null
        // node == node.parent.right
        return node.parent;
    }

    private Node<K, V> successor(Node<K, V> node) {
        if (node == null) return null;

        // 前驱节点在左子树当中（right.left.left.left....）
        Node<K, V> p = node.right;
        if (p != null) {
            while (p.left != null) {
                p = p.left;
            }
            return p;
        }

        // 从父节点、祖父节点中寻找前驱节点
        while (node.parent != null && node == node.parent.right) {
            node = node.parent;
        }

        return node.parent;
    }

//    private Node<K, V> node(K key) {
//        Node<K, V> node = root;
//        while (node != null) {
//            int cmp = compare(key, node.key);
//            if (cmp == 0) return node;
//            if (cmp > 0) {
//                node = node.right;
//            } else { // cmp < 0
//                node = node.left;
//            }
//        }
//        return null;
//    }

    private void afterPut(Node<K, V> node) {
        Node<K, V> parent = node.parent;

        // 添加的是根节点 或者 上溢到达了根节点
        if (parent == null) {
            black(node);
            return;
        }

        // 如果父节点是黑色，直接返回
        if (isBlack(parent)) return;

        // 叔父节点
        Node<K, V> uncle = parent.sibling();
        // 祖父节点
        Node<K, V> grand = red(parent.parent);
        if (isRed(uncle)) { // 叔父节点是红色【B树节点上溢】
            black(parent);
            black(uncle);
            // 把祖父节点当做是新添加的节点
            afterPut(grand);
            return;
        }

        // 叔父节点不是红色
        if (parent.isLeftChild()) { // L
            if (node.isLeftChild()) { // LL
                black(parent);
            } else { // LR
                black(node);
                rotateLeft(parent);
            }
            rotateRight(grand);
        } else { // R
            if (node.isLeftChild()) { // RL
                black(node);
                rotateRight(parent);
            } else { // RR
                black(parent);
            }
            rotateLeft(grand);
        }
    }

    private void rotateLeft(Node<K, V> grand) {
        Node<K, V> parent = grand.right;
        Node<K, V> child = parent.left;
        grand.right = child;
        parent.left = grand;
        afterRotate(grand, parent, child);
    }

    private void rotateRight(Node<K, V> grand) {
        Node<K, V> parent = grand.left;
        Node<K, V> child = parent.right;
        grand.left = child;
        parent.right = grand;
        afterRotate(grand, parent, child);
    }

    private void afterRotate(Node<K, V> grand, Node<K, V> parent, Node<K, V> child) {
        // 让parent称为子树的根节点
        parent.parent = grand.parent;
        if (grand.isLeftChild()) {
            grand.parent.left = parent;
        } else if (grand.isRightChild()) {
            grand.parent.right = parent;
        } else { // grand是root节点
            table[index(parent)] = parent;
        }

        // 更新child的parent
        if (child != null) {
            child.parent = grand;
        }

        // 更新grand的parent
        grand.parent = parent;
    }

    private Node<K, V> color(Node<K, V> node, boolean color) {
        if (node == null) return node;
        node.color = color;
        return node;
    }

    private Node<K, V> red(Node<K, V> node) {
        return color(node, RED);
    }

    private Node<K, V> black(Node<K, V> node) {
        return color(node, BLACK);
    }

    private boolean colorOf(Node<K, V> node) {
        return node == null ? BLACK : node.color;
    }

    private boolean isBlack(Node<K, V> node) {
        return colorOf(node) == BLACK;
    }

    private boolean isRed(Node<K, V> node) {
        return colorOf(node) == RED;
    }

    /**
     * @param k1
     * @param k2
     * @param h1 对应key1的hashCode
     * @param h2 对应key2 的hashCode
     * @return
     */
    private int compare(K k1, K k2, int h1, int h2) {
        //先用hash值进行比较,因为 产生哈希冲突的时候,hash值可能不一样
        int result = h1 - h2;
        if (result != 0) return result;

        //key的值相等
        if (Objects.equals(h1, h2)) {
            return 0;
        }
        //哈希值相等，但是不equals
        if (k1 != null && k2 != null) {

            String k1C = k1.getClass().getName();
            String k2C = k2.getClass().getName();
            result = k1C.compareTo(k2C);
            //不是同一种类型直接使用类名
            if (result != 0) return result;
            //同一种类型并且具有比较性
            if (k1 instanceof Comparable) {
                return ((Comparable) k1).compareTo(k2);
            }
        }
        //同一种类型 哈希值相等 并且不具备比较性
        //k1 is null and k2 is not null
        // k2 is null and k1 is not null
        //compare memory address  when the k1 is null ,the memory address is zero

        return System.identityHashCode(k1) - System.identityHashCode(k2);
    }

    private void keyNotNullCheck(K key) {
        if (key == null) {
            throw new IllegalArgumentException("key must not be null");
        }
    }

    private static class Node<K, V> {
        int hash;
        K key;
        V value;
        boolean color = RED;
        Node<K, V> left;
        Node<K, V> right;
        Node<K, V> parent;

        public Node(K key, V value, Node<K, V> parent) {
            this.hash = key == null ? 0 : key.hashCode();
            this.key = key;
            this.value = value;
            this.parent = parent;
        }

        public boolean isLeaf() {
            return left == null && right == null;
        }

        public boolean hasTwoChildren() {
            return left != null && right != null;
        }

        public boolean isLeftChild() {
            return parent != null && this == parent.left;
        }

        public boolean isRightChild() {
            return parent != null && this == parent.right;
        }

        public Node<K, V> sibling() {
            if (isLeftChild()) {
                return parent.right;
            }

            if (isRightChild()) {
                return parent.left;
            }

            return null;
        }
    }
}
