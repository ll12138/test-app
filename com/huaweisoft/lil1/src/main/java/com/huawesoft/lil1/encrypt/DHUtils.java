package com.huawesoft.lil1.encrypt;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Objects;

import javax.crypto.Cipher;
import javax.crypto.KeyAgreement;
import javax.crypto.SecretKey;
import javax.crypto.interfaces.DHPublicKey;
import javax.crypto.spec.DHParameterSpec;

import org.springframework.util.Base64Utils;

/**
 * @author lil1
 * @date 2019年6月4日 下午3:22:19
 * @Description 非对称加密 --DH秘钥交换算法 
 */
public class DHUtils {
	public static void main(String[] arg) {
		encryptByDH();
	}
	
	public static void encryptByDH(){
		
		try {
			/* 1.发送方: 创建公钥+私钥
			 * 2.公钥-->接收方
			 */ 
			KeyPair senderKeyPair = getSenderPair();
			byte[] senderPublicKeyEncode = senderKeyPair.getPublic().getEncoded();
			
			/*3.接收方: 通过发送方的公钥创建公钥+私钥 
			 * 4.公钥-->发送方
			 */		
			KeyPair receiverKeyPair = getRecevierPairBySender(senderPublicKeyEncode);
			byte[] receiverPublicKeyEncode = receiverKeyPair.getPublic().getEncoded();
			
			/* 5.发送方: 私钥+接收方公钥创建本地加密秘钥*/
			SecretKey senderDesKey = GetSecretKey(receiverKeyPair,senderPublicKeyEncode);
			
			/* 6.接收方: 私钥+发送方公钥创建本地解密秘钥*/
			SecretKey receiverDesKey = GetSecretKey(senderKeyPair,receiverPublicKeyEncode);
			
			if(Objects.equals(senderDesKey, receiverDesKey)){
				System.out.println("秘钥相同");
			}
			
			//加密
			String string = "DH 加密 test";
			Cipher cipher = Cipher.getInstance("DES");
			cipher.init(Cipher.ENCRYPT_MODE, senderDesKey);
			byte[] encryptCode = cipher.doFinal(string.getBytes());
			System.out.println("DH encrypt :" + Base64Utils.encodeToString(encryptCode));
			
			//解密
			cipher.init(Cipher.DECRYPT_MODE, senderDesKey);
			byte[] decryptCode = cipher.doFinal(encryptCode);
			System.out.println("DH decrypt :" + new String(decryptCode));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	



	/**
	 * @Description 创建发送方公钥+私钥
	 * @return KeyPair
	 */
	private static KeyPair getSenderPair() throws Exception{
		KeyPairGenerator senderKeyPairGenerator = KeyPairGenerator.getInstance("DH");
		//秘钥长度
		senderKeyPairGenerator.initialize(512);
		KeyPair senderKeyPair = senderKeyPairGenerator.generateKeyPair();
		return senderKeyPair;
	}
	
	
	/**
	 * @Description 根据发送方公钥,创建接收方公钥+秘钥
	 * @param senderPublicKeyEncode 发送方公钥
	 * @return KeyPair
	 */
	private  static KeyPair getRecevierPairBySender(byte[] senderPublicKeyEncode) throws Exception{
		KeyFactory recevierFactory = KeyFactory.getInstance("DH");
		//X509EncodedKeySpec秘钥编码标准
		X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(senderPublicKeyEncode);
		PublicKey publicKey = recevierFactory.generatePublic(x509EncodedKeySpec);
		//DHParameterSpec DH算法中使用的参数集合
		DHParameterSpec dhParameterSpec = ((DHPublicKey) publicKey).getParams();
		
		//使用与发送方的公钥一样的参数初始化构造器
		KeyPairGenerator senderKeyPairGenerator = KeyPairGenerator.getInstance("DH");
		senderKeyPairGenerator.initialize(dhParameterSpec);
		KeyPair receiverKeyPair = senderKeyPairGenerator.generateKeyPair();
		return receiverKeyPair;
		
	}
	
	
	/**
	 * @Description 根据己方私钥+对方公钥,创建本地的数据加密秘钥
	 * @param receiverKeyPair
	 * @param senderPublicKeyEncode
	 */
	private static SecretKey GetSecretKey(KeyPair keyPair, byte[] PublicKeyEncode) throws Exception{
		KeyFactory recevierFactory = KeyFactory.getInstance("DH");
		X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(PublicKeyEncode);
		PublicKey publicKey = recevierFactory.generatePublic(x509EncodedKeySpec);
		
		KeyAgreement keyAgreement =KeyAgreement.getInstance("DH"); 
		keyAgreement.init(keyPair.getPrivate());
		keyAgreement.doPhase(publicKey, true);
		/*
		 * DK8 update 161之后,DH长度至少512,而DES AES的长度达不到,报不支持AES/DES异常
		 * 解决:run configurations(arguments)配置jvm参数:  -Djdk.crypto.KeyAgreement.legacyKDF=true
		 */
		return keyAgreement.generateSecret("DES");
	}
}
