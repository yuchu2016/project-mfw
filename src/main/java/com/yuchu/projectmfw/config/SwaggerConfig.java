package com.yuchu.projectmfw.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: luqinglin
 * Date: 2017-12-28
 * Time: 16:07
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    /**
     * swagger配置
     * @return
     */
    @Bean
    public Docket createRestApi() {

        /**
         * swagger需要扫描的包
         */
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.yuchu.projectmfw.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    /**
     * 界面信息
     * @return
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("马蜂窝订单 保存Api")
                .description("马蜂窝订单 保存Api")
//                .termsOfServiceUrl("")
                .contact("yuchu")
                .version("0.1.0")
                .build();
    }


}
