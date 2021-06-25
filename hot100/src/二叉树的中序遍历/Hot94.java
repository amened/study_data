package 二叉树的中序遍历;

import sun.reflect.generics.tree.Tree;

import java.util.ArrayList;
import java.util.List;

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

public class Hot94 {
    public static void main(String[] args) {
        TreeNode node1 = new TreeNode(1,null,null);
        TreeNode node2 = new TreeNode(2,null,null);
        TreeNode node3 = new TreeNode(3,null,null);
        TreeNode node4 = new TreeNode(4,null,null);

        // 构建二叉树
        node2.left = node4;
        node1.left = node2;
        node1.right = node3;

        List<Integer> integers1 = inorderTraversal(node1);
        System.out.println(integers1.toString());
    }

    public static List<Integer> inorderTraversal(TreeNode root) {
            List<Integer> res = new ArrayList<>();
            inorder(root,res);
            return res;
    }

    public static void inorder(TreeNode root , List<Integer> res){
        if (root ==null){
            return;
        }
        inorder(root.left, res);
        res.add(root.val);
        inorder(root.right, res);
    }
}
