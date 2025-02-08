package array;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author linyw
 */
public class TwoPointer {
    public int removeElement(int[] nums, int val) {
        //给你一个数组 nums 和一个值 val，你需要 原地 移除所有数值等于 val 的元素。元素的顺序可能发生改变。然后返回 nums 中与 val 不同的元素的数量。
        //
        //假设 nums 中不等于 val 的元素数量为 k，要通过此题，您需要执行以下操作：
        //
        //更改 nums 数组，使 nums 的前 k 个元素包含不等于 val 的元素。nums 的其余元素和 nums 的大小并不重要。
        //返回 k。
        //nums = [3,2,2,3], val = 3
        //双指针法
        int left = 0, right = nums.length - 1, count = 0;
        //初始化，让right指向最后一个非val的位置
        while (right >= 0 && nums[right] == val) {
            right--;
            count++;
        }
        //循环接收后，right可以是-1， 也可能是在数组范围内
        if (right < 0) {
            return nums.length - count;
        } else {
            while (left <= right) {
                //如果left是val， 和right交换位置， 然后移动指针位置， 增加计数
                if (nums[left] == val) {
                    nums[left] = nums[right];
                    nums[right] = val;
                    right--;
                    count++;
                } else {
                    left++;
                }
            }
        }
        return nums.length - count;
    }
    //977. 有序数组的平方
    public int[] sortedSquares(int[] nums) {
        //倒叙插入，新数组的最大值一定出现在旧数组的两侧
        int[] res = new int[nums.length];
        int left = 0, right = nums.length - 1, index = nums.length - 1;
        while (left <= right) {
            if (nums[left] * nums[left] >=nums[right] * nums[right]) {
                res[index] = nums[left] * nums[left];
                left++;
                index--;
            } else {
                res[index] = nums[right] * nums[right];
                right--;
                index--;
            }
        }
        return res;
    }

    public int minSubArrayLen(int target, int[] nums) {
        //总和大于等于 target
        //输入：target = 7, nums = [2,3,1,2,4,3]
        //输出：2
        //解释：子数组 [4,3] 是该条件下的长度最小的子数组。
        //用一个滑动窗口，计算滑动窗口内的总和，如果小于总和，扩大右边界，如果大于总和，缩小左边界，直到找到满足条件的子数组。
        //初始化left=0,right=0,sum=0,退出条件：right=nums.length
        int left = 0, right = 0, sum = nums[0], min = Integer.MAX_VALUE;
        while (right < nums.length) {
            if (sum < target) {
                right++;
                if (right != nums.length) {
                    sum += nums[right];
                }
            } else {
                min = Math.min(min, right - left + 1);
                sum -= nums[left];
                left++;
            }
        }
        return min == Integer.MAX_VALUE ? 0 : min;
    }

    public int totalFruit(int[] fruits) {
        //初始化left=0, righht = 0, limit = 2,length = 0;
        //用一个map记录水果的存放情况 key为种类，value为数量
        Map<Integer, Integer> map = new HashMap<>();
        //left和right代表当前的边界，左闭右闭
        int left = 0, right = 0, limit = 2, ans = 1;
        while (right < fruits.length) {
            if (map.size() > limit) {
                //需要不断回退，直到符合要求的水果数量
                while (map.size() > limit) {
                    map.compute(fruits[left], (key, num) -> num == 1 ? null : num - 1);
                    left++;
                }
            } else if (map.size() < limit){
                ans = Math.max(ans, right - left + 1);
                map.put(fruits[right], map.getOrDefault(fruits[right], 0) + 1);
                right++;
            } else {
                if (map.get(fruits[right]) == null) {
                    map.put(fruits[right], 1);
                    right++;
                    //打印left和right
                    System.out.println("left:" + left + " right:" + right);
                    while (map.size() > limit) {
                        map.compute(fruits[left], (key, num) -> num == 1 ? null : num - 1);
                        left++;
                    }
                } else {
                    ans = Math.max(ans, right - left + 1);
                    map.put(fruits[right], map.getOrDefault(fruits[right], 0) + 1);
                    right++;
                }
            }
        }
        return ans;
    }

    /**
     * 给你一个字符串 s 、一个字符串 t 。返回 s 中涵盖 t 所有字符的最小子串。如果 s 中不存在涵盖 t 所有字符的子串，则返回空字符串 "" 。
     * 注意：
     *
     * 对于 t 中重复字符，我们寻找的子字符串中该字符数量必须不少于 t 中该字符数量。
     * 如果 s 中存在这样的子串，我们保证它是唯一的答案。
     * 示例 1：
     *
     * 输入：s = "ADOBECODEBANC", t = "ABC"
     * 输入：s = "ABDDCBBAC", t = "ABC"
     * 输出："BANC"
     * 解释：最小覆盖子串 "BANC" 包含来自字符串 t 的 'A'、'B' 和 'C'。
     *
     * 示例 2：
     * 输入：s = "a", t = "a"
     * 输出："a"
     * 解释：整个字符串 s 是最小覆盖子串。
     * 示例 3:
     * 输入: s = "a", t = "aa"
     * 输出: ""
     * 解释: t 中两个字符 'a' 均应包含在 s 的子串中，
     * 因此没有符合条件的子字符串，返回空字符串。
     */
    public String minWindow(String s, String t) {
        //双指针都指向左边界，当不包含目标时右移右边界，当包含目标时左移左边界并更新答案
        Map<Character, Integer> target = new HashMap<>();
        //把t的字母情况初始化到map
        for (char c : t.toCharArray()) {
            target.put(c, target.getOrDefault(c, 0) + 1);
        }
        Map<Character, Integer> windows = new HashMap<>();
        //指针指向的是当前滑动窗口的边界，左开右臂
        int left = 0, right = 0, ansLen = Integer.MAX_VALUE;
        String ans = "";
        while(right < s.length()) {
            if(!checkContains(target, windows)) {
                windows.put(s.charAt(right), windows.getOrDefault(s.charAt(right), 0) + 1);
                right++;
            }
            while (checkContains(target, windows)) {
                if (right - left + 1 < ansLen) {
                    ans = s.substring(left, right);
                    ansLen = right - left + 1;
                    System.out.println(ans);
                }
                windows.put(s.charAt(left), windows.getOrDefault(s.charAt(left), 0) - 1);
                left++;
            }
        }
        return ans;
    }


    private boolean checkContains(Map<Character, Integer> map, Map<Character, Integer> windows) {
        Iterator iter = map.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            Character key = (Character) entry.getKey();
            Integer val = (Integer) entry.getValue();
            if (windows.getOrDefault(key, 0) < val) {
                return false;
            }
        }
        return true;
    }
//    public String minWindow(String s, String t) {
//        // 用于记录t中字符的数量
//        Map<Character, Integer> map = new HashMap<>();
//        for (char c : t.toCharArray()) {
//            map.put(c, map.getOrDefault(c, 0) + 1);
//        }
//
//        // 用于记录当前窗口中的字符数量
//        Map<Character, Integer> window = new HashMap<>();
//
//        int left = 0, right = 0;
//        int required = t.length();  // 需要的字符总数
//        int minLength = Integer.MAX_VALUE;
//        String ans = "";
//
//        while (right < s.length()) {
//            char c = s.charAt(right);
//            window.put(c, window.getOrDefault(c, 0) + 1);
//
//            // 只有当当前窗口内t中的字符数满足条件时才减少所需字符
//            if (map.containsKey(c) && window.get(c) <= map.get(c)) {
//                required--;
//            }
//
//            // 当 required == 0 时，表示当前窗口内有所有t中的字符
//            while (required == 0) {
//                // 更新最优解
//                if (right - left + 1 < minLength) {
//                    minLength = right - left + 1;
//                    ans = s.substring(left, right + 1);
//                }
//
//                // 试图缩小窗口
//                char leftChar = s.charAt(left);
//                window.put(leftChar, window.get(leftChar) - 1);
//                if (map.containsKey(leftChar) && window.get(leftChar) < map.get(leftChar)) {
//                    required++;
//                }
//                left++;
//            }
//            right++;
//        }
//        return ans;
//    }


    @Test
    public void test() {
    }
}
