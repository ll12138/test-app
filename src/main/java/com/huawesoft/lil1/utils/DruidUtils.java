package com.huawesoft.lil1.utils;

import com.alibaba.druid.filter.config.ConfigTools;

/*
 *@author：LL
 *@Date:2021/5/26
 *@Description druid 数据库加密工具类
 */
public class DruidUtils {
    public static void main(String[] args) throws Exception {
        // 需要加密的明文命名
        String password = "1q2w#E$R"; // 【注意：这里要改为你自己的密码】
        // 调用 druid 生成私钥、公钥、密文
        ConfigTools.main(new String[]{password});
    }
}
