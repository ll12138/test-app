package com.huawesoft.lil1.encrypt;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

import org.springframework.util.Base64Utils;

/**
 * @author lil1
 * @date 2019年6月4日 下午4:53:09
 * @Description 非对称加密 --RSA
 */
public class RSAUtils {

    public static void main(String[] arg) {
        encryptByRSA();
    }

    public static void encryptByRSA() {
        String string = "rsa 加密 test";
        try {
            KeyPair keyPair = getKeyPair();
            RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) keyPair.getPrivate();
            RSAPublicKey rsaPublicKey = (RSAPublicKey) keyPair.getPublic();
            System.out.println("privateKey:" + Base64Utils.encodeToString(rsaPrivateKey.getEncoded()));
            System.out.println("publicKey:" + Base64Utils.encodeToString(rsaPublicKey.getEncoded()));
			
			/*
			 * //私钥加密,公钥解密---加密
				PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec (rsaPrivateKey.getEncoded());
				KeyFactory keyFactory = KeyFactory.getInstance("RSA");
				PrivateKey privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
				Cipher cipher = Cipher.getInstance("RSA");
				cipher.init(Cipher.ENCRYPT_MODE, privateKey);
				byte[] result = cipher.doFinal(string.getBytes());
				System.out.println("私钥加密,公钥解密---加密:" + Base64Utils.encodeToString(result));
				
				//私钥加密,公钥解密---解密
				X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(rsaPublicKey.getEncoded());
				keyFactory = KeyFactory.getInstance("RSA");
				PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
				cipher = Cipher.getInstance("RSA");
				cipher.init(Cipher.DECRYPT_MODE, publicKey);
				byte[] deresult = cipher.doFinal(result);
				System.out.println("私钥加密,公钥解密---解密:" + new String(deresult));
			*/


            //公钥加密,私钥解密--加密
            X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(rsaPublicKey.getEncoded());
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            byte[] result = cipher.doFinal(string.getBytes());
            System.out.println("公钥加密,私钥解密---加密:" + Base64Utils.encodeToString(result));

            //公钥加密,私钥解密--解密
            PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(rsaPrivateKey.getEncoded());
            keyFactory = KeyFactory.getInstance("RSA");
            PrivateKey privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
            cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            byte[] deresult = cipher.doFinal(result);
            System.out.println("公钥加密,私钥解密---解密:" + new String(deresult));


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private static KeyPair getKeyPair() throws Exception {
        KeyPairGenerator senderKeyPairGenerator = KeyPairGenerator.getInstance("RSA");
        //秘钥长度
        senderKeyPairGenerator.initialize(512);
        KeyPair senderKeyPair = senderKeyPairGenerator.generateKeyPair();
        return senderKeyPair;
    }
}
