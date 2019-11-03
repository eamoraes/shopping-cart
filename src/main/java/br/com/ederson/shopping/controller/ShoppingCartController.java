package br.com.ederson.shopping.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.ederson.shopping.domain.Item;
import br.com.ederson.shopping.domain.ShoppingCart;
import br.com.ederson.shopping.domain.ShoppingCartItem;
import br.com.ederson.shopping.dto.ShoppingCartCheckoutDTO;
import br.com.ederson.shopping.response.Response;
import br.com.ederson.shopping.service.ShoppingCartService;
import javassist.NotFoundException;

@RestController
@RequestMapping("/api/shoppingCart")
public class ShoppingCartController {
	
	@Autowired
	private ShoppingCartService shoppingCartService;
	
	@GetMapping("/{userId}")
	public ResponseEntity<Response<ShoppingCart>> findByUserId(@PathVariable Long userId) {
		Optional<ShoppingCart> shoppingCart = shoppingCartService.findByUserId(userId);
		if (!shoppingCart.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Response.error("commons.message.notFound"));
		}
		return ResponseEntity.ok(Response.success(shoppingCart.get()));
	}
	
	@PostMapping("/{userId}")
	public ResponseEntity<Response<ShoppingCart>> saveItem(@PathVariable Long userId,
			@RequestBody ShoppingCartItem shoppingCartItem) throws NotFoundException {
		return ResponseEntity.ok(Response.success(shoppingCartService.saveItem(userId, shoppingCartItem)));
	}

	@DeleteMapping("/{userId}")
	public ResponseEntity<Response<List<ShoppingCartItem>>> removeItem(@PathVariable Long userId, @RequestBody Item item)
			throws NotFoundException {
		List<ShoppingCartItem> removedItem = shoppingCartService.removeItem(userId, item);
		return ResponseEntity.ok(Response.success(removedItem));
	}
	
	@GetMapping("/checkout/{userId}")
	public ResponseEntity<Response<List<ShoppingCartCheckoutDTO>>> checkout(@PathVariable Long userId) {
		return ResponseEntity.ok(Response.success(shoppingCartService.checkout(userId)));
	}

}
