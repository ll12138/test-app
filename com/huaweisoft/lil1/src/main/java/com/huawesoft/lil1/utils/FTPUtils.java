package com.huawesoft.lil1.utils;

import java.io.IOException;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPConnectionClosedException;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author lil1
 * @date 2019年5月20日 下午5:17:07
 * @Description 
 */
public class FTPUtils {
	private static final Logger LOGGER = LoggerFactory.getLogger(FTPUtils.class);

    /**
     * @Description 连接ftp服务器
     */
    public  void connectToServer(FTPClient ftpClient,String ip,int port,String userName,String passWord) throws FTPConnectionClosedException,Exception { 
        if (!ftpClient.isConnected()) { 
            int reply; 
            try { 
                ftpClient.connect(ip,port);
                //设置主动模式
                ftpClient.enterLocalActiveMode();
                ftpClient.login(userName,passWord);
                reply = ftpClient.getReplyCode(); 
 
                if (!FTPReply.isPositiveCompletion(reply)) { 
                    ftpClient.disconnect(); 
                    LOGGER.info("connectToServer FTP server refused connection."); 
                } 
            
            }catch(FTPConnectionClosedException e){
            	LOGGER.error("服务器:IP："+ip+"没有连接数！", e);
                throw e;
            }catch (Exception e) { 
            	LOGGER.error("登录ftp服务器【"+ip+"】失败", e); 
                throw e;
            } 
        } 
    }
    
    /**
     * @Description 关闭ftp连接
     */
    public static void closeConnect(FTPClient ftpClient) {
        try {
            if (ftpClient != null) {
                ftpClient.logout();
                ftpClient.disconnect();
            }
        } catch (Exception e) {
        	LOGGER.error("ftp连接关闭失败！", e);
        }
    }
    
    
    /**
     * @Description 目录是否存在
     * @param path
     * @return boolean
     */
    public static boolean existDirectory(FTPClient ftpClient,String path) throws IOException {
        boolean flag = false;  
        FTPFile[] ftpFileArr = ftpClient.listFiles(path);  
        for (FTPFile ftpFile : ftpFileArr) {  
            if (ftpFile.isDirectory()) {  
                flag = true;  
                break;  
            }  
        }  
        return flag;  
    } 
    
    
    /**
     * 创建FTP文件夹目录
     * @param pathName
     */
    public static boolean createDirectory(FTPClient ftpClient,String pathName) throws IOException { 
    	boolean isSuccess=false;
    	try{
    		isSuccess=ftpClient.makeDirectory(pathName);
    	}catch(Exception e){
    		e.printStackTrace();
    	}
        return isSuccess;  
    }


}
