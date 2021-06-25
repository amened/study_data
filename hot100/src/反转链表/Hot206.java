package 反转链表;

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


public class Hot206 {
    public static void main(String[] args) {
        ListNode node1 = new ListNode(11);
        ListNode node2 = new ListNode(12);
        ListNode node3 = new ListNode(13);
        ListNode node4 = new ListNode(14 ,null);
        node1.next = node2;
        node2.next = node3;
        node3.next = node4;

        ListNode listNode = reverseList(node1);
        ListNode next = listNode;
        while (listNode != null){
            if (listNode.next == null){
                System.out.print(listNode.val);
            }else {
                System.out.print(listNode.val + "-->");
            }
            listNode = listNode.next;
        }
    }

    public static ListNode reverseList(ListNode head) {
        ListNode pre = null;
        ListNode curr = head;

        while (curr != null){
            ListNode next  = curr.next;
            curr.next = pre;
            pre = curr;
            curr = next;
        }

        return pre;
    }
}

