package ss18.demouploadfile.config;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.io.IOException;


@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {"ss18.demouploadfile.controller"})
@PropertySource("classpath:upload.properties")
public class AppConfig implements WebMvcConfigurer, ApplicationContextAware {
    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Bean
    public ViewResolver viewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/views/");
        viewResolver.setSuffix(".jsp");
        return viewResolver;
    }

    // Khai báo gán link uploadPath qua @Value
    // Đây là một annotation của Spring Framework,
    // được sử dụng để tiêm giá trị từ các tài liệu cấu hình (configuration properties) vào trong mã nguồn.
    //${uploadPath} là một biểu thức tham chiếu đến giá trị của thuộc tính có tên "uploadPath" trong tài liệu cấu hình.
    // Giá trị của biểu thức này sẽ được tiêm vào biến uploadPath ở phần tiếp theo.

    @Value("${uploadPath}")
    private String uploadPath;

    //    private String cssPath = ;
        @Bean(name = "multipartResolver") // bỏ name ok
        public CommonsMultipartResolver getResolver() throws IOException {
            CommonsMultipartResolver resolver = new CommonsMultipartResolver();
            resolver.setMaxUploadSizePerFile(52428800);
            return resolver;
        }

    // cấu hình lại đường dẫn tương đối
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/image/**", "/css/**")
                .addResourceLocations("file:" + uploadPath, "classpath:/assets/css/");
    }

}
