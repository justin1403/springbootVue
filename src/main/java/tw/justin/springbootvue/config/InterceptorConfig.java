package tw.justin.springbootvue.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import tw.justin.springbootvue.config.interceptor.JwtInterceptor;

@Configuration  // 配置類都要添加
public class InterceptorConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor())
                .addPathPatterns("/**")         // 攔截所有請求，通過判斷 token是否合法來，決定是否需要登錄
                .excludePathPatterns("/user/login","/hone","/user/register","/**/export","/**/import","/file/**",
                        "/swagger-resources/**","/webjars/**","/v2/**","swagger-ui.html/**","/api","/api-docs","/api-docs/**")    // 排除不驗證

                .excludePathPatterns("/**/*.html","/**/*.js","/**/*.css","/**/*.woff","/**/*.ttf" );     // 放行靜態文件
    }

    @Bean
    public JwtInterceptor jwtInterceptor(){
        return new JwtInterceptor();
    }

}
