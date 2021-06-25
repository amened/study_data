package 二叉树展开为链表;

import java.util.LinkedList;
import java.util.List;

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

public class Hot114 {
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
        flatten(node1);
        System.out.println(node1.right.right.right.val);
    }

    public static void flatten(TreeNode root) {
        List<TreeNode> list = new LinkedList<>();
        preTraverse(root, list);
        for (int i = 0 ; i < list.size() - 1 ; i++){
            list.get(i).right = list.get(i + 1);
            list.get(i).left = null;
        }
    }

    public static void preTraverse(TreeNode root, List<TreeNode> list){
        if (root == null){
            return;
        }

        list.add(root);
        preTraverse(root.left,list);
        preTraverse(root.right,list);
    }
}
