package com.huawesoft.lil1.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.huawesoft.lil1.annotation.ControllerLog;
import com.huawesoft.lil1.common.JsonResult;

/**
 * @author lil1
 * @date 2019年6月12日 上午9:54:36
 * @Description 
 */
@RestController
public class TestController {
	
	 @ControllerLog(actionType="1")
	 @GetMapping(value = "/")
	    public JsonResult idnex() {

	        return JsonResult.success("test-app");
	    }
}
