package info.eecc.intellipack.config;

import lombok.NonNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Developer: Sabrina Meier
 * Company: EECC
 * Created: 21.06.2021
 */
@Configuration
public class SwaggerConfig {

    /**
     *
     * @return
     */
    @Bean
    WebMvcConfigurer openapiConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addResourceHandlers(@NonNull ResourceHandlerRegistry registry) {
                registry.addResourceHandler("/swagger/**")
                        .addResourceLocations("classpath:/swagger/");
            }
        };
    }
}
