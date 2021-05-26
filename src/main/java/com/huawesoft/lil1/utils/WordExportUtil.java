package com.huawesoft.lil1.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.URLEncoder;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import freemarker.template.Configuration;
import freemarker.template.Template;

/**
 * @author lil1
 * @date 2018年10月19日 上午10:09:12
 * @Description
 */
public class WordExportUtil {

	/**
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @param response
	 *            HttpServletResponse
	 * @param docName
	 *            生成的doc文件名
	 * @param url
	 *            存放freemark模板的目录
	 * @param ftlName
	 *            freemark模板文件名
	 * @param beanParams
	 *            入参数据: Map<String, Object>类型
	 */
	public static void writeResponse(HttpServletRequest request, HttpServletResponse response, String path,
			String docName, String ftlName, Map<String, Object> beanParams) {
		Configuration config = new Configuration();
		// String path =
		// request.getSession().getServletContext().getRealPath("/"+url);
		InputStream is = null;
		File previewFile = null;
		try {
			config.setDirectoryForTemplateLoading(new File(path));
			config.setDefaultEncoding("UTF-8");
			config.setClassicCompatible(true);
			Template template = config.getTemplate(ftlName, "UTF-8");
			String filePath = path + "/" + docName;
			FileOutputStream fos = new FileOutputStream(filePath);
			Writer out = new OutputStreamWriter(fos,"utf-8");
			template.process(beanParams, out);
			out.flush();
			out.close();

			previewFile = new File(filePath);
			is = new FileInputStream(previewFile);
			response.reset();
			response.setContentType("application/msword;charset=UTF-8");
			docName = URLEncoder.encode(docName, "UTF-8");
			response.addHeader("Content-Disposition", "attachment;filename=" + docName);
			byte[] b = new byte[1024];
			int len;
			while ((len = is.read(b)) > 0) {
				response.getOutputStream().write(b, 0, len);
			}
			response.getOutputStream().flush();
			response.getOutputStream().close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (previewFile != null) {
				previewFile.delete();
			}
		}
	}

}
