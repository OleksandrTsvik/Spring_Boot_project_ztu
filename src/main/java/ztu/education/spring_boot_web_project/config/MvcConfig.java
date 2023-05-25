package ztu.education.spring_boot_web_project.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
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

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // картинки потрібно зберігати не в каталозі resources, бо він завантажується лише при старті проекта
        registry
                .addResourceHandler("/upload/**")
                .addResourceLocations("file:///" + System.getProperty("user.dir") + "/src/main/upload/");
    }
}
