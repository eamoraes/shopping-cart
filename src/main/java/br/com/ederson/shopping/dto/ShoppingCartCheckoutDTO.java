package br.com.ederson.shopping.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShoppingCartCheckoutDTO {

	private String name;

	private Long quantity;
	
	private BigDecimal total;
	
}
