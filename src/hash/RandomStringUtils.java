package hash;

import java.security.SecureRandom;

public class RandomStringUtils {
    private static final String ALPHANUMERIC_CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final SecureRandom RANDOM = new SecureRandom();

    /**
     * 生成指定长度的随机字母数字字符串。
     *
     * @param count 字符串长度
     * @return 随机字母数字字符串
     */
    public static String randomAlphanumeric(int count) {
        return random(count, ALPHANUMERIC_CHARACTERS);
    }

    private static String random(int count, String characters) {
        StringBuilder sb = new StringBuilder(count);
        for (int i = 0; i < count; i++) {
            int index = RANDOM.nextInt(characters.length());
            sb.append(characters.charAt(index));
        }
        return sb.toString();
    }
}
