package array;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

/**
 * 二分法
 * @author linyw
 */
public class BinarySearch {
    public static void main(String[] args) {
    }

    /**
     * 给定一个 n 个元素有序的（升序）整型数组 nums 和一个目标值 target  ，写一个函数搜索 nums 中的 target，如果目标值存在返回下标，否则返回 -1。
     * @param nums
     * @param target
     * @return
     */
    public int search(int[] nums, int target) {
        //示例 1:
        //输入: nums = [-1,0,3,5,9,12], target = 9
        //输出: 4
        //解释: 9 出现在 nums 中并且下标为 4
        //左闭右闭
        int left = 0, right = nums.length - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] == target) {
                return mid;
            } else if (nums[mid] > target) {
                right = mid - 1;
            } else if (nums[mid] < target) {
                left = mid + 1;
            }
        }
        return -1;
    }

    public int searchInsert(int[] nums, int target) {
        //给定一个排序数组和一个目标值，在数组中找到目标值，并返回其索引。如果目标值不存在于数组中，返回它将会被按顺序插入的位置。
        //请必须使用时间复杂度为 O(log n) 的算法。
        int left = 0, right = nums.length - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] == target) {
                return mid;
            } else if (nums[mid] > target) {
                right = mid - 1 ;
            } else {
                left = mid + 1;
            }
        }
        return left;
    }


    public int[] searchRange(int[] nums, int target) {
        //给定一个按照升序排列的整数数组 nums，和一个目标值 target。找出给定目标值在数组中的开始位置和结束位置。
        //如果数组中不存在目标值 target，返回 [-1, -1]。
        //进阶：
        //你可以设计并实现时间复杂度为 O(log n) 的算法解决此问题吗？
        //示例 1：
        //输入：nums = [5,7,7,8,8,10], target = 8
        //先找左边界
        int[] ans = {-1, -1};
        int left = 0, right = nums.length - 1;
        while (left <= right) {
            //趋近左边界
            int mid = left + (right - left) / 2;
            if (nums[mid] == target) {
                right = mid;
                if (left == right) {
                    ans[0] = left;
                    break;
                }
            } else if (nums[mid] > target) {
                right = mid -1;
            } else if (nums[mid] < target) {
                left = mid + 1;
            }
        }
        //再找右边界
        left = 0;
        right = nums.length - 1;
        while (left <= right) {
            //趋近右边界
            int mid = left + (right - left + 1) / 2;
            if (nums[mid] == target) {
                left = mid;
                if (left == right) {
                    ans[1] = left;
                    break;
                }
            } else if (nums[mid] > target) {
                right = mid -1;
            } else if (nums[mid] < target) {
                left = mid + 1;
            }
        }
        return ans;
    }
    public int mySqrt(int x) {
        //找出第一个平方<=x的数
        int left = 0, right = x;
        int ans = -1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if ((long)mid * mid > x){
                right = mid - 1;
            } else if ((long)mid * mid < x){
                ans = mid;
                left = mid + 1;
            } else {
                return mid;
            }
        }
        return ans;
    }


    @Test
    public void test() {
        String a = "aab";
        String b = "aa";
        if (a.compareTo(b) > 0) {
            System.out.println("a");
        } else if (a.compareTo(b) < 0) {
            System.out.println("b");
        } else {
            System.out.println("c");
        }

    }

}
