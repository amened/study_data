package 二叉树的路径和;

//给你二叉树的根节点root 和一个表示目标和的整数targetSum ，判断该树中是否存在 根节点到叶子节点 的路径，这条路径上所有节点值相加等于目标和 targetSum 。
//
//        叶子节点 是指没有子节点的节点。

//输入：root = [5,4,8,11,null,13,4,7,2,null,null,null,1], targetSum = 22
//        输出：true


import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

class TreeNode{
    int val;
    TreeNode left;
    TreeNode right;

    public TreeNode(){}
    public TreeNode(int val){this.val = val;}
    public TreeNode(int val, TreeNode left, TreeNode right){
        this.val = val;
        this.left = left;
        this.right = right;
    }
}

public class Hot112 {
    public static void main(String[] args) {
        TreeNode node1 = new TreeNode(1,null,null);
        TreeNode node2 = new TreeNode(2,null,null);
        TreeNode node3 = new TreeNode(3,null,null);
        TreeNode node4 = new TreeNode(4,null,null);
        TreeNode node5 = new TreeNode(5,null,null);
        TreeNode node6 = new TreeNode(6,null,null);

        // // 构建二叉树,node1为root
        //        1
        //      2    3
        //    4     5   6

        node2.left = node4;
        node1.left = node2;
        node1.right = node3;
        node3.left = node5;
        node3.right = node6;
        System.out.println(hasPathSum(node1, 9));
    }

    public static boolean hasPathSum(TreeNode root, int targetSum) {
        if (root == null){
            return false;
        }

        Queue<TreeNode> treeQueue = new LinkedList<>();
        Queue<Integer> valueQueue = new LinkedList<>();
        treeQueue.offer(root);
        valueQueue.offer(root.val);
        while (!treeQueue.isEmpty()){
            TreeNode pollTree = treeQueue.poll();
            Integer pollvalue = valueQueue.poll();
            if (pollTree.left == null && pollTree.right == null){
                if (pollvalue == targetSum){
                    return true;
                }
                continue;
            }
            if (pollTree.left != null){
                treeQueue.offer(pollTree.left);
                valueQueue.offer(pollvalue + pollTree.left.val);
            }
            if (pollTree.right != null){
                treeQueue.offer(pollTree.right);
                valueQueue.offer(pollvalue + pollTree.right.val);
            }
        }
        return false;
    }
}
