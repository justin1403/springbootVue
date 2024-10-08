package tw.justin.springbootvue.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {

    // 當前跨域請求最大有效時長，這裡默認一天 24 * 60 * 60
    private static final long MAX_AGE = 24 * 60 * 60;

    @Bean
    public CorsFilter corsFilter(){
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin("http://localhost:8080/"); // 1. 設置訪問源地址
        corsConfiguration.addAllowedHeader("*"); // 2. 設置訪問源請求頭
        corsConfiguration.addAllowedMethod("*"); // 3. 設置訪問源請求方法
        corsConfiguration.setMaxAge(MAX_AGE);
        source.registerCorsConfiguration("/**",corsConfiguration);  // 4. 對接口配置跨域設置
        return new CorsFilter(source);
    }

}
