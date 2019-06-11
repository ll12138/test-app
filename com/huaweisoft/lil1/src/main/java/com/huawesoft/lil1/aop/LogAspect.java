package com.huawesoft.lil1.aop;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.huawesoft.lil1.annotation.ControllerLog;
import com.huawesoft.lil1.common.JsonResult;


/**
 * @author lil1
 * @date 2018年9月14日 上午11:38:06
 * @Description TODO
 */
@Aspect
@Component
@Order(1)
public class LogAspect {

	private static final Logger LOGGER = LoggerFactory.getLogger(LogAspect.class);

	@Autowired
	//private CommonLogService commonLogService;

	// 定义controller切入点拦截规则，拦截SystemControllerLog注解的方法
	@Pointcut("@annotation(com.huaweisoft.common.annotation.ControllerLog)")
	public void controllerAspect() {
		
	}

	@Around("controllerAspect() && @annotation(log)")
	public JsonResult recordLog(ProceedingJoinPoint point, ControllerLog log) throws Throwable {
		// 获取session中的用户
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		StringBuffer url = request.getRequestURL();
		String module = log.module();
		String actionType = log.actionType();
		String remark = log.remark();
		// 返回值
		JsonResult res = (JsonResult) point.proceed();
		LOGGER.info("@Around recordLog");
		//commonLogService.saveLog(CurrentUser.getCurrentUserCode(), url.toString(), module, actionType, remark);
		System.out.println(url.toString()+"-------"+ module+"-------"+ actionType+remark);
		return res;
	}

	@AfterThrowing(pointcut = "controllerAspect() && @annotation(log)", throwing = "e")
	public void doAfterThrowing(JoinPoint joinPoint, Throwable e, ControllerLog log) {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		StringBuffer url = request.getRequestURL();
		String module = log.module();
		String actionType = log.actionType();
		String remark = log.remark();
		remark += e.getClass().getName() + ":" + e.getMessage();
		//commonLogService.saveLog(CurrentUser.getCurrentUserCode(), url.toString(), module, actionType, remark);
		System.out.println(url.toString()+"-------"+ module+"-------"+ actionType+remark);
	}

}
