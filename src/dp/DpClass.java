package dp;

import java.util.Arrays;

/**
 * @author linyw
 */
public class DpClass {
    public static void main(String[] args) {
        int i = numFactoredBinaryTrees(new int[]{18865777,36451879,36878647});
        System.out.println(i);
    }

    public static int numFactoredBinaryTrees(int[] arr) {
        //设dp[i]为以arr[i]为根节点的树的个数，可以从下标【0，i）中遍历，有 0 <= left <= right < i， 使得arr[left] * arr[right] == arr[i]
        //则有状态转移方程dp[i] = 1 + dp[left] * dp[right] * (left == right ? 1 : 2), 树的总个数为dp数组之和
        //初始化dp[0] = 1;
        long[] dp = new long[arr.length];
        Arrays.sort(arr);
        Arrays.fill(dp, 0, dp.length, 1);
        for (int i = 1; i < arr.length; i++) {
            for (int left = 0 ; left < i; left++) {
                for (int right = left; right < i; right++) {
                    if ((long)arr[left] * arr[right] == arr[i]) {
                        dp[i] += dp[left] * dp[right] * (left == right ? 1 : 2);
                    } else if((long)arr[left] * arr[right] > arr[i]) {
                        break;
                    }
                }
            }
        }
        long sum = Arrays.stream(dp).sum();
        return (int) (sum % (1000000007));
    }

}
