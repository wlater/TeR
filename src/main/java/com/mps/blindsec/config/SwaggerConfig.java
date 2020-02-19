package com.mps.blindsec.config;

import java.util.ArrayList;

import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig{

    public Docket swagConfig(){
        return new Docket(DocumentationType.SWAGGER_2).select()
                          .apis(RequestHandlerSelectors.basePackage("com.mps.blindsec.controller"))
                          .build()
                          .apiInfo(apiInfo());
        
    }

    private ApiInfo apiInfo(){
        return new ApiInfo(
                    "BlindSec",
                    "A user CRUD microservice with a public-key cryptography architecture", 
                    "0.0.1", 
                    "termsOfServiceUrl", 
                    new Contact("Erick Dias", "https://www.linkedin.com/in/erick-dias-b86112104/", "erickdias@cc.ci.ufpb.br"), 
                    "license", 
                    "icenseUrl", 
                    new ArrayList<>()
                );
    }
}