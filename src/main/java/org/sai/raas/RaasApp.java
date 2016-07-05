package org.sai.raas;

import com.google.common.base.Predicates;
import org.apache.commons.io.IOUtils;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Application main.
 *
 * @author saikris
 */
@SpringBootApplication
@Configuration
@EnableConfigurationProperties
@EnableAsync
@ComponentScan("org.sai.raas")
@EnableSwagger2
@EnableCaching
public class RaasApp extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(final SpringApplicationBuilder application) {
        return application.sources(RaasApp.class);
    }

    /**
     * Main method.
     *
     * @param args
     * @throws Exception
     */
    public static void main(final String[] args) throws Exception {
        SpringApplicationBuilder application = new SpringApplicationBuilder();
        application //
                .addCommandLineProperties(true) //
                .sources(RaasApp.class) //
                .showBanner(true)
                .main(RaasApp.class) //
                .registerShutdownHook(true)
                .run(args);
    }

    /**
     * Swagger 2 docket bean configuration.
     *
     * @return swagger 2 Docket.
     */
    @Bean
    public Docket rassApi() throws Exception {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("security-app")
                .apiInfo(apiInfo())
                .select()
                .paths(Predicates.not(PathSelectors.regex("/error"))) // Exclude Spring error controllers
                .build();
    }

    private ApiInfo apiInfo() throws Exception {
        return new ApiInfoBuilder()
                .title("Reports As a Service (RaaS) API")
                .description(IOUtils.toString(RaasApp.class.getClassLoader().getResourceAsStream("description.html")))
                .contact("Saiprasad.Krishnamurthy@gmail.com, saiprkri@cisco.com")
                .license("Apache V 2.0")
                .licenseUrl("https://github.com/SaiprasadKrishnamurthy")
                .version("1.0")
                .termsOfServiceUrl("https://github.com/SaiprasadKrishnamurthy")
                .build();
    }
}