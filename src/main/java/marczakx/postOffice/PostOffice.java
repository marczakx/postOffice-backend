package marczakx.postOffice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@SpringBootApplication
public class PostOffice {

	public static void main(String[] args) {
		SpringApplication.run(PostOffice.class, args);
	}
	
	@Bean
	public Docket get() {
		return new Docket(DocumentationType.SWAGGER_2).select().paths(PathSelectors.ant("/**"))
				.apis(RequestHandlerSelectors.basePackage("marczakx.postOffice.web")).build();
	}

}

