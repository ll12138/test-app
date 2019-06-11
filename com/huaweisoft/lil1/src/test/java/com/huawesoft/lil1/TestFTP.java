package com.huawesoft.lil1;

import java.util.Date;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPConnectionClosedException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.huawesoft.lil1.utils.FTPUtils;

/**
 * @author lil1
 * @date 2019年5月20日 下午5:34:53
 * @Description 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class TestFTP {
	private static final Logger LOGGER = LoggerFactory.getLogger(TestFTP.class);
	@Test
	public  void testMkDir() {
		FTPClient ftpClient = new FTPClient();
		try {
			new FTPUtils().connectToServer(ftpClient,"192.168.0.55",21,"test","1q2w#E$R");
			if(!FTPUtils.existDirectory(ftpClient,"/20190531")){
				LOGGER.info("进入testMkDir方法,{}","无目录");
				FTPUtils.createDirectory(ftpClient,"/20190531");
			};
			LOGGER.info("进入testMkDir方法,{}","关闭");
			FTPUtils.closeConnect(ftpClient);
		} catch (FTPConnectionClosedException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
