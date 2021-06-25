package 旋转链表;

// 给你一个链表的头节点 head ，旋转链表，将链表每个节点向右移动 k 个位置。

//输入：head = [1,2,3,4,5], k = 2
//        输出：[4,5,1,2,3]

class ListNode{
    int val;
    ListNode next;
    public ListNode(){}
    public ListNode(int val){this.val = val;}
    public ListNode(int val,ListNode next){
        this.val = val;
        this.next = next;
    }
}

public class Hot61 {


    public static void main(String[] args) {
        ListNode node1 = new ListNode(11);
        ListNode node2 = new ListNode(12);
        ListNode node3 = new ListNode(13);
        ListNode node4 = new ListNode(14 ,null);
        node1.next = node2;
        node2.next = node3;
        node3.next = node4;
        ListNode listNode = rotateRight(node1, 2);
        System.out.println(listNode.val);


    }

    public static ListNode rotateRight(ListNode head, int k) {
        if(head == null || head.next == null || k == 0) {
            return head;
        }

        ListNode tail = head;
        ListNode result = head;
        int length = 1;
        int pos = 0;
        while (tail.next != null){
            length++;
            tail = tail.next;
        }
        tail.next = head;

        for (int i = 0; i < length ; i ++){
            if ( (i + k) % length == 0 ){
                break;
            }
            result = result.next;
        }

        while (tail.next != result){
            tail = tail.next;
        }

        tail.next = null;
        return result;
    }

}
