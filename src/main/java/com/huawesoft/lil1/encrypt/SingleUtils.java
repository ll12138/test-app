package com.huawesoft.lil1.encrypt;

import java.security.MessageDigest;
import java.util.Base64;

import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;

import org.springframework.util.Base64Utils;

/**
 * @author lil1
 * @date 2019年6月6日 下午4:15:59
 * @Description MD SHA MAC Base64
 */
public class SingleUtils {
	private static final String SRC = "消息摘要加密算法";

	public static void main(String[] arg) {
		try {
			encryptMD5();
			encryptBase64();
			encryptSHA();
			encryptMAC();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @Description 消息摘要算法 MD5 MD2均可由此实现,除MD4
	 */
	public static void encryptMD5() throws Exception {
		MessageDigest md = MessageDigest.getInstance("MD5");
		byte[] result = md.digest(SRC.getBytes());
		md.update(SRC.getBytes());
		byte[] result2 = md.digest(SRC.getBytes());
		System.out.println("MD5 加密:(byte)" + result + "(Base64)" + Base64Utils.encodeToString(result));
		System.out.println("update MD5:(byte)" + result2 + "(Base64)" + Base64Utils.encodeToString(result2));
	}

	public static void encryptBase64() throws Exception {
		String result = Base64.getEncoder().encodeToString(SRC.getBytes());
		byte[] result2 = Base64.getEncoder().encode(SRC.getBytes());
		System.out.println("Base64 encodeToString 加密:"+result);
		System.out.println("Base64 encode 加密:"+new String(result2));
		System.out.println("Base64 解密:"+new String(Base64.getDecoder().decode(result)));
	}

	/**
	 * @Description 安全散列算法
	 */
	public static void encryptSHA() throws Exception {
		MessageDigest md = MessageDigest.getInstance("SHA");
		byte[] result = md.digest(SRC.getBytes());
		System.out.println("SHA 加密:" + Base64Utils.encodeToString(result));
	}
	
	/**
	 * @Description 消息认证码算法
	 */
	public static void encryptMAC() throws Exception {
		KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
		keyGenerator.init(128); // 128, 192 or 256
		SecretKey key = keyGenerator.generateKey();
		
		Mac mac = Mac.getInstance("HmacMD5");
		mac.init(key);
		byte[] result = mac.doFinal(SRC.getBytes());
		System.out.println("MAC-HmacMD5 加密:" + Base64Utils.encodeToString(result));
		
		
	}
}
