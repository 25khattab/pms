package org.trainopia.pms;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.trainopia.pms.features.project.Project;
import org.trainopia.pms.features.project.ProjectServiceImpl;
import org.trainopia.pms.features.project.projectDetails.ProjectDetails;
import org.trainopia.pms.features.project.projectExpense.ProjectExpense;


import java.util.List;

@SpringBootApplication
public class PmsApplication {

    public static void main(String[] args) {
        SpringApplication.run(PmsApplication.class, args);
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests().anyRequest().permitAll();
        return http.build();
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
