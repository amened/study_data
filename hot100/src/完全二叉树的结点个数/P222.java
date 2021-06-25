package 完全二叉树的结点个数;

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

public class P222 {
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
        //    4        6

        node2.left = node4;
        node1.left = node2;
        node1.right = node3;
        node3.right = node6;

        System.out.println(countNodes(node1));


    }

    public static int countNodes(TreeNode root) {
        if (root == null){
            return 0;
        }

        int lcounts = countNodes(root.left);
        int rcounts = countNodes(root.right);
        return lcounts + rcounts + 1;
    }
}
