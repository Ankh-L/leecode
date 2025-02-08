package str;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

/**
 * @author linyw
 */
public class Main {
    /**
     * 编写一个函数，其作用是将输入的字符串反转过来。输入字符串以字符数组 s 的形式给出。
     * <p>
     * 不要给另外的数组分配额外的空间，你必须原地修改输入数组、使用 O(1) 的额外空间解决这一问题。
     * <p>
     * <p>
     * <p>
     * 示例 1：
     * <p>
     * 输入：s = ["h","e","l","l","o"]
     * 输出：["o","l","l","e","h"]
     * 示例 2：
     * <p>
     * 输入：s = ["H","a","n","n","a","h"]
     * 输出：["h","a","n","n","a","H"]
     * <p>
     * <p>
     * 提示：
     * <p>
     * 1 <= s.length <= 105
     * s[i] 都是 ASCII 码表中的可打印字符
     */
    public void reverseString(char[] s) {
        int left = 0, right = s.length -1;
        while (left <= right) {
            swap(s, left, right);
            left++;
            right--;
        }
    }

    private void swap(char[] s, int left, int right) {
        char temp = s[left];
        s[left] = s[right];
        s[right] = temp;
    }
    /**
     * 给定一个字符串 s 和一个整数 k，从字符串开头算起，每计数至 2k 个字符，就反转这 2k 字符中的前 k 个字符。
     *
     * 如果剩余字符少于 k 个，则将剩余字符全部反转。
     * 如果剩余字符小于 2k 但大于或等于 k 个，则反转前 k 个字符，其余字符保持原样。
     *
     *
     * 示例 1：
     *
     * 输入：s = "abcdefg", k = 2
     *       bacdefg
     *       bacdfeg
     * 输出："bacdfeg"
     * 示例 2：
     *
     * 输入：s = "abcd", k = 2
     * 输出："bacd"
     *
     *
     * 提示：
     *
     * 1 <= s.length <= 104
     * s 仅由小写英文组成
     * 1 <= k <= 104
     */
    public String reverseStr(String s, int k) {
        char[] chars = s.toCharArray();
        int left = 0, right = 2 * k;
        while (right < s.length()) {
            //就反转这 2k 字符中的前 k 个字符。abcdefg
            reverseString(chars, left, left + k - 1);
            left += 2 * k;
            right += 2 * k;
        }
        right = s.length() - 1;
        //如果剩余字符少于 k 个，则将剩余字符全部反转。
        if (s.length() - left < k) {
            reverseString(chars, left, right);
        } else {
            //如果剩余字符小于 2k 但大于或等于 k 个，则反转前 k 个字符，其余字符保持原样。
            reverseString(chars, left, left + k -1);
        }
        return String.valueOf(chars);
    }
    /**
     *反转区间内的字符串，左闭右闭
     */
    public void reverseString(char[] s, int left, int right) {
        while (left <= right) {
            swap(s, left, right);
            left++;
            right--;
        }
    }

    /**
     * 给你一个字符串 s ，请你反转字符串中 单词 的顺序。
     *
     * 单词 是由非空格字符组成的字符串。s 中使用至少一个空格将字符串中的 单词 分隔开。
     *
     * 返回 单词 顺序颠倒且 单词 之间用单个空格连接的结果字符串。
     *
     * 注意：输入字符串 s中可能会存在前导空格、尾随空格或者单词间的多个空格。返回的结果字符串中，单词间应当仅用单个空格分隔，且不包含任何额外的空格。
     *
     * 示例 1：
     *
     * 输入：s = "the sky is blue"
     * 输出："blue is sky the"
     *
     * 示例 2：
     *
     * 输入：s = "  hello world  "
     * 输出："world hello"
     * 解释：反转后的字符串中不能存在前导空格和尾随空格。
     * @param s
     * @return
     */
    public String reverseWords(String s) {
        StringBuilder sb = new StringBuilder();
        //从后向前遍历字符串 ，左闭右闭
        int right = s.length() - 1;
        // 从尾部开始跳过尾随空格
        while (right >= 0 && s.charAt(right) == ' ') {
            right--;
        }

        int left = right;
        while (left >= 0) {
            if (s.charAt(left) == ' ') {
                if (sb.length() > 0) {
                    sb.append(' ');
                }
                //把【left+1,right】的部分放入stringBuilder中
                for (int i = left + 1; i <= right; i++) {
                    sb.append(s.charAt(i));
                }

                //移动到下一个非空格的位置
                while (left >= 0 && s.charAt(left) == ' '){
                    left--;
                }
                right = left;
            } else if (left == 0) {
                //把【left,right】的部分放入stringBuilder中
                if (sb.length() > 0) {
                    sb.append(' ');
                }
                for (int i = left; i <= right; i++) {
                    sb.append(s.charAt(i));
                }
                left--;
            } else {
                left--;
            }
        }
        return sb.toString();
    }

    /**
     * 右旋字符串（第八期模拟笔试）
     * 题目描述
     * 字符串的右旋转操作是把字符串尾部的若干个字符转移到字符串的前面。给定一个字符串 s 和一个正整数 k，请编写一个函数，将字符串中的后面 k 个字符移到字符串的前面，实现字符串的右旋转操作。
     *
     * 例如，对于输入字符串 "abcdefg" 和整数 2，函数应该将其转换为 "fgabcde"。
     *
     * 输入描述
     * 输入共包含两行，第一行为一个正整数 k，代表右旋转的位数。第二行为字符串 s，代表需要旋转的字符串。
     * 输出描述
     * 输出共一行，为进行了右旋转操作后的字符串。
     * 输入示例
     * 2
     * abcdefg
     * 输出示例
     * fgabcde
     * 提示信息
     * 数据范围：
     * 1 <= k < 10000,
     * 1 <= s.length < 10000;
     */
    public String rightRotateString(String s, int k) {
        k = k % s.length();
        if (k == 0) {
            return s;
        } else {
            StringBuilder sb = new StringBuilder();
            //从倒数K个数字开始
            for (int i = s.length() - k; i < s.length(); i++) {
                sb.append(s.charAt(i));
            }
            for (int i = 0; i < s.length() - k; i++) {
                sb.append(s.charAt(i));
            }
            return sb.toString();
        }
    }

    /**
     * 给你两个字符串 haystack 和 needle ，请你在 haystack 字符串中找出 needle 字符串的第一个匹配项的下标（下标从 0 开始）。如果 needle 不是 haystack 的一部分，则返回  -1 。
     *
     *
     *
     * 示例 1：
     *
     * 输入：haystack = "sadbutsad", needle = "sad"
     * 输出：0
     * 解释："sad" 在下标 0 和 6 处匹配。
     * 第一个匹配项的下标是 0 ，所以返回 0 。
     * 示例 2：
     *
     * 输入：haystack = "leetcode", needle = "leeto"
     * 输出：-1
     * 解释："leeto" 没有在 "leetcode" 中出现，所以返回 -1 。
     *
     *
     * 提示：
     *
     * 1 <= haystack.length, needle.length <= 104
     * haystack 和 needle 仅由小写英文字符组成
     */
    public int strStr(String haystack, String needle) {
        int left = 0, right = 0;
        while (left < haystack.length()) {
            int cur = left;
            while (left < haystack.length() && right < needle.length() && haystack.charAt(left) == needle.charAt(right)) {
                left++;
                right++;
            }
            if (right == needle.length()) {
                return cur;
            } else {
                left = cur + 1;
                right = 0;
            }
        }
        return -1;
    }
    @Test
    public void test(){
        String s = "abcdefg";
        int k = 2;
        System.out.println(rightRotateString(s, k));
    }
}
