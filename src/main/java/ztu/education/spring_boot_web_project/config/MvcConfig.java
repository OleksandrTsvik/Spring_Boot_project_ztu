package ztu.education.spring_boot_web_project.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/user/login").setViewName("formUserLogin");
        registry.addViewController("/admin/login").setViewName("formAdminLogin");
        registry.addViewController("/access-denied").setViewName("error/403");
    }
}
