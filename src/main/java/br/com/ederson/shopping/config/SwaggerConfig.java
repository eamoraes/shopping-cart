package br.com.ederson.shopping.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

	@Bean
	public Docket docket() {
		return new Docket(DocumentationType.SWAGGER_2)
	            .useDefaultResponseMessages(false)
				.select()
				.apis(RequestHandlerSelectors.basePackage("br.com.ederson.shopping"))
				.paths(PathSelectors.any())
				.build()
				.apiInfo(apiInfo());
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
				.title("Shopping Cart API")
				.description("API REST para cadastro de carrinho de compras")
				.version("1.0")
				.contact(new Contact("Ederson Abreu de Moraes", "https://github.com/eamoraes", "eamoraes@gmail.com"))
				.build();
	}
	
}