package br.com.ederson.shopping.repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.ederson.shopping.domain.ShoppingCartItem;

public interface ShoppingCartItemRepository extends JpaRepository<ShoppingCartItem, Long> {
	
	Optional<ShoppingCartItem> findByShoppingCartIdAndItemId(Long shoppingCartId, Long itemId);

	@Transactional
	List<ShoppingCartItem> deleteByShoppingCartIdAndItemId(Long shoppingCartId, Long itemId);
	
	List<ShoppingCartItem> findByShoppingCartIdOrderByItemNameAscTotalAsc(Long shoppingCartId);

}
