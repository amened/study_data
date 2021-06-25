package 二叉树的路径和2;

//给你二叉树的根节点 root 和一个整数目标和 targetSum ，找出所有 从根节点到叶子节点 路径总和等于给定目标和的路径。
//
//        叶子节点 是指没有子节点的节点。
//
//        输入：root = [5,4,8,11,null,13,4,7,2,null,null,5,1], targetSum = 22
//        输出：[[5,4,11,2],[5,8,4,5]]


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
public class Hot113 {
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

        List<List<Integer>> lists = pathSum(node1, 5);
        for (List<Integer> list : lists){
            System.out.println(list.toString());
        }
    }

    public static List<List<Integer>> res = new LinkedList<>();
    public static Deque<Integer> path  = new LinkedList<>();


    public static List<List<Integer>> pathSum(TreeNode root, int targetSum) {
        dfs(root, targetSum);

        return res;
    }

    public static void dfs(TreeNode root,int targetSum){
        if (root == null){
            return;
        }

        targetSum = targetSum - root.val;
        path.offerLast(root.val);
        if (root.left == null && root.right == null && targetSum == 0){
            res.add(new LinkedList<>(path));
        }
        dfs(root.left, targetSum);
        dfs(root.right, targetSum);

        path.pollLast();
    }
}