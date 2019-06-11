package com.huawesoft.lil1.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.commons.codec.binary.Base64;

/**
 * @author lil1
 * @date 2018年12月14日 上午11:25:33
 * @Description 图片转码
 */
public class ImageToBase64 {
	
	
	/**
	 * @Description 通过URL获取图片，并转码Base64
	 * @param 图片链接
	 * @return String
	 */
	public static String ImageToBase64ByURL(String path) throws IOException {
		URL url = null;
		HttpURLConnection urlconnection = null;
		InputStream in = null;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] data = null;
		try {
			url = new URL(path);
			urlconnection = (HttpURLConnection) url.openConnection();
			urlconnection.connect();
			in = urlconnection.getInputStream();
			byte[] b = new byte[1024];
			int len = 0;
			while ((len = in.read(b)) != -1) {
				baos.write(b, 0, len);
			}
			data = baos.toByteArray();
			in.close();
			baos.close();
			urlconnection.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (in != null) {
				in.close();
				urlconnection.disconnect();
			}
		}
		return new String(Base64.encodeBase64(data));
	}
}
