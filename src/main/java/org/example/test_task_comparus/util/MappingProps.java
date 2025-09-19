package org.example.test_task_comparus.util;

import lombok.Data;

import java.util.Map;

@Data
public class MappingProps {
    private String id;
    private String username;
    private String name;
    private String surname;

    public Map<String, String> toMap() {
        return Map.of(
                "id", id,
                "username", username,
                "name", name,
                "surname", surname
        );
    }
}