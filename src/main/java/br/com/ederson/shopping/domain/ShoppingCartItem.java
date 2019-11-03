package br.com.ederson.shopping.domain;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

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
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ShoppingCartItem {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Getter
	private Long id;
	
	@Getter @Setter
	@Column(insertable = false, updatable = false)
	private Long shoppingCartId;
	
	@Getter @Setter
	private Long quantity;
	
	@OneToOne
	@Getter @Setter
	private Item item;

	@Getter
	private BigDecimal total;

    @PrePersist
    public void prePersist() {
    	this.total = item.getValue().multiply(new BigDecimal(quantity));
    }
 
    @PreUpdate
    public void preUpdate() {
    	this.total = item.getValue().multiply(new BigDecimal(quantity));
    }
	
}
