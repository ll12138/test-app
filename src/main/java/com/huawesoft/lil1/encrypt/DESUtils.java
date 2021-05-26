package com.huawesoft.lil1.encrypt;

import java.security.Key;
import java.util.Objects;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.util.Base64Utils;

/**
 * @author lil1
 * @date 2019年6月6日 下午3:04:25
 * @Description 对称加密--DES 3DES AES PBE
 */
public class DESUtils {
	
	public static void main(String[] arg) {
		try {
			encryptDES();
			encrypt3DES();
			encryptAES();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void encryptDES() throws Exception{
		/*String key = "gffrefFDSFUH";
		DESKeySpec desKeySpec = new DESKeySpec(key.getBytes());
		*/
		
		//生成key
		KeyGenerator keyGenerator = KeyGenerator.getInstance("DES");
		keyGenerator.init(56);//must be equal to 56
		SecretKey key = keyGenerator.generateKey();
		
		
		//生成密钥
		DESKeySpec desKeySpec = new DESKeySpec(key.getEncoded());
		SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("DES");
		Key desKey1 = secretKeyFactory.generateSecret(desKeySpec);
		
		Key desKey2 = new SecretKeySpec(key.getEncoded(),"DES");
		if(Objects.equals(desKey1, desKey2)){
			System.out.println("desKey1 = desKey2:"+Base64Utils.encodeToString(desKey1.getEncoded()));
		}
		
		//加密
		Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding"); // (算法/工作模式/填充方式)
		cipher.init(Cipher.ENCRYPT_MODE, desKey1);
		byte[] result = cipher.doFinal("测试DES加密".getBytes());
		//解密
		cipher.init(Cipher.DECRYPT_MODE, desKey1);
		byte[] str = cipher.doFinal(result);
		System.out.println("解密:"+new String(str));
		
	}
	
	
	public static void encrypt3DES() throws Exception{
		//生成key
		KeyGenerator keyGenerator = KeyGenerator.getInstance("DESede");
		keyGenerator.init(112); //112~168
		SecretKey key = keyGenerator.generateKey();
		
		//生成密钥
		DESedeKeySpec desKeySpec = new DESedeKeySpec(key.getEncoded());
		SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("DESede");
		Key desKey1 = secretKeyFactory.generateSecret(desKeySpec);
		
		Key desKey2 = new SecretKeySpec(key.getEncoded(),"DESede");
		
		if(Objects.equals(desKey1, desKey2)){
			System.out.println("3desKey1 = 3desKey2:"+Base64Utils.encodeToString(desKey1.getEncoded()));
		}
		
		//加密
		Cipher cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding"); // (算法/工作模式/填充方式)
		cipher.init(Cipher.ENCRYPT_MODE, desKey1);
		byte[] result = cipher.doFinal("测试3DES加密".getBytes());
		//解密
		cipher.init(Cipher.DECRYPT_MODE, desKey1);
		byte[] str = cipher.doFinal(result);
		System.out.println("解密:"+new String(str));
	}
	
	
	
	public static void encryptAES() throws Exception{
		//生成key
		KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
		keyGenerator.init(128); // 128, 192 or 256
		SecretKey key = keyGenerator.generateKey();
		
		//生成密钥
		Key aesKey = new SecretKeySpec(key.getEncoded(),"AES");
		//加密
		Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding"); // (算法/工作模式/填充方式)
		cipher.init(Cipher.ENCRYPT_MODE, aesKey);
		byte[] result = cipher.doFinal("测试AES加密".getBytes());
		//解密
		cipher.init(Cipher.DECRYPT_MODE, aesKey);
		byte[] str = cipher.doFinal(result);
		System.out.println("解密:"+new String(str));
		
	}
}
