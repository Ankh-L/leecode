package hash;

import java.security.SecureRandom;

public class RandomUtils {
    private static final SecureRandom RANDOM = new SecureRandom();

    /**
     * 生成指定范围内的随机整数。
     *
     * @param startInclusive 起始值（包含）
     * @param endExclusive 结束值（不包含）
     * @return 随机整数
     */
    public static int nextInt(int startInclusive, int endExclusive) {
        if (startInclusive >= endExclusive) {
            throw new IllegalArgumentException("Start value must be less than end value.");
        }
        return startInclusive + RANDOM.nextInt(endExclusive - startInclusive);
    }
}
