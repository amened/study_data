package 二叉树的层序遍历;

import java.util.*;

class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;
    TreeNode() {}
    TreeNode(int val) { this.val = val; }
    TreeNode(int val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }
}

public class Hot102 {
    public static void main(String[] args) {
        TreeNode node1 = new TreeNode(1,null,null);
        TreeNode node2 = new TreeNode(2,null,null);
        TreeNode node3 = new TreeNode(3,null,null);
        TreeNode node4 = new TreeNode(4,null,null);

        // 构建二叉树,node1为root

        node2.left = node4;
        node1.left = node2;
        node1.right = node3;

        System.out.println(levelOrder(node1).toString());

    }

    public static List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> lists = new ArrayList<>();
        if (root == null){
            return lists;
        }
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()){
            List<Integer> list = new ArrayList<>();
            // 不能直接使用queue.szie()，会造成循环数目改变
            int size = queue.size();
            for (int i = 1 ; i <= size ; i++) {
                TreeNode poll = queue.poll();
                list.add(poll.val);
                if (poll.left != null) {
                    queue.offer(poll.left);
                }
                if (poll.right != null) {
                    queue.offer(poll.right);
                }
            }

            lists.add(list);
        }

        return lists;
    }
}
