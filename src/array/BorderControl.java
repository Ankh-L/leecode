package array;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author linyw
 */
public class BorderControl {

    public List<Integer> spiralOrder(int[][] matrix) {
        List<Integer> ans = new ArrayList<>();
        //初始化边界， 左闭右闭
        int left = 0, right = matrix[0].length - 1, top = 0, bottom = matrix.length - 1;
        while(ans.size() < matrix.length * matrix[0].length) {
            //从左上到右上
            for (int x = top, y = left; y <= right; y++) {
                ans.add(matrix[x][y]);
            }
            top++;
            if (ans.size() == matrix.length * matrix[0].length) break;

            //从右上到右下
            for (int x = top , y = right; x <= bottom; x++) {
                ans.add(matrix[x][y]);
            }
            right--;
            if (ans.size() == matrix.length * matrix[0].length) break;

            //从右下到左下
            for (int x = bottom, y =  right; y >= left; y--) {
                ans.add(matrix[x][y]);
            }
            bottom--;
            if (ans.size() == matrix.length * matrix[0].length) break;

            //从左下到左上
            for (int x = bottom, y = left; x >= top; x--) {
                ans.add(matrix[x][y]);
            }
            left++;
        }
        return ans;
    }
    /**给你一个正整数 n ，生成一个包含 1 到 n2 所有元素，且元素按顺时针顺序螺旋排列的 n x n 正方形矩阵 matrix 。
     * [[1,2,3],
     * [8,9,4],
     * [7,6,5]]
     */

    public int[][] generateMatrix(int n) {
        int[][] matrix = new int[n][n];
        //左闭右闭
        int left = 0, right = n - 1, top = 0, bottom = n - 1 ;
        int count = 1;
        while (count <= n * n) {
            //从左上到右上 纵坐标变，横坐标不变
            for(int x = top, y = left; y <= right; y++) {
                matrix[x][y] = count++;
            }
            top++;
            //从右上到右下， 横坐标变，纵坐标不变
            for(int x = top, y = right; x <= bottom; x++) {
                matrix[x][y] = count++;
            }
            right--;
            //从右下到左下
            for (int x = bottom, y = right; y >= left; y--) {
                matrix[x][y] = count++;
            }
            bottom--;
            //从左下到左上 横坐标变，纵坐标不变
            for (int x = bottom, y = left; x >= top; x--) {
                matrix[x][y] = count++;
            }
            left++;
        }
        return matrix;
    }

    @Test
    public void fun() {
//        int[][] matrix = {{1,2,3,4},{5,6,7,8},{9,10,11,12}};
//        List<Integer> integers = spiralOrder(matrix);

//        int[] arr = new int[]{1,2,3,4,5};
//        System.out.println(sum(arr, 0 ,1));
//        System.out.println(sum(arr, 1 ,3));

        int[][] matrix = {{1,2,3},{2,1,3},{1,2,3}};
        int sumplus = sumplus(matrix);
        System.out.println(sumplus);
    }
    /**
     * 题目描述
     * 给定一个整数数组 Array，请计算该数组在每个指定区间内元素的总和。
     * 输入描述
     * 第一行输入为整数数组 Array 的长度 n，接下来 n 行，每行一个整数，表示数组的元素。随后的输入为需要计算总和的区间下标：a，b （b > = a），直至文件结束。
     * 输出描述
     * 输出每个指定区间内元素的总和。
     * 输入示例
     * 5
     * 1
     * 2
     * 3
     * 4
     * 5
     * 0 1
     * 1 3
     */
    public int sum(int[] arr, int start, int end) {
        //1 2 3  4  5
        //1 3 6 10 15
        //初始化总和数组sum,sum[i]表示累加到第i项时的总和
        int[] sum = new int[arr.length];
        sum[0] = arr[0];
        for(int i = 1; i < arr.length; i++) {
            sum[i] = sum[i - 1] + arr[i];
        }
        if (start - 1 < 0) {
            return sum[end];
        } else {
            return sum[end] - sum[start - 1];
        }
    }



    public int sumplus(int[][] arr) {
        int row = arr.length;
        int col = arr[0].length;
        //初始化sum，sum[i]表示，累加到第i列时的总和
        int[] sum = new int[col];
        for (int i = 0; i < row; i++) {
            sum[0] += arr[i][0];
        }
        for (int curCol = 1; curCol < col; curCol++) {
            int temp = 0;
            for (int curRow = 0; curRow < row; curRow++) {
                temp += arr[curRow][curCol];
            }
            sum[curCol] = sum[curCol - 1] + temp;
        }
        //遍历求解
        int ans = Integer.MAX_VALUE;
        for (int i = 1; i < col; i++) {
            int left = sum[i];
            int right = sum[col - 1] - sum[i];
            ans = Math.min(ans , Math.abs(left - right));
        }
        return ans;
    }
}
