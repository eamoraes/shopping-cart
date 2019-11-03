package br.com.ederson.shopping.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.ederson.shopping.domain.Item;
import br.com.ederson.shopping.domain.ShoppingCart;
import br.com.ederson.shopping.domain.ShoppingCartItem;
import br.com.ederson.shopping.dto.ShoppingCartCheckoutDTO;
import br.com.ederson.shopping.repository.ShoppingCartItemRepository;
import br.com.ederson.shopping.repository.ShoppingCartRepository;
import javassist.NotFoundException;

@Service
public class ShoppingCartService {

	@Autowired
	private ShoppingCartRepository shoppingCartRepository;
	
	@Autowired
	private ShoppingCartItemRepository shoppingCartItemRepository;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ItemService itemService;
	
	public ShoppingCart saveItem(Long userId, ShoppingCartItem shoppingCartItem) throws NotFoundException {
		
		Optional<ShoppingCart> shoppingCart = this.findByUserId(userId);
		if (shoppingCart.isPresent()) {
			return updateUserShoppingCart(shoppingCartItem, shoppingCart.get());
		} else {
			return createUserShoppingCart(userId, shoppingCartItem);
		}
	}

	private ShoppingCart createUserShoppingCart(Long userId, ShoppingCartItem shoppingCartItem)
			throws NotFoundException {
		ShoppingCart shoppingCartNew = new ShoppingCart();
		shoppingCartNew.setUser(userService.findById(userId).orElseThrow(() -> new NotFoundException("user.notFound")));
		itemValidator(shoppingCartItem);
		
		shoppingCartNew.getShoppingCartItems().add(shoppingCartItem);
		shoppingCartRepository.save(shoppingCartNew);
		return shoppingCartNew;
	}

	private ShoppingCart updateUserShoppingCart(ShoppingCartItem shoppingCartItem, ShoppingCart shoppingCart) throws NotFoundException {
		Optional<ShoppingCartItem> shoppingCartItemFound = shoppingCartItemRepository
				.findByShoppingCartIdAndItemId(shoppingCart.getId(), shoppingCartItem.getItem().getId());
		if (shoppingCartItemFound.isPresent()) {
			shoppingCartItemFound.get().setQuantity(shoppingCartItemFound.get().getQuantity() + shoppingCartItem.getQuantity());
		} else {
			itemValidator(shoppingCartItem);
			shoppingCart.addShoppingCartItem(shoppingCartItem);
		}
		
		return shoppingCartRepository.save(shoppingCart);
	}
	
	private void itemValidator(ShoppingCartItem shoppingCartItem) throws NotFoundException {
		Optional<Item> itemFound = itemService.findById(shoppingCartItem.getItem().getId());
		if (!itemFound.isPresent())
			throw new NotFoundException("item.notFound");
	}
	
	public Optional<ShoppingCart> findByUserId(Long userId) {
		return shoppingCartRepository.findByUserId(userId);
	}
	
	public List<ShoppingCartItem> removeItem(Long userId, Item item) throws NotFoundException {
		
		Optional<ShoppingCart> shoppingCart = this.findByUserId(userId);
		if (shoppingCart.isPresent()) {
			Optional<ShoppingCartItem> shoppingCartItemFound = shoppingCartItemRepository
					.findByShoppingCartIdAndItemId(shoppingCart.get().getId(), item.getId());

			if (shoppingCartItemFound.isPresent()) {
				return shoppingCartItemRepository.deleteByShoppingCartIdAndItemId(shoppingCart.get().getId(), item.getId());
			} else {
				throw new NotFoundException("item.notFound");
			}
		} else {
			throw new NotFoundException("shoppingCart.notFound");
		}
	}
	
	public List<ShoppingCartCheckoutDTO> checkout(Long userId) {
		List<ShoppingCartCheckoutDTO> shoppingCartCheckoutDTOList = new ArrayList<>();
		Long totalQuantity = 0L;
		BigDecimal totalAllItems = BigDecimal.ZERO;
		
		Optional<ShoppingCart> shoppingCart = this.findByUserId(userId);
		if (shoppingCart.isPresent()) {
			List<ShoppingCartItem> shoppingCartItems = shoppingCartItemRepository
					.findByShoppingCartIdOrderByItemNameAscTotalAsc(shoppingCart.get().getId());

			for (ShoppingCartItem shoppingCartItem : shoppingCartItems) {
				shoppingCartCheckoutDTOList.add(new ShoppingCartCheckoutDTO(shoppingCartItem.getItem().getName(),
						shoppingCartItem.getQuantity(), shoppingCartItem.getTotal()));
				totalQuantity += shoppingCartItem.getQuantity();
				totalAllItems = totalAllItems.add(shoppingCartItem.getTotal());
			}
		}

		shoppingCartCheckoutDTOList.add(new ShoppingCartCheckoutDTO("Total", totalQuantity, totalAllItems));
		
		return shoppingCartCheckoutDTOList;
	}
}

