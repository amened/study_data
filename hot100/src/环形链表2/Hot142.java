package 环形链表2;

//给定一个链表，返回链表开始入环的第一个节点。 如果链表无环，则返回 null。

import java.util.ArrayList;

class ListNode{
    int val;
    ListNode next;
    public ListNode(){}
    public ListNode(int val){this.val = val;}
    public ListNode(int val, ListNode next){
        this.val = val;
        this.next = next;
    }
}

public class Hot142 {
    public static void main(String[] args) {
        // 有环链表，node4为环点
        ListNode node1 = new ListNode(11);
        ListNode node2 = new ListNode(12);
        ListNode node3 = new ListNode(13);
        ListNode node4 = new ListNode(14);
        ListNode node5 = new ListNode(15);
        ListNode node6 = new ListNode(16);
        ListNode node7 = new ListNode(17);
        ListNode node8 = new ListNode(18);
        node1.next = node2;
        node2.next = node3;
        node3.next = node4;
        node4.next = node5;
        node5.next = node6;
        node6.next = node7;
        node7.next = node8;
        node8.next = node4;
        System.out.println(detectCycle(node1).val);
    }

    public static ListNode detectCycle(ListNode head) {
        ListNode pos = meetPos(head);
        ListNode first = head;
        if (pos == null){
            return null;
        }else {
            ListNode posNext = pos.next;
            while (posNext != first){
                posNext = posNext.next;
                first = first.next;
            }
            return first;
        }
    }

    public static ListNode meetPos(ListNode head){
        if (head == null || head.next == null){
            return null;
        }
        ListNode slow = head;
        ListNode fast = head.next;
        while (slow != fast){
            if (fast.next == null || fast.next.next == null){
                return null;
            }
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }
}
