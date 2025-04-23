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
     *
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
     *
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
        if (cur == null) {
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
            for (TreeNode child : children) {
                myAns = Math.max(postorderTraversal3(child, depth + 1), myAns);
            }
            //mid
            return myAns;
        }
    }

    public boolean isBalanced(TreeNode root) {
        if (root == null) {
            return true;
        }
        //left
        boolean condition1 = isBalanced(root.left);
        //right
        boolean condition2 = isBalanced(root.right);
        //mid
        int leftDepth = tellMeYourMaxDepth(root.left);
        int rightDepth = tellMeYourMaxDepth(root.right);
        boolean condition3 = Math.abs(leftDepth - rightDepth) <= 1;
        return condition1 && condition2 && condition3;
    }

    public int tellMeYourMaxDepth(TreeNode node) {
        if (node == null) {
            return 0;
        }
        //left
        int leftDepth = tellMeYourMaxDepth(node.left);
        //right
        int rightDepth = tellMeYourMaxDepth(node.right);
        //mid
        return Math.max(leftDepth, rightDepth) + 1;
    }

    List<String> answer = new ArrayList<>();

    public List<String> binaryTreePaths(TreeNode root) {
        List<String> path = new ArrayList<>();
        preorderTraversal(root, path);
        return answer;
    }

    private void preorderTraversal(TreeNode cur, List<String> path) {
        //mid
        path.add(String.valueOf(cur.val));
        //这里没有对cur进行判空，所以下面递归的时候要控制cur不能null
        if (cur.left == null && cur.right == null) {
            //收集答案
            answer.add(String.join("->", path));
            return;
        }
        //left， 确保递归后，cur不为null
        if (cur.left != null) {
            preorderTraversal(cur.left, path);
            path.remove(path.size() - 1);
        }
        //right
        if (cur.right != null) {
            preorderTraversal(cur.right, path);
            path.remove(path.size() - 1);
        }
    }

    int ansForSumOfLeftLeaves = 0;

    public int sumOfLeftLeaves(TreeNode root) {
        preOrderTraversal3(root, "mid");
        return ansForSumOfLeftLeaves;
    }

    private void preOrderTraversal3(TreeNode cur, String from) {
        if (cur.left == null && cur.right == null && from.equals("left")) {
            ansForSumOfLeftLeaves += cur.val;
            return;
        }
        //mid
        //left
        if (cur.left != null) {
            preOrderTraversal3(cur.left, "left");
        }
        //rigt
        if (cur.right != null) {
            preOrderTraversal3(cur.right, "right");
        }
    }

    Integer maxDepthForfindBottomLeftValue = 0;
    Integer maxLeftValueForfindBottomLeftValue = 0;

    public int findBottomLeftValue(TreeNode root) {
        preOrderTraversal4(root, 1);
        return maxLeftValueForfindBottomLeftValue;
    }

    private void preOrderTraversal4(TreeNode cur, int curDepth) {
        //到达叶子节点
        if (cur.left == null && cur.right == null) {
            if (curDepth > maxDepthForfindBottomLeftValue) {
                maxDepthForfindBottomLeftValue = curDepth;
                //因为是先序，所以先到达的叶子节点，就是同层最左边的叶子节点
                maxLeftValueForfindBottomLeftValue = cur.val;
            }
        }
        //mid
        //left
        if (cur.left != null) {
            curDepth += 1;
            preOrderTraversal4(cur.left, curDepth);
            curDepth -= 1;
        }
        //right
        if (cur.right != null) {
            curDepth += 1;
            preOrderTraversal4(cur.right, curDepth);
            curDepth -= 1;
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

    public boolean hasPathSum(TreeNode root, int targetSum) {
        if (root == null) {
            return false;
        }
        return tellMeHasPathSum(root, targetSum, root.val);
    }
    //2
//1      3

    private boolean tellMeHasPathSum(TreeNode node, int targetSum, int pathSum) {
        //退出条件
        if (node.left == null && node.right == null) {
            return pathSum == targetSum;
        }
        //mid
        System.out.println(node.val);
        //left
        if (node.left != null) {
            pathSum += node.left.val;
            if (tellMeHasPathSum(node.left, targetSum, pathSum)) {
                return true;
            }
            pathSum -= node.left.val;
        }
        //right
        if (node.right != null) {
            pathSum += node.right.val;
            if (tellMeHasPathSum(node.right, targetSum, pathSum)) {
                return true;
            }
            pathSum -= node.right.val;
        }
        return false;
    }

    /**
     * 给你二叉树的根节点 root 和一个整数目标和 targetSum ，找出所有 从根节点到叶子节点 路径总和等于给定目标和的路径。
     * <p>
     * 叶子节点 是指没有子节点的节点。
     */
    List<List<Integer>> ansForPathSum = new ArrayList<>();

    public List<List<Integer>> pathSum(TreeNode root, int targetSum) {
        if (root == null) {
            return ansForPathSum;
        }
        List<Integer> path = new ArrayList<>();
        path.add(root.val);
        preOrderTraversal5(root, targetSum, root.val, path);
        return ansForPathSum;
    }

    private void preOrderTraversal5(TreeNode node, int targetSum, int pathVal, List<Integer> path) {
        if (node.left == null && node.right == null) {
            if (pathVal == targetSum) {
                ansForPathSum.add(new ArrayList<>(path));
            }
        }
        //mid
        //left
        if (node.left != null) {
            path.add(node.left.val);
            preOrderTraversal5(node.left, targetSum, pathVal + node.left.val, path);
            path.remove(path.size() - 1);
        }
        //right
        if (node.right != null) {
            path.add(node.right.val);
            preOrderTraversal5(node.right, targetSum, pathVal + node.right.val, path);
            path.remove(path.size() - 1);
        }
    }

    public TreeNode buildTree3(int[] inorder, int[] postorder) {
        //第一步：如果数组大小为零的话，说明是空节点了。
        if (inorder.length == 0 || postorder.length == 0) {
            return null;
        }
        //第二步：如果不为空，那么取后序数组最后一个元素作为节点元素。
        int lastIndex = postorder.length - 1;
        int curMidValue = postorder[lastIndex];
        TreeNode curMid = new TreeNode(curMidValue);
        //第三步：找到后序数组最后一个元素在中序数组的位置，作为切割点
        int curMidIndexInInOrder = 0;
        for (int i = 0; i < inorder.length; i++) {
            if (inorder[i] == curMidValue) {
                curMidIndexInInOrder = i;
                break;
            }
        }
        //第四步：切割中序数组，切成中序左数组和中序右数组 （顺序别搞反了，一定是先切中序数组）
        int[] leftPartInOrder = Arrays.copyOfRange(inorder, 0, curMidIndexInInOrder);
        int[] rightPartInOrder = Arrays.copyOfRange(inorder, curMidIndexInInOrder + 1, inorder.length);
        //第五步：切割后序数组，切成后序左数组和后序右数组
        int[] leftPartPostOrder = Arrays.copyOf(postorder, leftPartInOrder.length);
        int[] rightPartPostOrder = Arrays.copyOfRange(postorder, leftPartInOrder.length, postorder.length - 1);

        // 第六步：递归处理左区间和右区间
        curMid.left = buildTree3(leftPartInOrder, leftPartPostOrder);
        curMid.right = buildTree3(rightPartInOrder, rightPartPostOrder);
        return curMid;
    }

    //中左右                       左中右
    //preorder = [3,9,20,15,7], inorder = [9,3,15,20,7]
    public TreeNode buildTree(int[] preorder, int[] inorder) {
        //中断条件
        if (preorder == null || preorder.length == 0) {
            return null;
        }
        //单层逻辑
        //1.先序数组的第一个节点是根节点，用它切割中序数组得到左右子树
        int rootVal = preorder[0];
        TreeNode root = new TreeNode(rootVal);
        int rootIndexInInorder = 0;
        for (int i = 0; i < inorder.length; i++) {
            if (inorder[i] == rootVal) {
                rootIndexInInorder = i;
                break;
            }
        }
        int[] leftPartInorder = Arrays.copyOfRange(inorder, 0, rootIndexInInorder);
        int[] rightPartInorder = Arrays.copyOfRange(inorder, rootIndexInInorder + 1, inorder.length);
        //2.再借助切分后的中序数组切分先序
        int[] leftPartPreorder = Arrays.copyOfRange(preorder, 1, leftPartInorder.length + 1);
        int[] rightPartPreorder = Arrays.copyOfRange(preorder, leftPartInorder.length + 1, preorder.length);
        //递归调用
        root.left = buildTree(leftPartPreorder, leftPartInorder);
        root.right = buildTree(rightPartPreorder, rightPartInorder);
        return root;
    }

    /**
     * 创建一个根节点，其值为 nums 中的最大值。
     * 递归地在最大值 左边 的 子数组前缀上 构建左子树。
     * 递归地在最大值 右边 的 子数组后缀上 构建右子树。
     *
     * @param nums
     * @return
     */
    public TreeNode constructMaximumBinaryTree(int[] nums) {
        //终止条件
        if (nums == null || nums.length == 0) {
            return null;
        }
        //单层逻辑
        //获取nums中的最大值的索引
        int maxIndex = 0, maxVal = Integer.MIN_VALUE;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] > maxVal) {
                maxIndex = i;
                maxVal = nums[i];
            }
        }
        TreeNode root = new TreeNode(maxVal);
        //数组切分
        int[] leftPart = Arrays.copyOfRange(nums, 0, maxIndex);
        int[] rightPart = Arrays.copyOfRange(nums, maxIndex + 1, nums.length);
        //递归
        root.left = constructMaximumBinaryTree(leftPart);
        root.right = constructMaximumBinaryTree(rightPart);
        return root;
    }

    public TreeNode mergeTrees(TreeNode root1, TreeNode root2) {
        //终止条件
        if (root1 == null && root2 == null) {
            return null;
        }
        //单层逻辑
        //mid
        int val1 = root1 == null ? 0 : root1.val;
        int val2 = root2 == null ? 0 : root2.val;
        TreeNode root = new TreeNode(val1 + val2);
        if (root1 == null) {
            root.left = mergeTrees(null, root2.left);
            root.right = mergeTrees(null, root2.right);
        } else if (root2 == null) {
            root.right = mergeTrees(root1.right, null);
            root.right = mergeTrees(root1.right, null);
        } else {
            root.left = mergeTrees(root1.left, root2.left);
            root.right = mergeTrees(root1.right, root2.right);
        }
        //递归
        return root;
    }

    Integer minForgetMinimumDifference = Integer.MAX_VALUE;
    TreeNode preForgetMinimumDifference = null;

    public int getMinimumDifference(TreeNode root) {
        inorderTraversal2(root);
        return minForgetMinimumDifference;
    }

    private void inorderTraversal2(TreeNode cur) {
        if (cur == null) {
            return;
        }
        //left
        inorderTraversal2(cur.left);
        //mid 计算与前一个节点的差的绝对值
        if (preForgetMinimumDifference != null) {
            int abs = Math.abs(cur.val - preForgetMinimumDifference.val);
            minForgetMinimumDifference = Math.min(minForgetMinimumDifference, abs);
        }
        //right
        inorderTraversal2(cur.right);
        preForgetMinimumDifference = cur;
    }

    List<Integer> listForfindMode = new ArrayList();
    TreeNode preForfindMode = null;
    int countForfindMode = 0;
    int maxCountForfindMode = Integer.MIN_VALUE;

    public int[] findMode(TreeNode root) {
        inorderTraversal3(root);
        //把listForfindMode转换为数组
        int[] res = new int[listForfindMode.size()];
        for (int i = 0; i < listForfindMode.size(); i++) {
            res[i] = listForfindMode.get(i);
        }
        return res;
    }

    private void inorderTraversal3(TreeNode cur) {
        if (cur == null) {
            return;
        }
        //left
        inorderTraversal3(cur.left);
        //mig
        if (preForfindMode == null) {
            countForfindMode = 1;
        } else if (preForfindMode.val == cur.val) {
            countForfindMode++;
        } else {
            countForfindMode = 1;
        }
        if (countForfindMode > maxCountForfindMode) {
            maxCountForfindMode = countForfindMode;
            listForfindMode.clear();
            listForfindMode.add(cur.val);
        } else if (countForfindMode == maxCountForfindMode) {
            listForfindMode.add(cur.val);
        }
        preForfindMode = cur;
        //right
        inorderTraversal3(cur.right);
    }

    @Test
    public void bitTest() {
        byte a = 10;
        byte b = 01;
        int i = a | b;
        System.out.println(i);
    }

    class Entity {
        byte val;
        TreeNode node;

        public Entity(byte val, TreeNode node) {
            this.val = val;
            this.node = node;
        }
    }

    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        Entity entity = searchPq(root, p, q);
        return entity.node;
    }

    public Entity searchPq(TreeNode cur, TreeNode p, TreeNode q) {
        if (cur == null) {
            return null;
        }
        //leftEntity
        Entity leftEntity = searchPq(cur.left, p, q);
        //rightEntity
        Entity rightEntity = searchPq(cur.right, p, q);
        //mid
        if (leftEntity != null && leftEntity.val == 11) {
            return leftEntity;
        }
        if (rightEntity != null && rightEntity.val == 11) {
            return rightEntity;
        }
        byte mid = 0;
        if (cur.val == p.val) {
            mid = 10;
        } else if (cur.val == q.val) {
            mid = 01;
        }
        byte leftVal = leftEntity == null ? 0 : leftEntity.val;
        byte rightVal = rightEntity == null ? 0 : rightEntity.val;
        byte b = (byte) (leftVal | rightVal | mid);
        return new Entity(b, cur);
    }

    @Test
    public void fff() {
        int[] inorder = new int[]{9, 3, 15, 20, 7};
        int[] postorder = new int[]{9, 15, 7, 20, 3};
        TreeNode treeNode = buildTree3(inorder, postorder);


    }

    class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        public List<TreeNode> children;

        TreeNode() {
        }

        ;

        TreeNode(int val) {
            this.val = val;
        }

        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }
}
