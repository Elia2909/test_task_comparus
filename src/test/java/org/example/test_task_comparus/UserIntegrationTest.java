package org.example.test_task_comparus;

import org.example.test_task_comparus.dto.UserDTO;
import org.example.test_task_comparus.service.UserService;
import org.example.test_task_comparus.util.ApplicationProps;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.testcontainers.containers.MariaDBContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import javax.sql.DataSource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@Testcontainers
@SpringBootTest
public class UserIntegrationTest {
    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16")
            .withDatabaseName("comparus")
            .withUsername("postgres")
            .withPassword("1234qwer");

    @Container
    static MariaDBContainer<?> mariadb = new MariaDBContainer<>("mariadb:11")
            .withDatabaseName("comparus")
            .withUsername("admin")
            .withPassword("admin123");

    @Autowired
    UserService userService;

    @BeforeEach
    void setUp() {
        TimeZone.setDefault(TimeZone.getTimeZone("Europe/Kyiv"));
    }

    @Test
    void shouldReturnAllUsers() {
        List<UserDTO> all = userService.findAllWithFilter(null, null, null);
        assertEquals(4, all.size());
    }

    @Test
    void shouldFilterByUsername() {
        List<UserDTO> users = userService.findAllWithFilter("user-1", null, null);
        assertEquals(1, users.size());
        assertEquals("user-1", users.get(0).username());
    }

    @Test
    void shouldFilterBySurname() {
        List<UserDTO> users = userService.findAllWithFilter(null, null, "Tarasenko");
        assertTrue(users.stream().anyMatch(u -> u.surname().equals("Tarasenko")));
    }
}
