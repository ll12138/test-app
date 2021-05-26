package com.huawesoft.lil1.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/*
 *@author：LL
 *@Date:2021/5/26
 *@Description swagger3.0.0配置
 * 访问地址：http://localhost:8089/swagger-ui/
 */
@Configuration
public class SwaggerConfig {
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.OAS_30) // v2 不同
                .apiInfo(apiInfo())
                .pathMapping("/")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.huawesoft.lil1.controller")) // 设置扫描路径
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("接口文档")
                .contact(new Contact("self-service","","LL.com"))
                .description("swagger生成的接口文档")
                .version("1.0")
                .build();

    }
}
