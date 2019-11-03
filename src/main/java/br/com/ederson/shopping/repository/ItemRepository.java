package br.com.ederson.shopping.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.ederson.shopping.domain.Item;

public interface ItemRepository extends JpaRepository<Item, Long> {
	
}
