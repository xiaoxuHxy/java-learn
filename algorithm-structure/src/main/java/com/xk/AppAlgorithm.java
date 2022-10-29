//package com.xk;
//
//import com.xk.heap.BinaryHeap;
//import com.xk.line.queue.CircleQueue;
//import com.xk.tree.BinaryTree;
//import com.xk.tree.avl.AvlTree;
//import com.xk.tree.binary.BinarySearchTree;
//import com.xk.tree.binary.printer.BinaryTrees;
//import com.xk.tree.rb.RBTree;
//
//import java.util.HashMap;
//
//public class AppAlgorithm {
//    public static void main(String[] args) {
//        BinaryHeap binaryHeap = new BinaryHeap();
//        binaryHeap.add(1);
//        binaryHeap.add(2);
//        binaryHeap.add(3);
//        binaryHeap.add(4);
//        binaryHeap.add(6);
//        binaryHeap.add(7);
//        BinaryTrees.println(binaryHeap);
//        binaryHeap.replace(5);
//        BinaryTrees.println(binaryHeap);
//
//    }
//
//    public static void testTbTree() {
//
//    }
//
//
//    public static void testMap() {
//        HashMap hashMap = new HashMap();
//        hashMap.put("1", 2);
//        hashMap.put("21", 2);
//        System.out.println(hashMap.size());
//    }
//
//    public static void testRBTree(RBTree rbTree) {
//        Integer[] data = new Integer[]{
//                1, 2, 3, 4, 5, 6, 7, 8, 9, 10
//        };
//        for (int i = 0; i < data.length; i++) {
//            rbTree.add(data[i]);
//        }
//        BinaryTrees.println(rbTree);
//        for (int i = 0; i < data.length; i++) {
//            rbTree.remove(data[i]);
//            System.out.println("删除=====" + data[i]);
//            BinaryTrees.println(rbTree);
//        }
//    }
//
//    public static void testAvlTree(AvlTree avlTree) {
//
//        for (int i = 0; i < 10; i++) {
//            avlTree.add(i);
//        }
//        BinaryTrees.println(avlTree);
//    }
//
//    /**
//     * 测试二叉树
//     */
//    public static <E> void testBinarySearchTree(BinarySearchTree binarySearchTree) {
////        BinarySearchTreeTestTest binarySearchTreeTest = new BinarySearchTreeTestTest(new Comparator<Person>() {
////            @Override
////            public int compare(Person e1, Person e2) {
////                return e1.getAge() - e2.getAge();
////            }
////        });
//        Integer[] data = new Integer[]{
//                6, 7, 4, 2, 5,
////                1,3, 4,
//        };
//        for (int i = 0; i < data.length; i++) {
//            binarySearchTree.add(data[i]);
//        }
//        BinaryTrees.println(binarySearchTree);
//        binarySearchTree.add(8);
//        BinaryTrees.println(binarySearchTree);
//        binarySearchTree.inOrder(new BinaryTree.Visitor() {
//            @Override
//            public boolean visit(Object element) {
//                System.out.println(element);
//                return false;
//            }
//        });
//        System.out.println(binarySearchTree.height());
//
////        binarySearchTreeTest.levelOrder(new BinarySearchTreeTestTest.Visitor<Integer>() {
////            @Override
////            public boolean visit(Integer element) {
////                System.out.println(element);
////                if(element.equals(4) )return true;
////                return false;
////            }
////        });
////        binarySearchTreeTest.preOrder();
////        binarySearchTreeTest.inorder();
////        String string = BinaryTrees.printString(binarySearchTreeTest);
////        Files.writeToFile("F:/1.txt",string);
//    }
//
//    public static void testCircleQueue(CircleQueue circleQueue) {
//        for (int i = 0; i < 10; i++) {
//            circleQueue.enQueue(i);
//        }
//
//        for (int i = 0; i < 5; i++) {
//            circleQueue.deQueue();
//        }
//        for (int i = 15; i < 23; i++) {
//            circleQueue.enQueue(i);
//        }
//        System.out.println(circleQueue.toString());
//        while (!circleQueue.isEmpty()) {
//            System.out.println(circleQueue.deQueue());
//        }
//    }
//
//    public static void testQueue(Queue queue) {
//        queue.enQueue(11);
//        queue.enQueue(22);
//        queue.enQueue(33);
//        queue.enQueue(44);
//        queue.enQueue(55);
//
//        while (!queue.isEmpty()) {
//            System.out.println(queue.deQueue());
//        }
//
//    }
//
//    /**
//     * 测试写的栈
//     *
//     * @param stack
//     */
//    public static void testStack(Stack stack) {
//        stack.push(11);
//        stack.push(22);
//        stack.push(33);
//        stack.push(44);
//        while (!stack.isEmpty()) {
//            System.out.println(stack.pop());
//        }
//    }
//
//    /**
//     * 测试编写的线性表结构如linkedList以及arrayList 其中索引判断未做验证
//     *
//     * @param list
//     */
//    public static void testList(List list) {
//        list.add(1);
//        list.add(2);
//        list.add(3);
//        //添加
//        System.out.println("原始数据  " + list.toString());
//        System.out.println("====================添加测试===================");
//        list.add(0, 0);//头部添加
//        AssertUtil.equals(list.toString(), "[0,1,2,3]");
//        list.add(1, 1);//中部添加
//        AssertUtil.equals(list.toString(), "[0,1,1,2,3]");
//        list.add(list.size(), 10); //尾部添加
//        AssertUtil.equals(list.toString(), "[0,1,1,2,3,10]");
//        System.out.println("====================添加测试通过===================");
//        System.out.println(list.toString());
//        System.out.println("====================查询测试===================");
//        AssertUtil.equals(list.get(list.size() - 1), 10);//尾部获取
//        AssertUtil.equals(list.get(0), 0);//0处获取
//        AssertUtil.equals(list.get(3), 2);//中间获取
//        System.out.println("====================查询测试通过===================");
//        System.out.println(list.toString());
//        System.out.println("====================删除测试===================");
//        list.remove(0);//头部删除
//        AssertUtil.equals(list.toString(), "[1,1,2,3,10]");
//        list.remove(list.size() - 1);//尾部删除
//        AssertUtil.equals(list.toString(), "[1,1,2,3]");
//        list.remove(1);//中间删除
//        AssertUtil.equals(list.toString(), "[1,2,3]");
//        System.out.println("====================删除测试通过===================");
//        System.out.println(list.toString());
//        System.out.println("====================修改测试 ===================");
//        list.set(0, 0);//头部修改
//        AssertUtil.equals(list.toString(), "[0,2,3]");
//        list.set(list.size() - 1, 4);//尾部添加
//        AssertUtil.equals(list.toString(), "[0,2,4]");
//        list.set(1, 3);//中部添加
//        AssertUtil.equals(list.toString(), "[0,3,4]");
//        System.out.println("====================修改测试 通过 ===================");
//        System.out.println(list.toString());
//
//    }
//
//}
