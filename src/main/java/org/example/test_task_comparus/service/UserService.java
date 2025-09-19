package org.example.test_task_comparus.service;

import org.example.test_task_comparus.dto.UserDTO;

import java.util.List;

public interface UserService {
    List<UserDTO> findAllWithFilter(String username, String name, String surname);

}
