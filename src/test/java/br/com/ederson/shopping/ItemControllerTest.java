package br.com.ederson.shopping;

import static org.springframework.http.HttpMethod.DELETE;
import static org.springframework.http.HttpMethod.PUT;

import java.math.BigDecimal;
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

import br.com.ederson.shopping.domain.Item;
import br.com.ederson.shopping.repository.ItemRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ItemControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @MockBean
    private ItemRepository itemRepository;
    
    private Item item;
    
    @Before
    public void setup() {
    	item = Item.builder()
				.id(1L)
				.name("Mouse")
				.value(BigDecimal.TEN)
				.build();
        BDDMockito.when(itemRepository.findById(item.getId())).thenReturn(Optional.of(item));
    }
    
	@Test
    public void createShouldPersistDataAndReturnStatusCode201Created() throws Exception {
    	Item item = Item.builder()
				.id(99L)
				.name("Mouse")
				.value(BigDecimal.TEN)
				.build();
        BDDMockito.when(itemRepository.save(item)).thenReturn(item);
        ResponseEntity<String> response = restTemplate.postForEntity("/api/items/", item, String.class);
        Assertions.assertThat(response.getStatusCodeValue()).isEqualTo(201);
    }
    
    @Test
    public void createWhenValueIsLessOrEqualZeroShouldReturnStatusCode400BadRequest() throws Exception {
    	Item item = Item.builder()
				.id(99L)
				.name("Mouse")
				.value(BigDecimal.ZERO)
				.build();
        BDDMockito.when(itemRepository.save(item)).thenReturn(item);
        ResponseEntity<String> response = restTemplate.postForEntity("/api/items/", item, String.class);
        Assertions.assertThat(response.getStatusCodeValue()).isEqualTo(400);
        Assertions.assertThat(response.getBody()).contains("errors","item.value.greater.zero");
    }

	@Test
    public void deleteShouldReturnStatusCode200Ok() throws Exception {
        BDDMockito.doNothing().when(itemRepository).delete(item);
        ResponseEntity<String> exchange = restTemplate.exchange("/api/items/{id}", DELETE, null, String.class, 1L);
        Assertions.assertThat(exchange.getStatusCodeValue()).isEqualTo(200);
    }
	
	@Test
    public void updateShouldReturnStatusCode200Ok() throws Exception {
		HttpEntity<Item> httpEntity = new HttpEntity<>(item);
        ResponseEntity<String> exchange = restTemplate.exchange("/api/items/{id}", PUT, httpEntity, String.class, 1L);
        Assertions.assertThat(exchange.getStatusCodeValue()).isEqualTo(200);
    }
	
	@Test
    public void findShouldReturnStatusCode200Ok() throws Exception {
        ResponseEntity<String> response = restTemplate.getForEntity("/api/items/{id}", String.class, 1L);
        Assertions.assertThat(response.getStatusCodeValue()).isEqualTo(200);
    }
}
