package kr.co.ymtech;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@EntityScan(
		basePackageClasses = {Jsr310JpaConverters.class},
		basePackages = {"kr.co.ymtech.entity"})
@SpringBootApplication
public class GraphQlServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(GraphQlServerApplication.class, args);
	}

}
