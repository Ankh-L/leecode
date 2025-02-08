package design;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;

/**
 * @author linyw
 */
public class LFUCache {
    /**
     * 460. LFU 缓存
     * 困难
     * 704
     * 相关企业
     * 请你为 最不经常使用（LFU）缓存算法设计并实现数据结构。
     *
     * 实现 LFUCache 类：
     *
     * LFUCache(int capacity) - 用数据结构的容量 capacity 初始化对象
     * int get(int key) - 如果键 key 存在于缓存中，则获取键的值，否则返回 -1 。
     * void put(int key, int value) - 如果键 key 已存在，则变更其值；如果键不存在，请插入键值对。当缓存达到其容量 capacity 时，则应该在插入新项之前，移除最不经常使用的项。在此问题中，当存在平局（即两个或更多个键具有相同使用频率）时，应该去除 最近最久未使用 的键。
     * 为了确定最不常使用的键，可以为缓存中的每个键维护一个 使用计数器 。使用计数最小的键是最久未使用的键。
     *
     * 当一个键首次插入到缓存中时，它的使用计数器被设置为 1 (由于 put 操作)。对缓存中的键执行 get 或 put 操作，使用计数器的值将会递增。
     *
     * 函数 get 和 put 必须以 O(1) 的平均时间复杂度运行。
     *
     * // cnt(x) = 键 x 的使用计数
     * // cache=[] 将显示最后一次使用的顺序（最左边的元素是最近的）
     * LFUCache lfu = new LFUCache(2);
     * lfu.put(1, 1);   // cache=[1,_], cnt(1)=1
     * lfu.put(2, 2);   // cache=[2,1], cnt(2)=1, cnt(1)=1
     * lfu.get(1);      // 返回 1
     *                  // cache=[1,2], cnt(2)=1, cnt(1)=2
     * lfu.put(3, 3);   // 去除键 2 ，因为 cnt(2)=1 ，使用计数最小
     *                  // cache=[3,1], cnt(3)=1, cnt(1)=2
     * lfu.get(2);      // 返回 -1（未找到）
     * lfu.get(3);      // 返回 3
     *                  // cache=[3,1], cnt(3)=2, cnt(1)=2
     * lfu.put(4, 4);   // 去除键 1 ，1 和 3 的 cnt 相同，但 1 最久未使用
     *                  // cache=[4,3], cnt(4)=1, cnt(3)=2
     * lfu.get(1);      // 返回 -1（未找到）
     * lfu.get(3);      // 返回 3
     *                  // cache=[3,4], cnt(4)=1, cnt(3)=3
     * lfu.get(4);      // 返回 4
     *                  // cache=[3,4], cnt(4)=2, cnt(3)=3
     */
    //为了实现get set 需要一个map.
    //当缓存达到其容量 capacity 时，则应该在插入新项之前，移除最不经常使用的项。 所以需要统计每一个键的使用次数
    //当次数一样时需要找到最久没用的键，所以也要记录最近使用过的键的列表  先进后出？ treeset
    Map<Integer, Node> map;
    TreeSet<Node> set; 
    int capacity, time;
    public LFUCache(int capacity) {
        this.capacity = capacity;
        map = new HashMap<>(capacity);
        set = new TreeSet<>();
        time = 0;
    }

    public int get(int key) {
        Node node = getNode(key);
        if (node == null) {
            return -1;
        } else {
            updateNode(node, node.value);
            return node.value;
        }
    }

    private Node getNode(int key) {
        return map.get(key);
    }

    public void put(int key, int value) {
        Node oldNode = getNode(key);
        if (oldNode == null) {
            //之前不存在，需要新增
            //当缓存达到其容量 capacity 时，则应该在插入新项之前，移除最不经常使用的项。 所以需要统计每一个键的使用次数
            if (map.size() == capacity) {
                //移除,找到用的最少的
                Node first = set.first();
                map.remove(first.key, first);
                set.remove(first);
            }
            Node node = new Node(1, ++time, key, value);
            map.put(key, node);
            set.add(node);
        } else {
           //更新值
            updateNode(oldNode, value);
        }
    }
    
    public void updateNode(Node node, int newValue) {
        set.remove(node);
        //更新node 次数++,时间更新
        node.cnt += 1;
        node.time = ++time;
        node.value = newValue;
        set.add(node);
    }
    class Node implements Comparable<Node>{
        int cnt;
        int time;
        int key;
        int value;
        Node (int cnt, int time, int key, int value) {
            this.cnt = cnt;
            this.time = time;
            this.key = key;
            this.value = value;
        }

        @Override
        public int compareTo(Node rhs) {
            return cnt == rhs.cnt ? time - rhs.time : cnt - rhs.cnt;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Node)) return false;

            Node node = (Node) o;

            if (cnt != node.cnt) return false;
            return time == node.time;
        }

        @Override
        public int hashCode() {
            int result = cnt;
            result = 31 * result + time;
            return result;
        }
    }

    public static void main(String[] args) {
        LFUCache lfu = new LFUCache(2);
        lfu.put(1, 1);   // cache=[1,_], cnt(1)=1
        lfu.put(2, 2);   // cache=[2,1], cnt(2)=1, cnt(1)=1
        int i = lfu.get(1);// 返回 1
        System.out.println(i);
// cache=[1,2], cnt(2)=1, cnt(1)=2
        lfu.put(3, 3);   // 去除键 2 ，因为 cnt(2)=1 ，使用计数最小
        // cache=[3,1], cnt(3)=1, cnt(1)=2
        int i1 = lfu.get(2);// 返回 -1（未找到）
        System.out.println(i1);
        int i2 = lfu.get(3);// 返回 3
        System.out.println(i2);
// cache=[3,1], cnt(3)=2, cnt(1)=2
        lfu.put(4, 4);   // 去除键 1 ，1 和 3 的 cnt 相同，但 1 最久未使用
        // cache=[4,3], cnt(4)=1, cnt(3)=2
        int i3 = lfu.get(1);// 返回 -1（未找到）
        System.out.println(i3);
        int i4 = lfu.get(3);// 返回 3
        System.out.println(i4);
// cache=[3,4], cnt(4)=1, cnt(3)=3
        int i5 = lfu.get(4);// 返回 4
        System.out.println(i5);
// cache=[3,4], cnt(4)=2, cnt(3)=3
    }
}
