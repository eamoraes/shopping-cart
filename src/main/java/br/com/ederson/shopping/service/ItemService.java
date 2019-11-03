package br.com.ederson.shopping.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.ederson.shopping.domain.Item;
import br.com.ederson.shopping.repository.ItemRepository;

@Service
public class ItemService {

	@Autowired
	private ItemRepository itemRepository;
	
	public Item create(Item item) {
		return itemRepository.save(item);
	}
	
	public Optional<Item> findById(Long id) {
		return itemRepository.findById(id);
	}

	public List<Item> findAll() {
		return itemRepository.findAll();
	}

	public Item update(Long id, Item item) throws Exception {
		if (!id.equals(item.getId())) {
			throw new Exception("item.update.failure");
		}
		return itemRepository.save(item);
	}

	public Optional<Item> delete(Long id) {
		Optional<Item> item = this.findById(id);
		if (!item.isPresent()) {
			return Optional.empty();
		}
		itemRepository.delete(item.get());
		return item;
	}
	
}

