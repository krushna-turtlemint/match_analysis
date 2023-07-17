package io.mk.match_analysis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan
@SpringBootApplication
public class MatchAnalysisApplication {

	public static void main(String[] args) {
		SpringApplication.run(MatchAnalysisApplication.class, args);

	}
}
