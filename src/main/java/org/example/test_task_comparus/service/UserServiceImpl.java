package org.example.test_task_comparus.service;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.example.test_task_comparus.util.ApplicationProps;
import org.example.test_task_comparus.dto.UserDTO;
import org.example.test_task_comparus.util.MappingProps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserServiceImpl implements UserService {
    ApplicationProps applicationProps;
    Map<String, JdbcTemplate> jdbcByTypeDateBase;

    @Autowired
    public UserServiceImpl(ApplicationProps applicationProps, Map<String, JdbcTemplate> jdbcByTypeDateBase) {
        this.applicationProps = applicationProps;
        this.jdbcByTypeDateBase = jdbcByTypeDateBase;
    }

    @Override
    public List<UserDTO> findAllWithFilter(String username, String name, String surname) {
        List<UserDTO> allUsers = new ArrayList<>();
        for (var dataSource : applicationProps.getDataSources()) {
            var jdbcTemplate = jdbcByTypeDateBase.get(dataSource.getName());
            var mapping = dataSource.getMapping();

            String request = "SELECT * FROM %s".formatted(dataSource.getTable());
            String buildRequest = buildRequestWithFilters(request,
                    generateFiltersMap(username, name, surname),
                    mapping.toMap());

            allUsers.addAll(jdbcTemplate.query(buildRequest, (resultSet, it) -> new UserDTO(
                    resultSet.getString(mapping.getId()),
                    resultSet.getString(mapping.getUsername()),
                    resultSet.getString(mapping.getName()),
                    resultSet.getString(mapping.getSurname()))));
        }
        return allUsers;
    }

    private Map<String, Object> generateFiltersMap(String username, String name, String surname) {
        Map<String, Object> filters = new HashMap<>();
        putIfHasText(filters, "username", username);
        putIfHasText(filters, "name",     name);
        putIfHasText(filters, "surname",  surname);
        return filters;
    }
    private void putIfHasText(Map<String, Object> m, String k, String v) {
        if (v != null && !v.isBlank()) m.put(k, v);
    }

    private String buildRequestWithFilters(String baseRequest,
                                           Map<String, Object> filtersMap,
                                           Map<String, String> mapping) {
        if (filtersMap == null || filtersMap.isEmpty()) {
            return baseRequest;
        }

        String whereClause = filtersMap
                .entrySet()
                .stream()
                .map(e -> {
                    String key = e.getKey();
                    String col = mapping.getOrDefault(key, key);
                    return col + " LIKE '%" + e.getValue() + "%'";
                })
                .collect(Collectors.joining(" AND "));

        return baseRequest + " WHERE " + whereClause;
    }
}
