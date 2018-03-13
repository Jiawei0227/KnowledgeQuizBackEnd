package nju.wjw;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by Jerry Wang on 12/03/2018.
 */
@SpringBootApplication
@EnableAutoConfiguration
public class KnowledgeQuizApp {
    public static void main(String[] args) throws Exception {
        SpringApplication.run(KnowledgeQuizApp.class, args);
    }
}
