package com.huawesoft.lil1.annotation;

import java.lang.annotation.*;


/**
 * @author lil1
 * @date 2018年9月14日上午10:56:57
 * @Description 日志注解
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})               //定义了注解声明在哪些元素之前
@Documented
public @interface ControllerLog {
	/**
	 * 该方法涉及的模块代码,默认00-01(用户)
	 * 模块代发参考公共使用文档
	 *  {@link SysLogConstant.ActionModule}
     */
	String module() default "00-01";    
    /**
     * 该方法的操作类型默认0(其他)
     * 0-其他（默认）
   	 * 1-登入 2-登出 3-增加 4-删除 5-修改 6-查询
   	 * {@link SysLogConstant.ActionType}
     */
	String actionType() default "0";        
    /**
     * 对该方法操作类型的补充说明
     */
    String remark() default "";
}
