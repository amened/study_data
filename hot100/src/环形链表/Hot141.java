package 环形链表;

// 判断链表是否有环

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

public class Hot141 {
    public static void main(String[] args) {
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
        System.out.println(hasCycle(node1));
    }

    public static boolean hasCycle(ListNode head){
        if (head == null || head.next == null){
            return false;
        }
        ListNode slow = head;
        ListNode fast = head.next;
        while (fast != slow){
            if(fast.next == null || fast.next.next == null){
                return false;
            }else {
                fast = fast.next.next;
                slow = slow.next;
            }
        }
        return true;
    }

}
