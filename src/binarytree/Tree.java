package binarytree;

import org.junit.jupiter.api.Test;

import java.util.*;

/**
 * @author linyw
 */
public class Tree {
    public List<Integer> preorderTraversal(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        iterTreePreOrder(root, res);
        return res;
    }

    /**
     * 遍历法。前序 中左右，处理顺序与访问顺序一致
     * @param root
     * @return
     */
    public List<Integer> preorderTraversal2(TreeNode root) {
        if (root == null) {
            return new ArrayList<>();
        }
        List<Integer> ans = new ArrayList<>();
        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);
        while (!stack.empty()) {
            TreeNode cur = stack.pop();
            //中
            ans.add(cur.val);
            //右
            if (cur.right != null) {
                stack.push(cur.right);
            }
            //左
            if (cur.left != null) {
                stack.push(cur.left);
            }
        }
        return ans;
    }

    public List<Integer> postorderTraversal(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        iterTreePostOrder(root, res);
        return res;
    }

    public List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        iterTreeInOrder(root, res);
        return res;
    }

    private void iterTreeInOrder(TreeNode root, List<Integer> res) {
        if (root == null) {
            return;
        }
        //left
        iterTreeInOrder(root.left, res);
        //mid
        res.add(root.val);
        //right
        iterTreeInOrder(root.right, res);
    }

    private void iterTreePostOrder(TreeNode node, List<Integer> res) {
        if (node == null) {
            return;
        }
        //left
        iterTreePostOrder(node.left, res);
        //right
        iterTreePostOrder(node.right, res);
        //mid
        res.add(node.val);
    }

    /**
     * 递归三要素，入参出参、退出条件、单层逻辑
     * @param node
     * @param res
     */
    private void iterTreePreOrder(TreeNode node, List<Integer> res) {
        if (node == null) {
            return;
        }
        //中
        res.add(node.val);
        //左
        iterTreePreOrder(node.left, res);
        //右
        iterTreePreOrder(node.right, res);
    }
    public int maxDepth(TreeNode root) {
        return postorderTraversal2(root, 1);
    }

    private int postorderTraversal2(TreeNode cur, Integer depth) {
        //递归三要素，入参出餐、退出条件、单层逻辑
        if(cur == null) {
            return depth;
        }
        //left
        int i = postorderTraversal2(cur.left, depth + 1);
        //right
        int i1 = postorderTraversal2(cur.right, depth + 1);
        //mid
        return Math.max(i, i1);
    }

    public int maxDepth2(TreeNode root) {
        return postorderTraversal3(root, 1);
    }
    Integer myAns = Integer.MIN_VALUE;
    private int postorderTraversal3(TreeNode cur, int depth) {
        if (cur == null) {
            return depth - 1;
        }

        List<TreeNode> children = cur.children;
        if (children == null || children.size() == 0) {
            return depth;
        } else {
            for (TreeNode child: children) {
                myAns = Math.max(postorderTraversal3(child, depth + 1) ,myAns);
            }
            //mid
            return myAns;
        }
    }

    public int minDepth(TreeNode root) {
        return tellMeYourMinDepth(root);
    }

    int tellMeYourMinDepth(TreeNode node) {
        if (node == null) {
            return 0;
        }
        //left
        int leftDepth = tellMeYourMinDepth(node.left);
        //right
        int rightDepth = tellMeYourMinDepth(node.right);
        //mid
        if (node.left != null && node.right != null) {
            return Math.min(leftDepth, rightDepth) + 1;
        } else if (node.left != null) {
            return leftDepth + 1;
        } else if (node.right != null) {
            return rightDepth + 1;
        } else {
            return 1;
        }

    }

    public int countNodes(TreeNode root) {
        return tellMeYourCount(root);
    }
    int tellMeYourCount(TreeNode node) {
        //终止条件
        if (node == null) {
            return 0;
        }
        TreeNode left = node.left;
        TreeNode right = node.right;
        int leftDepth = 0, rightDepth = 0;
        while (left != null) {
            left = left.left;
            leftDepth++;
        }
        while (right != null) {
            right = right.right;
            rightDepth++;
        }
        if (leftDepth == rightDepth) {
            return (2 << leftDepth) - 1;
        }
        //left
        int leftCount = tellMeYourCount(node.left);
        //right
        int rightCount = tellMeYourCount(node.right);
        //mid
        return leftCount + rightCount + 1;
    }
    @Test
    public void fff(String[] args) {
        TreeNode root = new TreeNode(1);

        System.out.println(maxDepth2(root));
    }
    class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        public List<TreeNode> children;

        TreeNode() {};
        TreeNode(int val) { this.val = val; }
        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }
}
