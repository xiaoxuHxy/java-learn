package com.xk.line.linked.exercise;

import com.xk.structure.line.linked.DoubleCircleLinkedList;

public class SubjectOne {
    public static void main(String[] args) {
        josePhus();
    }

    /**
     * 206反转链表
     * https://leetcode-cn.com/problems/reverse-linked-list/
     */
    public static ListNode reverseNode(ListNode head) {
        if (head == null) return null;
        if (head.next == null) return head;
        ListNode newHead = reverseNode(head.next);
        head.next.next = head;
        head.next = null;
        return newHead;
    }


    /**
     * 237 删除链表中的节点
     * https://leetcode-cn.com/problems/delete-node-in-a-linked-list/
     */
    public static void deleteNode(ListNode node) {
        node.val = node.next.val;
        node.next = node.next.next;
    }

    /**
     * 判断是否是循环链表
     */


    /**
     * 约瑟夫问题
     */

    public static void josePhus() {
        DoubleCircleLinkedList doubleCircleLinkedList = new DoubleCircleLinkedList();
        for (int i = 1; i < 9; i++) {
            doubleCircleLinkedList.add(i);
        }
        doubleCircleLinkedList.reset();//指向头部节点

        while (!doubleCircleLinkedList.isEmpty()) {
            doubleCircleLinkedList.next();
            doubleCircleLinkedList.next();
            System.out.println(doubleCircleLinkedList.remove());
        }
    }
}
