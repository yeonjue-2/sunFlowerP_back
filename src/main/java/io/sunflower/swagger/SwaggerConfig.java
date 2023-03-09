package io.sunflower.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.HashSet;
import java.util.Set;

@Configuration
@EnableWebMvc
public class SwaggerConfig {

    private ApiInfo swaggerInfo() {
        return new ApiInfoBuilder().title("SunFlower API")
                .description("SunFlower API Docs").build();
    }

    @Bean
    public Docket swaggerApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .consumes(getConsumeContentTypes())
                .produces(getProduceContentTypes())
                .apiInfo(swaggerInfo()).select()
                .apis(RequestHandlerSelectors.basePackage("io.sunflower"))
                .paths(PathSelectors.any())
                .build()
                .useDefaultResponseMessages(false);
    }

    private Set<String> getConsumeContentTypes() {
        Set<String> consumes = new HashSet<>();
        consumes.add("application/json;charset=UTF-8");
        consumes.add("application/x-www-form-urlencoded");
        return consumes;
    }

    private Set<String> getProduceContentTypes() {
        Set<String> produces = new HashSet<>();
        produces.add("application/json;charset=UTF-8");
        return produces;
    }

    // driver-class-name: com.mysql.cj.jdbc.Driver
    // url: jdbc:mysql://localhost:3306/sunflower?serverTimezone=UTC&characterEncoding=UTF-8
    //    username: root
    //    password: 1234

//    driver-class-name: com.mysql.cj.jdbc.Driver
//    url: jdbc:mysql://sunflower-db.cvmzekcc6vfq.ap-northeast-2.rds.amazonaws.com:3306/sunflower_database?serverTimezone=UTC&characterEncoding=UTF-8
//    username: admin
//    password: sunflower

    // database: sql_server
}

