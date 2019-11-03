package br.com.ederson.shopping.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@EqualsAndHashCode
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShoppingCart {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Getter
	private Long id;
	
	@OneToOne
	@Getter @Setter
	private User user;
	
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "shoppingCartId")
	@Getter @Setter
	@Builder.Default
	private List<ShoppingCartItem> shoppingCartItems = new ArrayList<>();
	
	public void addShoppingCartItem(ShoppingCartItem shoppingCartItem) {
		this.shoppingCartItems.add(shoppingCartItem);
	}
	
	public void removeShoppingCartItem(ShoppingCartItem shoppingCartItem) {
		this.shoppingCartItems.remove(shoppingCartItem);
	}
	
}
