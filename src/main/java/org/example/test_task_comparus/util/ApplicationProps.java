package org.example.test_task_comparus.util;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;


@Data
@Configuration
@ConfigurationProperties(prefix = "app")
public class ApplicationProps {
    List<DataSourceProps> dataSources;
}
