package cn.anytec.welcome.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
@Configuration
public class MVCConfiguration extends WebMvcConfigurerAdapter {

    //请求直接映射页面
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        //registry.addViewController("/").setViewName("index");
    }

    //静态资源映射
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //相对路径
      /*  registry.addResourceHandler("/static/**").addResourceLocations("classpath:/source/");
        //绝对路径
        registry.addResourceHandler("/static/**").addResourceLocations("file:/source/");
        super.addResourceHandlers(registry);*/
    }
}
