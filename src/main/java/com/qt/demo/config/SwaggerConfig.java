package com.qt.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName:
 * @Description:
 * @author: QianTao
 * @date: 2021/08/25 17:21
 * @version: V1.0
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("API接口文档")
                .description("用户权限信息")
                .version("1.0.0")
                .build();
    }

    @Bean
    public Docket createRestApi() {

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.qt.demo.controller")) //这里写的是API接口所在的包位置
                .paths(PathSelectors.any())
                .build();
//                .securitySchemes(securitySchemes())//这2个配置会在swagger上添加 authorize模块
//                .securityContexts(securityContexts());
        //   @ApiImplicitParams({ @ApiImplicitParam(paramType = "header", dataType = "String", name = "access_token", value = "请求token", required = true) })
        //或者使用注解在方法上进行 指定方法添加参数
    }

    /**
     * 添加自定义请求数据
     * @return
     */
    private List<SecurityContext> securityContexts() {
        List<SecurityContext> securityContextList = new ArrayList<>();
//        List<SecurityReference> securityReferenceList = new ArrayList<>();
//        securityReferenceList.add(new SecurityReference("access_token", scopes()));
//        securityContextList.add(SecurityContext
//                .builder()
//                .securityReferences(securityReferenceList)
//                .forPaths(PathSelectors.any())
//                .build()
//        );
        return securityContextList;
    }

//    private AuthorizationScope[] scopes() {
//        return new AuthorizationScope[]{new AuthorizationScope("global", "accessAnything")};
//    }
//
//    private List<SecurityScheme> securitySchemes() {
//        List<SecurityScheme> apiKeyList = new ArrayList<>();
//        apiKeyList.add(new ApiKey(HeadConst.ACCESS_TOKEN, HeadConst.ACCESS_TOKEN, "header"));
//        return apiKeyList;
//    }

}