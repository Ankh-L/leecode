package stack;

import org.junit.jupiter.api.Test;

import java.util.*;

/**
 * @author linyw
 */
public class Main {

    class MyQueue {
        Stack<Integer> stack1;
        Stack<Integer> stack2;
        public MyQueue() {
            stack1 = new Stack<>();
            stack2 = new Stack<>();
        }

        public void push(int x) {
            while (!stack2.empty()) {
                Integer value = stack2.pop();
                stack1.push(value);
            }
            stack1.push(x);
        }

        public int pop() {
            while (!stack1.empty()) {
                Integer value = stack1.pop();
                stack2.push(value);
            }
            return stack2.pop();
        }

        public int peek() {
            while (!stack1.empty()) {
                Integer value = stack1.pop();
                stack2.push(value);
            }
            return stack2.peek();
        }

        public boolean empty() {
            return stack1.empty() && stack2.empty();
        }
    }

    class MyStack {
        Queue<Integer> queue1;
        Queue<Integer> queue2;
        public MyStack() {
            queue1 = new LinkedList<>();
            queue2 = new LinkedList<>();
        }

        public void push(int x) {
            queue1.offer(x);
        }

        public int pop() {
            int result = -1;
            while (queue1.size() != 0) {
                Integer value = queue1.poll();
                if (queue1.size() == 0) {
                    result = value;
                    break;
                }
                queue2.offer(value);
            }
            Queue<Integer> temp = queue2;
            queue2 = queue1;
            queue1 = temp;
            return result;
        }

        public int top() {
            int result = -1;
            while (queue1.size() != 0) {
                Integer value = queue1.poll();
                if (queue1.size() == 0) {
                    result = value;
                }
                queue2.offer(value);
            }
            Queue<Integer> temp = queue2;
            queue2 = queue1;
            queue1 = temp;
            return result;
        }

        public boolean empty() {
            return queue1.isEmpty() && queue2.isEmpty();
        }
    }
    //难点，treeSet不支持存放重复的元素
    public int[] maxSlidingWindow(int[] nums, int k) {
        //初始化
        MonotonicQueue monotonicQueue = new MonotonicQueue();
        for (int i = 0; i < k; i++) {
            monotonicQueue.push(nums[i]);
        }
        int left = 0, right = k - 1;
        int[] ans = new int[nums.length - k + 1];
        while (right < nums.length) {
            //获取最大值，然后移动窗口
            ans[left] = monotonicQueue.front();
            monotonicQueue.pop(nums[left]);
            if (right + 1 < nums.length) {
                monotonicQueue.push(nums[right + 1]);
            }
            left++;
            right++;
        }
        return ans;
    }

    @Test
    public void test() {
        int[] nums = {-7,-8,7,5,7,1,6,0};
        int[] ints = maxSlidingWindow(nums, 4);
        System.out.println(Arrays.toString(ints));
    }

    public int[] topKFrequent(int[] nums, int k) {
        //统计次数， key是数字，value是次数
        Map<Integer, Integer> map = new HashMap<>();
        for (int num : nums) {
            map.put(num, map.getOrDefault(num, 0) + 1);
        }
        //放入大顶堆取topk
        PriorityQueue<Element> priorityQueue = new PriorityQueue<>((o1, o2) -> o1.times - o2.times);
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            if (priorityQueue.size() < k) {
                priorityQueue.add(new Element(entry.getKey(), entry.getValue()));
            } else if (priorityQueue.peek().times < entry.getValue()) {
                priorityQueue.poll();
                priorityQueue.add(new Element(entry.getKey(), entry.getValue()));
            }
        }
        int[] ans = new int[k];
        for(int i = k -1 ; i >= 0; i--) {
            ans[i] = priorityQueue.poll().value;
        }
        return ans;
    }
    class Element {
        int value;
        int times;

        public Element(int value, int times) {
            this.value = value;
            this.times = times;
        }
    }
    class MonotonicQueue { //单调队列（从大到小）
        Deque<Integer> que = new LinkedList<>(); // 使用deque来实现单调队列
        // 每次弹出的时候，比较当前要弹出的数值是否等于队列出口元素的数值，如果相等则弹出。
        // 同时pop之前判断队列当前是否为空。
        void pop(int value) {
            if (!que.isEmpty() && value == que.peek()) {
                que.pop();
            }
        }

        /**
         * 如果push的数值大于入口元素的数值，那么就将队列后端的数值弹出，
         * 直到push的数值小于等于队列入口元素的数值为止。这样就保持了队列里的数值是单调从大到小的了。
         * @param value
         */
        void push(int value) {
            //获取入口元素
            while (!que.isEmpty() && value > que.peekLast()) {
                que.pollLast();
            }
            que.add(value);
        }
        // 查询当前队列里的最大值 直接返回队列前端也就是front就可以了。
        int front() {
            return que.peek();
        }
    }

}
