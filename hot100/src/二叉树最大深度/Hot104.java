package 二叉树最大深度;

//给定一个二叉树，找出其最大深度。
//
//        二叉树的深度为根节点到最远叶子节点的最长路径上的节点数。

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

public class Hot104 {
    public static void main(String[] args) {
        TreeNode node1 = new TreeNode(1,null,null);
        TreeNode node2 = new TreeNode(2,null,null);
        TreeNode node3 = new TreeNode(3,null,null);
        TreeNode node4 = new TreeNode(4,null,null);

        // 构建二叉树
        node2.left = node4;
        node1.left = node2;
        node1.right = node3;

    }

    public static int maxDepth(TreeNode root) {
        if (root == null){
            return 0;
        }

        int leftDepth = maxDepth(root.left);
        int rightDepth = maxDepth(root.right);
        return (Math.max(leftDepth, rightDepth)) + 1;
    }
}
