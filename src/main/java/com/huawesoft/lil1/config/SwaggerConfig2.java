/*
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
import springfox.documentation.swagger2.annotations.EnableSwagger2;

*/
/*
 *@author：LL
 *@Date:2021/5/26
 *@Description  swagger2.9.2配置
 * 依赖2个jar包、启动类注解@EnableSwagger2
 * http://localhost:8089/swagger-ui.html
 *//*

@Configuration
@EnableSwagger2
public class SwaggerConfig2 {
    @Bean
    public Docket api(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .pathMapping("/")
                .select()
                .paths(PathSelectors.regex("/.*"))
                .apis(RequestHandlerSelectors.basePackage("com.symedsoft.selfservice.controller"))
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("接口文档")
                .contact(new Contact("self-service","","symedsoft.com"))
                .description("swagger生成的接口文档")
                .version("1.0")
                .build();

    }
}
*/
