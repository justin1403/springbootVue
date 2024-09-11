package tw.justin.springbootvue.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
@EnableOpenApi
public class SwaggerConfig {
    @Bean
    public Docket resApi(){
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("標準接口")
                .apiInfo(apiInfo("spring Boot 中使用 Swagger2 構建 RESTful APIs","1.0"))
                .useDefaultResponseMessages(true)
                .forCodeGeneration(false)
                .select()
                .apis(RequestHandlerSelectors.basePackage("tw.justin.springbootvue.controller"))
                .paths(PathSelectors.any())
                .build();
    }
    private ApiInfo apiInfo(String title, String version){
        return new ApiInfoBuilder()
                .title(title)
                .description("更多請關註: https://blog.csdn.net/xgnode")
                .termsOfServiceUrl("https://blog.csdn.net/xgnode")
                .contact(new Contact("xgnode","https://blog.csdn.net/xgnode","xiaqingweb@163.com"))
                .version(version)
                .build();
    }
}
