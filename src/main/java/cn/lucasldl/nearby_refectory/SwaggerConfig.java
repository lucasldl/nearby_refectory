package cn.lucasldl.nearby_refectory;

import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket swaggerSpringMvcPlugin(){
        ApiInfo apiInfo = new ApiInfo(
                "Nearby Refectory APIs 附近餐厅查看接口",
                "基于springboot+mongodb开发",
                "1.0.0",
                "null",
                "lucas",
                "lucas",
                "null");
        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo)
                .select()
                .paths(PathSelectors.any())
                .build();
        return docket;
    }
}
