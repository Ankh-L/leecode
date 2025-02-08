package hash;

import org.junit.jupiter.api.Assertions;

import java.util.ArrayList;
import java.util.List;

/**
 * @author linyw
 */
public class ConsistentHashTest {
    // 定义节点数量
    public static final int NODE_SIZE = 10;
    // 定义字符串数量，代表需要缓存的数据量
    public static final int STRING_COUNT = 100 * 100;
    // 创建一致性哈希实例
    private static ConsistentHash consistentHash = new ConsistentHash();
    // 创建字符串列表，用于存储需要缓存的数据
    private static List<String> sList = new ArrayList<>();

    public static void main(String[] args) {
        // 增加节点
        for (int i = 0; i < NODE_SIZE; i++) {
            String ip = new StringBuilder("10.2.1.").append(i)
                    .toString();
            consistentHash.addNode(ip);
        }

        // 生成需要缓存的数据;
        for (int i = 0; i < STRING_COUNT; i++) {
            sList.add(RandomStringUtils.randomAlphanumeric(10));
        }

        // 将数据放入到缓存中。
        for (String s : sList) {
            consistentHash.put(s, s);
        }

        // 随机选取数据，测试缓存获取
        for(int i = 0 ; i < 10 ; i ++) {
            int index = RandomUtils.nextInt(0, STRING_COUNT);
            String key = sList.get(index);
            String cache = (String) consistentHash.get(key);
            Assertions.assertEquals(key, cache);
        }

        // 输出节点及数据分布情况
        for (Node node : consistentHash.nodeList){
            System.out.println(node);
        }

        // 新增一个数据节点
        consistentHash.addNode("10.2.1.110");
        // 再次随机选取数据，测试缓存获取
        for(int i = 0 ; i < 10 ; i ++) {
            int index = RandomUtils.nextInt(0, STRING_COUNT);
            String key = sList.get(index);
            String cache = (String) consistentHash.get(key);
            Assertions.assertEquals(key, cache);
        }

        // 输出节点及数据分布情况
        for (Node node : consistentHash.nodeList){
            System.out.println(node);
        }
    }


}
