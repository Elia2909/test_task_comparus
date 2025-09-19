package org.example.test_task_comparus.config;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.example.test_task_comparus.util.ApplicationProps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Configuration
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DynamicDataSourceConfig {
    ApplicationProps applicationProps;
    InitDataConfig initDataConfig;

    @Autowired
    public DynamicDataSourceConfig(ApplicationProps applicationProps,
                                   InitDataConfig initDataConfig) {
        this.applicationProps = applicationProps;
        this.initDataConfig = initDataConfig;
    }

    @Bean
    public Map<String, JdbcTemplate> jdbcTemplates() throws IOException {
        Map<String, JdbcTemplate> jdbcTemplateMap = new HashMap<>();
        for (var dataSourceProps : applicationProps.getDataSources()) {
            DataSource dataSource = DataSourceBuilder.create()
                    .driverClassName(dataSourceProps.getDriverClassName())
                    .url(dataSourceProps.getUrl())
                    .username(dataSourceProps.getUsername())
                    .password(dataSourceProps.getPassword())
                    .build();
            initDataConfig.initData(dataSource, dataSourceProps.getStrategy());
            jdbcTemplateMap.put(dataSourceProps.getName(), new JdbcTemplate(dataSource));
        }
        return jdbcTemplateMap;
    }
}
