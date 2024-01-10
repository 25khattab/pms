package org.trainopia.pms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class PmsApplication {

    public static void main(String[] args) {
        SpringApplication.run(PmsApplication.class, args);
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    //    @Bean
    //    public CommandLineRunner commandLineRunner(ProjectServiceImpl projectService) {
    //        return args -> {
    //            Project project = new Project("title", 10, 20, 20, "aaa");
    //            ProjectDetails pd = new ProjectDetails("aaaaaa");
    //            ProjectExpense pe = new ProjectExpense("aa", 20);
    //            project.setProjectDetails(pd);
    //            project.addProjectExpense(pe);
    //            projectService.save(project);
    //        };
    //
    //    }
}
