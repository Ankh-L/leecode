package hash;

import org.junit.jupiter.api.Test;

import java.util.*;

/**
 * @author linyw
 */
public class Main {
    /**
     * 给定两个字符串 s 和 t ，编写一个函数来判断 t 是否是 s 的
     * 字母异位词
     * 。
     *
     * 示例 1:
     *
     * 输入: s = "anagram", t = "nagaram"
     * 输出: true
     * 示例 2:
     *
     * 输入: s = "rat", t = "car"
     * 输出: false
     *
     *
     * 提示:
     *
     * 1 <= s.length, t.length <= 5 * 104
     * s 和 t 仅包含小写字母
     */
    public boolean isAnagram(String s, String t) {
        int count = s.length();
        Map<Character, Integer> map = new HashMap<>();
        for(char c : s.toCharArray()) {
            map.put(c, map.getOrDefault(c, 0) + 1);
        }
        for (char c : t.toCharArray()) {
            Integer times = map.get(c);
            if (times == null) {
                return false;
            } else if (times > 0){
                map.put(c, times - 1);
                count--;
            } else {
                return false;
            }
        }
        return count == 0;
    }

    public List<List<String>> groupAnagrams(String[] strs) {
        String[] trans = new String[strs.length];
        MyMap<Character,Integer> map = new MyMap<>();
        for (int i = 0; i < strs.length; i++) {
            String str = strs[i];
            for (char c : str.toCharArray()) {
                map.put(c, map.getOrDefault(c, 0) + 1);
            }
            trans[i] = map.toString();
            map.clear();
        }
        Map<String, List<String>> table = new HashMap<>();
        for (int i = 0; i < trans.length; i++) {
            String transedStr = trans[i];
            List<String> list = table.computeIfAbsent(transedStr, k -> new ArrayList<>());
            list.add(strs[i]);
        }
        return new ArrayList<>(table.values());
    }

    /**
     * 438. 找到字符串中所有字母异位词
     * 已解答
     * 中等
     * 相关标签
     * 相关企业
     * 给定两个字符串 s 和 p，找到 s 中所有 p 的
     * 异位词
     *  的子串，返回这些子串的起始索引。不考虑答案输出的顺序。
     * 示例 1:
     *
     * 输入: s = "cbaebabacd", p = "abc"
     * 输出: [0,6]
     * 解释:
     * 起始索引等于 0 的子串是 "cba", 它是 "abc" 的异位词。
     * 起始索引等于 6 的子串是 "bac", 它是 "abc" 的异位词。
     *  示例 2:
     *
     * 输入: s = "abab", p = "ab"
     * 输出: [0,1,2]
     * 解释:
     * 起始索引等于 0 的子串是 "ab", 它是 "ab" 的异位词。
     * 起始索引等于 1 的子串是 "ba", 它是 "ab" 的异位词。
     * 起始索引等于 2 的子串是 "ab", 它是 "ab" 的异位词。
     *
     *
     * 提示:
     *
     * 1 <= s.length, p.length <= 3 * 104
     * s 和 p 仅包含小写字母
     */

    public List<Integer> findAnagrams(String s, String p) {
        //滑动窗口，开一个长度为p的窗口，统计窗口内的字符数量，移动窗口，判断窗口内字符数量是否与p一致，一致则加入结果集，返回结果集。
        if (p.length() > s.length()) {
            return new ArrayList<>();
        }
        //左闭右闭
        int left = 0, right = p.length() -1;
        List<Integer> ans = new ArrayList<>();
        Map<Character, Integer> map = new HashMap<>();
        Map<Character, Integer> windowMap = new HashMap<>();
        for (char c : p.toCharArray()) {
            map.put(c, map.getOrDefault(c, 0) + 1);
        }
        for (int i = 0; i < p.length(); i++) {
            char c = s.charAt(i);
            windowMap.put(c, windowMap.getOrDefault(c, 0) + 1);
        }
        do {
            if (map.equals(windowMap)) {
                ans.add(left);
            }
            char leftChar = s.charAt(left);
            windowMap.put(leftChar, windowMap.get(leftChar) - 1);
            // 如果计数为0，从窗口中移除该字符
            if (windowMap.get(leftChar) == 0) {
                windowMap.remove(leftChar);
            }
            left++;

            right++;
            if (right < s.length()) {
                char c = s.charAt(right);
                windowMap.put(c, windowMap.getOrDefault(c, 0) + 1);
            }
        }
        while (right < s.length());
        return ans;
    }
    public boolean isHappy(int n) {
        Set<Integer> set = new HashSet<>();
        while (n != 1) {
            int sum = 0;
            while(n != 0) {
                int temp = n % 10;
                sum += temp * temp;
                n = n / 10;
            }
            if (set.contains(sum)) {
                return false;
            } else {
                set.add(sum);
                n = sum;
            }
        }
        return true;
    }
    public int[] twoSum(int[] nums, int target) {
        //双指针，先对数组进行排序
        Arrays.sort(nums);
        //2 7 11 15 #14
        //2 3 4 #6
        int left = 0, right = nums.length - 1;
        while (left < right) {
            int sum = nums[left] + nums[right];
            if (sum == target) {
                return new int[]{nums[left], nums[right]};
            } else if(sum < target){
                left++;
            } else {
                right--;
            }
        }
        return null;
    }

    /**
     * 给你四个整数数组 nums1、nums2、nums3 和 nums4 ，数组长度都是 n ，请你计算有多少个元组 (i, j, k, l) 能满足：
     *
     * 0 <= i, j, k, l < n
     * nums1[i] + nums2[j] + nums3[k] + nums4[l] == 0
     *
     *
     * 示例 1：
     *
     * 输入：nums1 = [1,2], nums2 = [-2,-1], nums3 = [-1,2], nums4 = [0,2]
     * 输出：2
     * 解释：
     * 两个元组如下：
     * 1. (0, 0, 0, 1) -> nums1[0] + nums2[0] + nums3[0] + nums4[1] = 1 + (-2) + (-1) + 2 = 0
     * 2. (1, 1, 0, 0) -> nums1[1] + nums2[1] + nums3[0] + nums4[0] = 2 + (-1) + (-1) + 0 = 0
     * 示例 2：
     *
     * 输入：nums1 = [0], nums2 = [0], nums3 = [0], nums4 = [0]
     * 输出：1
     *
     *
     *   提示：
     *
     * n == nums1.length
     * n == nums2.length
     * n == nums3.length
     * n == nums4.length
     * 1 <= n <= 200
     * -228 <= nums1[i], nums2[i], nums3[i], nums4[i] <= 228
     */
    public int fourSumCount(int[] nums1, int[] nums2, int[] nums3, int[] nums4) {
        int ans = 0;
        //前两个数组一组  后两个数组一组，
        Map<Integer, Integer> map1 = new HashMap<>();
        Map<Integer, Integer> map2 = new HashMap<>();
        for (int num1 : nums1) {
            for (int num2 : nums2) {
                int sum = num1 + num2;
                map1.put(sum, map1.getOrDefault(sum, 0) + 1);
            }
        }

        for (int num1 : nums3) {
            for (int num2 : nums4) {
                int sum = num1 + num2;
                map2.put(sum, map2.getOrDefault(sum, 0) + 1);
            }
        }

        for (Integer key : map1.keySet()) {
            Integer times = map2.get(-key);
            if (times != null) {
                ans = ans + map1.get(key) * times;
            }
        }
        return ans;
    }

    /**
     * 给你一个整数数组 nums ，判断是否存在三元组 [nums[i], nums[j], nums[k]] 满足 i != j、i != k 且 j != k ，同时还满足 nums[i] + nums[j] + nums[k] == 0 。请你返回所有和为 0 且不重复的三元组。
     *
     * 注意：答案中不可以包含重复的三元组。
     *
     *
     *
     *
     *
     * 示例 1：
     *
     * 输入：nums = [-1,0,1,2,-1,-4]
     * 输出：[[-1,-1,2],[-1,0,1]]
     * 解释：0-=】‘’
     * nums[0] + nums[1] + nums[2] = (-1) + 0 + 1 = 0 。
     * nums[1] + nums[2] + nums[4] = 0 + 1 + (-1) = 0 。
     * nums[0] + nums[3] + nums[4] = (-1) + 2 + (-1) = 0 。
     * 不同的三元组是 [-1,0,1] 和 [-1,-1,2] 。
     * 注意，输出的顺序和三元组的顺序并不重要。
     * 示例 2：
     *
     * 输入：nums = [0,1,1]
     * 输出：[]
     * 解释：唯一可能的三元组和不为 0 。
     * 示例 3：
     *
     * 输入：nums = [0,0,0]
     * 输出：[[0,0,0]]
     * 解释：唯一可能的三元组和为 0 。
     *
     *
     * 提示：
     *
     * 3 <= nums.length <= 3000
     * -105 <= nums[i] <= 105
     */

    public List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> ans = new ArrayList<>();
        //排序 【-4，-1，-1，0 ，1，2】
        Arrays.sort(nums);
        Set<Integer> set = new HashSet<>();
        for (int i = 0; i < nums.length; i++) {
            //元素A去重
            if (i > 0 && nums[i-1] == nums[i]) {
                continue;
            }
            set.clear();
            int target = nums[i];
            for (int j = i + 1; j < nums.length; j++) {
                //元素B去重
                if (j > i+2 && nums[j] == nums[j-1] && nums[j-1] == nums[j-2] ) {
                    continue;
                }
                //A + B  = 0- target
                if(set.contains(-target - nums[j])) {
                    List<Integer> temp = new ArrayList<>();
                    temp.add(target);
                    temp.add(nums[j]);
                    temp.add(-target - nums[j]);
                    ans.add(temp);
                    System.out.println(temp);
                    //元素C去重
                    set.remove(-target - nums[j]);
                }
                set.add(nums[j]);
            }
        }
        return ans;
    }

    /**
     * 给你一个由 n 个整数组成的数组 nums ，和一个目标值 target 。请你找出并返回满足下述全部条件且不重复的四元组 [nums[a], nums[b], nums[c], nums[d]] （若两个四元组元素一一对应，则认为两个四元组重复）：
     *
     * 0 <= a, b, c, d < n
     * a、b、c 和 d 互不相同
     * nums[a] + nums[b] + nums[c] + nums[d] == target
     * 你可以按 任意顺序 返回答案 。
     *
     *
     *
     * 示例 1：
     *             [-2,-1,0,0,0,1,2]
     * 输入：nums = [1,0,-1,0,-2,2], target = 0
     * 输出：[[-2,-1,1,2],[-2,0,0,2],[-1,0,0,1]]
     * 示例 2：
     *
     * 输入：nums = [2,2,2,2,2], target = 8
     * 输出：[[2,2,2,2]]
     *
     *
     * 提示：
     *
     * 1 <= nums.length <= 200
     * -109 <= nums[i] <= 109
     * -109 <= target <= 109
     */
    public List<List<Integer>> fourSum(int[] nums, int target) {
        List<List<Integer>> ans = new ArrayList<>();
        //排序
        Arrays.sort(nums);
        //四指针，锚定两个，动两个
        for (int anchor1 = 0; anchor1 < nums.length - 3; anchor1++) {
            if (anchor1 > 0 && nums[anchor1] == nums[anchor1 - 1]) {
                continue;
            }
            for (int anchor2 = anchor1 + 1; anchor2 < nums.length - 2; anchor2++) {
                if (anchor2 - anchor1 > 1 && nums[anchor2] == nums[anchor2 - 1]) {
                    continue;
                }
                int left = anchor2 + 1, right = nums.length - 1;
                while (left < right) {
                    long sum = (long) nums[anchor1] + nums[anchor2] + nums[left] + nums[right];
                    if (sum < target) {
                        left++;
                    } else if (sum > target) {
                        right--;
                    } else {
                        List<Integer> temp = Arrays.asList(nums[anchor1], nums[anchor2], nums[left], nums[right]);
                        System.out.println(temp);
                        ans.add(temp);

                        while (left < right && nums[left] == nums[left + 1]) {
                            left++;
                        }
                        while (left < right && nums[right] == nums[right - 1]) {
                            right--;
                        }
                        left++;
                        right--;
                    }
                }
            }
        }
        return ans;
    }
    @Test
    public void test() {
//        int[] nums = new int[]{1,0,-1,0,-2,2};
//        int[] nums = new int[]{2,2,2,2,2};
        int[] nums = new int[]{1000000000,1000000000,1000000000,1000000000};
//        fourSum(nums, 0);
//        fourSum(nums, 8);
        fourSum(nums, -294967296);
    }
    class MyMap<K,V> extends HashMap<K,V> {
        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            for (char i = 'a'; i <= 'z'; i++) {
                V times = get(i);
                if (times != null) {
                    sb.append(i).append(times);
                }
            }
            return sb.toString();
        }
    }
}
