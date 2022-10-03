package com.isoler.studyim;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.ApplicationPidFileWriter;

@SpringBootApplication
@MapperScan(basePackages = "com.isoler.studyim.business.**.mapper")
public class StudyImApplication {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(StudyImApplication.class);
        app.addListeners(new ApplicationPidFileWriter());
        app.run(args);
    }

}
