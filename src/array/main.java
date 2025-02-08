package array;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author linyw
 */
public class main {
    /**
     * 918. 环形子数组的最大和
     * 提示
     * 中等
     * 608
     * 相关企业
     * 给定一个长度为 n 的环形整数数组 nums ，返回 nums 的非空 子数组 的最大可能和 。
     *
     * 环形数组 意味着数组的末端将会与开头相连呈环状。形式上， nums[i] 的下一个元素是 nums[(i + 1) % n] ， nums[i] 的前一个元素是 nums[(i - 1 + n) % n] 。
     *
     * 子数组 最多只能包含固定缓冲区 nums 中的每个元素一次。形式上，对于子数组 nums[i], nums[i + 1], ..., nums[j] ，不存在 i <= k1, k2 <= j 其中 k1 % n == k2 % n 。
     *
     *
     *
     * 示例 1：
     *
     * 输入：nums = [1,-2,3,-2]
     *              1
     *              1 -1
     *              1 -1 3
     *              1 -1 3 1
     *              设dp[i][0]为到第i个元素为止的最大和，则有状态转移方程dp[i][0] = max(dp[i-1][0] + nums[i], nums[i])
     *              设dp[][1]为到第i个元素为止的最小和，则有状态转移方程dp[i][1] = min(dp[i-1][1] + nums[i], nums[i])
     *              ans = max(dp[i][0]中的最大值， 数组和 - dp[i][1]中的最小值)
     * 输出：3
     * 解释：从子数组 [3] 得到最大和 3
     * 示例 2：
     *
     * 输入：nums = [5,-3,5]
     * 输出：10
     * 解释：从子数组 [5,5] 得到最大和 5 + 5 = 10
     * 示例 3：
     *
     * 输入：nums = [3,-2,2,-3]
     * 输出：3
     * 解释：从子数组 [3] 和 [3,-2,2] 都可以得到最大和 3
     * 有两种可能，子数组跨边界和不跨边界；如果是跨边界的， 等价于找中间连续和最小的子数组，然后用数组和减去这个子数组的和；如果是不跨边界的，xxxxxx
     */
//    [-3,-2,-3]
    //-3            -3
    //-3 -2         -3 -5
    //-3 -2 -3      -3 -5 -8
    public static int maxSubarraySumCircular(int[] nums) {
        int dp[][] = new int[nums.length][2];
        dp[0][0] = nums[0];
        dp[0][1] = nums[0];
        long sum = nums[0], max = nums[0], min = Integer.MAX_VALUE;//-3 -3 -3
        for (int i = 1; i < nums.length; i++) {
            dp[i][0] = Math.max(dp[i-1][0] + nums[i], nums[i]);
            max = Math.max(max, dp[i][0]);
            sum += nums[i];
            if (i < nums.length - 1) {
                dp[i][1] = Math.min(dp[i-1][1] + nums[i], nums[i]);
                min = Math.min(min, dp[i][1]);
            }
        }

        long temp = sum - min;
        return (int) Math.max(max, temp);
    }


    public static long waysToBuyPensPencils(int total, int cost1, int cost2) {
        long ans = 0;
        for (int a = 0; a * cost1 <= total; a++) {
            int dif = total - a * cost1;
            int num = dif / cost2;
            ans += num + 1;
        }
        return ans;
    }

    public int passThePillow(int n, int time) {
        time = time % ((n-1)*2);
        if (time > n) {
            return n - (time - n + 1);// 4 -
        } else if(time == n){
            return n-1;
        } else {
            return 1 + time;
        }
    }

    public List<Integer> filterRestaurants(int[][] restaurants, int veganFriendly, int maxPrice, int maxDistance) {
        List<Restaurant> list = Arrays.stream(restaurants).map(Restaurant::new).collect(Collectors.toList());
        List<Integer> ans = list.stream().filter(e -> e.vaganFridenly >= veganFriendly && e.price <= maxPrice && e.distince <= maxDistance)
                .sorted().map(e -> e.id).collect(Collectors.toList());
        return ans;
    }

    class Restaurant implements Comparable<Restaurant>{
        int id;
        int rate;
        int vaganFridenly;
        int price;
        int distince;

        Restaurant(int[] restaurant) {
            this.id = restaurant[0];
            this.rate = restaurant[1];
            this.vaganFridenly = restaurant[2];
            this.price = restaurant[3];
            this.distince = restaurant[4];
        }
        @Override
        public int compareTo(Restaurant o) {
            return this.rate == o.rate ? o.id - this.id : o.rate - this.rate;
        }
    }

    /**
     * 2591. 将钱分给最多的儿童
     * 提示
     * 简单
     * 110
     * 相关企业
     * 给你一个整数 money ，表示你总共有的钱数（单位为美元）和另一个整数 children ，表示你要将钱分配给多少个儿童。
     *
     * 你需要按照如下规则分配：
     *
     * 所有的钱都必须被分配。
     * 每个儿童至少获得 1 美元。
     * 没有人获得 4 美元。
     * 请你按照上述规则分配金钱，并返回 最多 有多少个儿童获得 恰好 8 美元。如果没有任何分配方案，返回 -1 。
     *
     *
     *
     * 示例 1：
     *
     * 输入：money = 20, children = 3
     * 输出：1
     * 解释：
     * 最多获得 8 美元的儿童数为 1 。一种分配方案为：
     * - 给第一个儿童分配 8 美元。
     * - 给第二个儿童分配 9 美元。
     * - 给第三个儿童分配 3 美元。
     * 没有分配方案能让获得 8 美元的儿童数超过 1 。
     * 示例 2：
     *
     * 输入：money = 16, children = 2
     * 输出：2
     * 解释：每个儿童都可以获得 8 美元。
     *
     *
     * 提示：
     *
     * 1 <= money <= 200
     * 2 <= children <= 30
     * 4 2
     */
    public static int distMoney(int money, int children) {
        if (children > money) {
            return -1;
        }
        //1.为每一个人分配一个块钱
        money -= children;
        //2.为尽可能多的人分配7块钱，再得到7快的人数cnt = min(money / 7, children);
        int cnt = Math.min(children, money / 7);
        money -= 7 * cnt;
        children -= cnt;
        //3.分类讨论money和children
        if (children == 0 && money > 0) {
            cnt--;
        } else if (money == 3 && children == 1) {
            cnt--;
        }

        return cnt;
    }
    //创建一个length为花期最大值的数组flowerOpen，flowerOpen【i】表示第i天开放的花的数量，遍历每一朵花的花期【a,b】，为flowerOpen[a~b]做++
    //最后提去flowerOpen[people[x]] 作为答案
    //痛点： 1.花期最大值不可知  2.遍历自增太慢
    public int[] fullBloomFlowers(int[][] flowers, int[] people) {
        int[] ans = new int[people.length];
        int[] flowerOpen = new int[(int) Math.pow(10,9) + 1];
        for (int[] flower: flowers) {
            for (int i = flower[0]; i <= flower[1]; i++) {
                flowerOpen[i]++;
            }
        }
        for (int i = 0; i < people.length; i++) {
            ans[i] = flowerOpen[people[i]];
        }
        return ans;
    }

    public static void main(String[] args) {
        System.out.println((int)Math.pow(10,9));
    }
//[5,14,13,8,12]
    public long findTheArrayConcVal(int[] nums) {
        long ans = 0;
        int left = 0, right = nums.length - 1;
        while(right >= left) {
            if (right == left) {
                ans += nums[right];
            } else {
                long chuanLian = Long.valueOf(String.valueOf(nums[left]) + String.valueOf(nums[right]));
                ans += chuanLian;
            }
            right--;
            left++;
        }
        return ans;
    }


}
