package org.example.test_task_comparus.util;

import lombok.Data;

@Data
public class DataSourceProps {
    private String name;
    private String strategy;
    private String driverClassName;
    private String url;
    private String username;
    private String password;
    private String table;
    private MappingProps mapping;
}