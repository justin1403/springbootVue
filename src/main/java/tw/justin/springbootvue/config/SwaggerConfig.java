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
@EnableOpenApi // localhost:9090/swagger-ui/index.html
public class SwaggerConfig {

    /**
     * 創建 API 應用
     * apiInfo() 增加 API 相關信息
     * 通過 select() 函數返回一個 ApiSelectorBuilder 實例，用來控制哪些接口暴露給 Swagger 來展現，
     * 本例採用指定掃描的包路徑來定義指定要建立 API 的目錄
     *
     * @return
     */


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


    /**
     * 創建該 API 的基本信息（這些基本信息會展現在文檔頁面中）
     * 訪問地址： http://ip:port/swagger-ui.html
     */

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
