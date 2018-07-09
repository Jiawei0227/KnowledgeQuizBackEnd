package nju.wjw;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

/**
 * Created by Jerry Wang on 12/03/2018.
 */
@SpringBootApplication
@EnableAutoConfiguration
public class KnowledgeQuizApp extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(KnowledgeQuizApp.class);
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(KnowledgeQuizApp.class, args);
    }
}
