package org.example.test_task_comparus;

import org.example.test_task_comparus.util.ApplicationProps;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import java.util.TimeZone;

@SpringBootApplication
@EnableConfigurationProperties(ApplicationProps.class)
public class TestTaskComparusApplication {

    public static void main(String[] args) {
        TimeZone.setDefault(TimeZone.getTimeZone("Europe/Kyiv"));
        SpringApplication.run(TestTaskComparusApplication.class, args);
    }

}
