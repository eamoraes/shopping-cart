package br.com.ederson.shopping.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.ederson.shopping.domain.Item;
import br.com.ederson.shopping.response.Response;
import br.com.ederson.shopping.service.ItemService;

@RestController
@RequestMapping("/api/items")
public class ItemController {
	
	@Autowired
	private ItemService itemService;
	
	@GetMapping
	public ResponseEntity<Response<List<Item>>> list() {
		return ResponseEntity.ok(Response.success(itemService.findAll()));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Response<Item>> findById(@PathVariable Long id) {
		Optional<Item> item = itemService.findById(id);
		if (!item.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Response.error("commons.message.notFound"));
		}
		return ResponseEntity.ok(Response.success(item.get()));
	}
	
	@PostMapping
	public ResponseEntity<Response<Item>> create(@Valid @RequestBody Item item) throws Exception {
		return ResponseEntity.created(URI.create("/api/items/" + item.getId()))
				.body(Response.success(itemService.create(item)));
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Response<Item>> update(@PathVariable Long id, @Valid @RequestBody Item item) throws Exception {
		return ResponseEntity.ok(Response.success(itemService.update(id, item)));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Response<Item>> delete(@PathVariable Long id) {
		Optional<Item> item = itemService.delete(id);
		if (!item.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Response.error("commons.message.notFound"));
		}
		return ResponseEntity.ok(Response.success(item.get()));
	}

}
