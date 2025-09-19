package org.example.test_task_comparus.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;

@Configuration
public class InitDataConfig {

    public void initData(DataSource dataSource, String dataBaseStrategy) throws IOException {
        var resolver = new PathMatchingResourcePatternResolver();
        Resource[] resource = resolver.getResources("classpath:db/" + dataBaseStrategy + "/*.sql");
        Arrays.sort(resource, Comparator.comparing(Resource::getFilename));
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator(
                resource
        );
        DataSourceInitializer dataSourceInitializer = new DataSourceInitializer();
        dataSourceInitializer.setDatabasePopulator(populator);
        dataSourceInitializer.setDataSource(dataSource);
        populator.execute(dataSource);
    }
}
