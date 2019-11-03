package br.com.ederson.shopping;

import static org.springframework.http.HttpMethod.DELETE;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
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
import br.com.ederson.shopping.domain.ShoppingCart;
import br.com.ederson.shopping.domain.ShoppingCartItem;
import br.com.ederson.shopping.domain.User;
import br.com.ederson.shopping.repository.ItemRepository;
import br.com.ederson.shopping.repository.ShoppingCartItemRepository;
import br.com.ederson.shopping.repository.ShoppingCartRepository;
import br.com.ederson.shopping.repository.UserRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ShoppingCartControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @MockBean
    private ShoppingCartRepository shoppingCartRepository;
    
    @MockBean
    private ShoppingCartItemRepository shoppingCartItemRepository;
    
    @MockBean
    private ItemRepository itemRepository;
    
    @MockBean
    private UserRepository userRepository;
    
    private ShoppingCart shoppingCart;
    
    private Item item;
    
    private User user;
    
    private ShoppingCartItem shoppingCartItem = new ShoppingCartItem();
    
    @Before
    public void setup() {
    	user = User.builder()
				.id(1L)
				.name("João")
				.email("joao@gmail.com")
				.build();
    	shoppingCart = ShoppingCart.builder().id(1L).user(user).build();
    	
    	item = Item.builder()
				.id(1L)
				.name("Mouse")
				.value(BigDecimal.TEN)
				.build();
    	
		shoppingCartItem = ShoppingCartItem.builder().id(1L).total(new BigDecimal("100")).quantity(10L).item(item)
				.build();
    	
    	shoppingCart.addShoppingCartItem(shoppingCartItem);
    	BDDMockito.when(shoppingCartRepository.findByUserId(user.getId())).thenReturn(Optional.of(shoppingCart));
    }
    
	@Test
    public void addAnItemInAnExistentShoppingCartShouldPersistDataAndReturnStatusCode200Ok() throws Exception {
		Item itemIdTwo = createItemIdTwo();   
		
    	shoppingCartItem.setItem(itemIdTwo);
    	shoppingCartItem.setQuantity(5L);
    	BDDMockito.when(itemRepository.findById(itemIdTwo.getId())).thenReturn(Optional.of(itemIdTwo));
        ResponseEntity<String> response = restTemplate.postForEntity("/api/shoppingCart/{userId}", shoppingCartItem, String.class, 1L);
        Assertions.assertThat(response.getStatusCodeValue()).isEqualTo(200);
    }

	private Item createItemIdTwo() {
		return Item.builder()
				.id(2L)
				.name("Keyboard")
				.value(BigDecimal.TEN)
				.build();
	}
	
	@Test
    public void addAnItemInAnNonexistentShoppingCartShouldPersistDataAndReturnStatusCode200Ok() throws Exception {
    	Item itemIdTwo = createItemIdTwo();
    	
    	shoppingCartItem.setItem(itemIdTwo);
    	shoppingCartItem.setQuantity(5L);
    	
		BDDMockito.when(userRepository.findById(2L))
				.thenReturn(Optional.of(User.builder().id(2L).name("Ederson").email("eamoraes@gmail.com").build()));
		BDDMockito.when(itemRepository.findById(itemIdTwo.getId())).thenReturn(Optional.of(itemIdTwo));
        ResponseEntity<String> response = restTemplate.postForEntity("/api/shoppingCart/{userId}", shoppingCartItem, String.class, 2L);
        Assertions.assertThat(response.getStatusCodeValue()).isEqualTo(200);
    }
    
	@Test
    public void removeAnItemInAnExistentShoppingCartShouldReturnStatusCode200Ok() throws Exception {
		BDDMockito.when(shoppingCartItemRepository.findByShoppingCartIdAndItemId(shoppingCart.getId(), item.getId()))
				.thenReturn(Optional.of(shoppingCartItem));

		HttpEntity<Item> httpEntity = new HttpEntity<>(item);
    	ResponseEntity<String> exchange = restTemplate.exchange("/api/shoppingCart/{userId}", DELETE, httpEntity, String.class, 1L);
    	Assertions.assertThat(exchange.getStatusCodeValue()).isEqualTo(200);
    }
	
	@Test
    public void checkoutShouldReturnStatusCode200Ok() throws Exception {
		List<ShoppingCartItem> list = new ArrayList<>();
		list.add(shoppingCartItem);
		
		BDDMockito.when(shoppingCartItemRepository.findByShoppingCartIdOrderByItemNameAscTotalAsc(shoppingCart.getId())).thenReturn(list);
		
        ResponseEntity<String> response = restTemplate.getForEntity("/api/shoppingCart/checkout/{userId}", String.class, 1L);
        Assertions.assertThat(response.getStatusCodeValue()).isEqualTo(200);
    }
	
	@Test
    public void findShouldReturnStatusCode200Ok() throws Exception {
        ResponseEntity<String> response = restTemplate.getForEntity("/api/shoppingCart/{userId}", String.class, 1L);
        Assertions.assertThat(response.getStatusCodeValue()).isEqualTo(200);
    }
}
