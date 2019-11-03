package br.com.ederson.shopping;

import static org.springframework.http.HttpMethod.DELETE;
import static org.springframework.http.HttpMethod.PUT;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.ederson.shopping.domain.User;
import br.com.ederson.shopping.repository.UserRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @MockBean
    private UserRepository userRepository;
    
    private User user;
    
    @Before
    public void setup() {
    	user = User.builder()
				.id(1L)
				.name("João")
				.email("joao@gmail.com")
				.build();
        BDDMockito.when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
        BDDMockito.when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
    }
    
	@Test
    public void createShouldPersistDataAndReturnStatusCode201Created() throws Exception {
		User user = User.builder()
				.id(99L)
				.name("Ederson")
				.email("eamoraes@gmail.com")
				.build();
        BDDMockito.when(userRepository.save(user)).thenReturn(user);
        ResponseEntity<String> response = restTemplate.postForEntity("/api/users/", user, String.class);
        Assertions.assertThat(response.getStatusCodeValue()).isEqualTo(201);
    }
    
    @Test
    public void createWhenNameIsNullShouldReturnStatusCode400BadRequest() throws Exception {
		User user = User.builder()
				.id(1L)
				.name(null)
				.email("eamoraes@gmail.com")
				.build();
        BDDMockito.when(userRepository.save(user)).thenReturn(user);
        ResponseEntity<String> response = restTemplate.postForEntity("/api/users/", user, String.class);
        Assertions.assertThat(response.getStatusCodeValue()).isEqualTo(400);
        Assertions.assertThat(response.getBody()).contains("errors","user.name.not.blank");
    }
    
	@Test
    public void createWhenEmailAlreadyExistsShouldReturnStatusCode400BadRequest() throws Exception {
		User user = User.builder()
				.id(99L)
				.name("João Silva")
				.email("joao@gmail.com")
				.build();
        BDDMockito.when(userRepository.save(user)).thenReturn(user);
        ResponseEntity<String> response = restTemplate.postForEntity("/api/users/", user, String.class);
        Assertions.assertThat(response.getStatusCodeValue()).isEqualTo(400);
        Assertions.assertThat(response.getBody()).contains("errors","user.already.exists");
    }
	
	@Test
    public void deleteShouldReturnStatusCode200Ok() throws Exception {
        BDDMockito.doNothing().when(userRepository).delete(user);
        ResponseEntity<String> exchange = restTemplate.exchange("/api/users/{id}", DELETE, null, String.class, 1L);
        Assertions.assertThat(exchange.getStatusCodeValue()).isEqualTo(200);
    }
	
	@Test
    public void updateShouldReturnStatusCode200Ok() throws Exception {
		HttpEntity<User> httpEntity = new HttpEntity<>(user);
        ResponseEntity<String> exchange = restTemplate.exchange("/api/users/{id}", PUT, httpEntity, String.class, 1L);
        Assertions.assertThat(exchange.getStatusCodeValue()).isEqualTo(200);
    }
	
	@Test
    public void findShouldReturnStatusCode200Ok() throws Exception {
        ResponseEntity<String> response = restTemplate.getForEntity("/api/users/{id}", String.class, 1L);
        Assertions.assertThat(response.getStatusCodeValue()).isEqualTo(200);
    }
}
