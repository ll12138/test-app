package com.huawesoft.lil1.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * @author lil1
 * @date 2019年6月12日 上午10:38:29
 * @Description
 */
@Controller
public class TestController2 {

	@RequestMapping(value = "/login")
	public String idnex() {

		return "login";
	}
}
