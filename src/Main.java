import java.util.*;

/**
 * @author linyw
 */
public class Main {
    public static void main(String[] args) {
        int[] nums = new int[]{24569};
        int i = halveArray(nums);
        System.out.println(i);
    }

    public static int largestValsFromLabels(int[] values, int[] labels, int numWanted, int useLimit) {
        //构造大顶堆
        PriorityQueue<int[]> pq = new PriorityQueue<>((o1, o2) -> o2[0] - o1[0]);
        for (int i = 0; i < values.length; i++) {
            pq.offer(new int[]{values[i], labels[i]});
        }
        Map<Integer, Integer> labelTimeMap = new HashMap<>();
        int ans = 0;
        //退出条件：拿够了或者没得拿了
        while(numWanted > 0 && !pq.isEmpty()) {
            int[] pair = pq.poll();
            Integer times = labelTimeMap.getOrDefault(pair[1], 0);
            if (times < useLimit) {
                ans += pair[0];
                labelTimeMap.put(pair[1], times + 1);
                numWanted--;
            }
        }
        return ans;
    }

    public static int maximumTastiness(int[] price, int k) {
        Arrays.sort(price);
        //甜蜜°的上下界，左闭右闭
        int left = 0, right = price[price.length - 1] - price[0];
        //趋近right
        int mid = left + (right - left + 1) /2;
        System.out.println("gauss ans = " + mid);
//        [0, mid] (mid ,right]
        while(left < right) {
            //猜小了，答案在右边
            if (check(price, k, mid)) {
                left = mid;
            } else {
                right = mid - 1;
            }
            mid = left + (right - left + 1) /2;
        }
        return mid;
    }

    public static boolean check(int[] price, int k, int tastiness) {
        int pre = Integer.MIN_VALUE / 2;
        int cnt = 0;
        for (int cur : price) {
            if ((cur - pre) >= tastiness) {
                pre = cur;
                cnt++;
            }
        }
        return cnt >= k;
    }
    public static List<Boolean> canMakePaliQueries(String s, int[][] queries) {
        List<Boolean> ans  = new ArrayList<>();

        int[] count = new int[s.length() + 1];
        for (int i = 0; i < s.length(); i++) {
            int bit = 1 << (s.charAt(i) - 'a');
            count[i+1] = count[i] ^ bit;
        }

        //只需要统计出现次数为奇数的字母的个数m，看是否满足2/m<=k，满足则通过
        for (int i = 0; i < queries.length; i++) {
            int left = queries[i][0];
            int right = queries[i][1];
            int k = queries[i][2];
            int m = Integer.bitCount(count[right + 1] ^ count[left]);
            ans.add(m/2 <= k);
        }
        return ans;
    }

    public static int subarraySum(int[] nums, int k) {
        int ans = 0;
        //sum【i】表示下标从【0，i)累加的和, i∈[0, nums.lenth-1】
        int[] sum = new int[nums.length + 1];
        //key是sum, value是在sum数组中的下标的集合
        Map<Integer, List<Integer>> map = new HashMap<>();
        //计算前缀和  O（N）
        for (int i = 0; i < nums.length; i++) {
            sum[i + 1] = sum[i] + nums[i];    //i+1 >= 0    i+1 < nums.length
            int dif = sum[i+1] - k;
            if (sum[i+1] == k) {
            }
            List<Integer> temp = map.get(dif);
            ans++;
            if (temp != null) {
                ans += temp.size();
            }
            List<Integer> indexs = map.computeIfAbsent(sum[i + 1], k1 -> new ArrayList<>());
            indexs.add(i+1);
        }
        return ans;
    }
    public static boolean f(int[] nums, int k) {
        //计算前缀和, sum[i]表示从[0,i)的累加和， i属于【0，n】
        int n = nums.length;
        int[] sum = new int[n + 1];
        //存sum[]%k
        Set<Integer> set = new HashSet<>();
        for (int i = 0; i < n; i++) {
            sum[i+1] = sum[i] + nums[i];
            if (i > 0 && sum[i+1] % k == 0) {
                return true;
            } else {
                if (set.contains(sum[i+1] % k)){
                    return true;
                }
            }
            set.add(sum[i]%k);
        }
        return false;
    }
    //s[n+1][0] = max(s[n][0], s[n][1] + nums[n+1]
//1262. 可被三整除的最大和
    //定义 s[i][j] 表示取到nums[i]时，%3的余数为j时的最大和
    //如果 num[n] 可以被3整除， 递推公式：s[n][0] = s[n-1][0] + nums[n]
//                                   s[n][1] = s[n-1][1] + nums[n]
//                                   s[n][2] = s[n-1][2] + nums[n]
    //如果不能被整除，          s[n][0] = max(s[n-1][0], s[n-1][1] + nums[n] || s[n-1][2] + nums[n])
//                            s[n][1] = max(s[n-1][0] + nums[i]  当且仅当nums[i]余1, s[n-1][2] + nums[i]当且仅当nums[i]余2 )
//                            s[n][2] = max(s[n-1][0] + nums[i],  s[n-1][1] + nums[i]  当且仅当nums[i]余1)
    //初始化 s[0][0] = 0||nums[0],  s[0][1] = 0||nums[0], s[0][2] = 0 || nums[0], 视nums[0]%3的结果而定
//    给你一个整数数组 nums，请你找出并返回能被三整除的元素最大和。
//
    /**nums[i][j] 表示从前j个数中拿i个数，所能达到的最大和， 有nums[i][j] = MAX(nums[i-1][j], nums[i-1][j-1] + num[j]), 当nums[i-1][j-1] + num[j]%3=0 成立
     * 3 6 5 1 8
     * %0 %1 %2
     * 3   0 0
     * 9   6 6
     */
//
//
//    示例 1：
//
//    输入：nums = [3,6,5,1,8]
//    输出：18
//    解释：选出数字 3, 6, 1 和 8，它们的和是 18（可被 3 整除的最大和）。
//    示例 2：
//
//    输入：nums = [4]
//    输出：0
//    解释：4 不能被 3 整除，所以无法选出数字，返回 0。
//    示例 3：
//
//    输入：nums = [1,2,3,4,4]
//    输出：12
//    解释：选出数字 1, 3, 4 以及 4，它们的和是 12（可被 3 整除的最大和）。
//
//0,1 ,2
//    提示：
//
//            1 <= nums.length <= 4 * 10^4
//            1 <= nums[i] <= 10^4
    //转移方程：从上一个 或者上一个+nums[i]
    public static int maxSumDivThree(int[] nums) {
        int n = nums.length;
        int[][] dp = new int[n][3];//dp[i][j]表示前i个数，模3=j时的最大值
        dp[0][nums[0]%3] = nums[0];
        for (int i = 1; i < n; i++) {
            for (int j = 0; j < 3; j++) {
                //先将上一个状态转移过来
                dp[i][j] = Math.max(dp[i][j],dp[i-1][j]);
                //转移每个模3=j的数
                int sum = dp[i-1][j]+nums[i];
                dp[i][sum%3] = Math.max(dp[i][sum%3],sum);

            }
        }
        return dp[n-1][0];
    }
    public static int halveArray(int[] nums) {
        long sum = 0;
        int ans = 0;
        //从大到小排列
        PriorityQueue<Double> pq = new PriorityQueue<>((o1, o2) -> o2.compareTo(o1));
        for (int num : nums) {
            pq.offer((double) num);
            sum += num;
        }
        double dif = 0;
        while(dif * 2 < sum) {
            Double poll = pq.poll();
            poll = poll / 2;
            dif += poll;
            pq.offer(poll);
            ans++;
        }
        return ans;
    }

    public int findMaxConsecutiveOnes(int[] nums) {
        //遍历一边，如果是1计数++，如果是0计数清零
        int count = 0,ans = 0;
        for (int num : nums) {
            if (num == 1) {
                count++;
                ans  = Math.max(count, ans);
            } else {
                count = 0;
            }
        }
        return ans;
    }

    public int numJewelsInStones(String jewels, String stones) {
        char[] chars = stones.toCharArray();
        Map<Character, Integer> c2times = new HashMap<>();
        for (char c : chars) {
            Integer times = c2times.getOrDefault(c,0);
            times++;
            c2times.put(c, times);
        }
        chars = jewels.toCharArray();
        int ans = 0;
        for (char c : chars) {
            ans += c2times.getOrDefault(c,0);
        }
        return ans;
    }


}