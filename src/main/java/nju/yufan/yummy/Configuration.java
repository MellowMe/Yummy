package nju.yufan.yummy;


import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@org.springframework.context.annotation.Configuration
public class Configuration extends WebMvcConfigurationSupport {
	@Override
	protected void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/**").addResourceLocations("file:"+"C:\\Users\\yufan\\workspace\\yummy\\src\\main\\resources\\static\\");
		super.addResourceHandlers(registry);
	}
}
