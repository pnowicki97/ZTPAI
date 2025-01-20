package nowicki.piotr.spring_boot_docker.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Autowired
    private JwtInterceptor jwtInterceptor;
//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**")
//                .allowedOrigins("http://localhost:8080")  // or your frontend URL
//                .allowedMethods("GET", "POST", "PUT", "DELETE","OPTIONS")
//                .allowedHeaders("*")
//                .allowCredentials(true);  // Make sure to allow credentials (cookies, headers, etc.)
//    }
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Ensure that the static files are mapped correctly
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/") // Correct location of static files
                .setCacheControl(CacheControl.noCache());
    }
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor)
                .addPathPatterns("/**") // Apply to all endpoints
                .excludePathPatterns("/auth/**"); // Exclude login or public endpoints
    }
}