package top.rectorlee.utils;

import cn.hutool.crypto.symmetric.SymmetricAlgorithm;
import cn.hutool.crypto.symmetric.SymmetricCrypto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;

/**
 * @author Lee
 * @description
 * @date 2023-04-24  17:52:13
 */
@Component
public class AesUtil {
    // AES工具声明
    private static SymmetricCrypto aes;

    // 密钥字符串
    private static String secret;

    @Value("${aes.secret}")
    public void setSecret(String secret) {
        AesUtil.secret = secret;
    }

    @PostConstruct
    public void init() {
        // AES工具构建
        byte[] key = secret.getBytes(StandardCharsets.UTF_8);
        aes = new SymmetricCrypto(SymmetricAlgorithm.AES, key);
    }

    /**
     * 加密
     */
    public static String encrypt(String content) {
        return aes.encryptHex(content);
    }

    /**
     * 解密
     */
    public static String decrypt(String encryptStr) {
        return aes.decryptStr(encryptStr);
    }
}
