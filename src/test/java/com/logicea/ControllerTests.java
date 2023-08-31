package com.logicea;

import javax.sql.DataSource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.AfterEach;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.utility.DockerImageName;

import com.logicea.security.domain.Role;
import com.logicea.security.domain.RoleRepository;
import com.logicea.security.domain.RoleService;
import com.logicea.security.domain.User;
import com.logicea.security.domain.UserService;
import com.zaxxer.hikari.HikariDataSource;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
public class ControllerTests {

	@Autowired
	private DataSource dataSource;

	@Autowired private UserService userService;
	@Autowired private RoleRepository roleRepository;

	@Container
	public static MySQLContainer<?> mySQLContainer = new MySQLContainer<>(DockerImageName.parse("mysql:8.0.26"))
	.withDatabaseName("testdb")
	.withUsername("test")
	.withPassword("test")
	.waitingFor(Wait.forListeningPort())
	.withEnv("MYSQL_ROOT_HOST", "%");


	@LocalServerPort
	private int port;


	@Autowired
	private TestRestTemplate restTemplate;


	@DynamicPropertySource
	static void registerPgProperties(DynamicPropertyRegistry registry) {
		registry.add("spring.datasource.url", mySQLContainer::getJdbcUrl);
		registry.add("spring.datasource.password", mySQLContainer::getPassword);
		registry.add("spring.datasource.username", mySQLContainer::getUsername);
	}
	@AfterEach
	void tearDown() {
		if (dataSource instanceof HikariDataSource) {
			((HikariDataSource) dataSource).close();
		}
	}


	@Test
	public void testCreateUser() {
		User user = new User();
		user.setEmail("testuser@gmail.com");
		user.setPassword("testpass");

		Set<Role> roles = new HashSet();
		roles.add(createRole("ROLE_ADMIN"));
		roles.add(createRole("ROLE_USER"));
		user.setRoles(roles);

		User response = userService.createUser(user);
		assertThat(response).isNotNull();
		assertThat(response.getId()).isNotNull();
	}


	private Role createRole(String role) {

		Role rl  = new Role();
		rl.setDatecreated(LocalDateTime.now());
		rl.setName(role);
		rl = roleRepository.save(rl);
		return rl;
	}

}
