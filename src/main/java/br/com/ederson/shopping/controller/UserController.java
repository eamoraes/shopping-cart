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

import br.com.ederson.shopping.domain.User;
import br.com.ederson.shopping.response.Response;
import br.com.ederson.shopping.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping
	public ResponseEntity<Response<List<User>>> list() {
		return ResponseEntity.ok(Response.success(userService.findAll()));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Response<User>> findById(@PathVariable Long id) {
		Optional<User> user = userService.findById(id);
		if (!user.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Response.error("commons.message.notFound"));
		}
		return ResponseEntity.ok(Response.success(user.get()));
	}
	
	@PostMapping
	public ResponseEntity<Response<User>> create(@Valid @RequestBody User user) throws Exception {
		return ResponseEntity.created(URI.create("/api/users/" + user.getId()))
				.body(Response.success(userService.save(user)));
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Response<User>> update(@PathVariable Long id, @Valid @RequestBody User user) throws Exception {
		return ResponseEntity.ok(Response.success(userService.update(id, user)));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Response<User>> delete(@PathVariable Long id) {
		Optional<User> user = userService.delete(id);
		
		if (!user.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Response.error("commons.message.notFound"));
		}
		return ResponseEntity.ok(Response.success(user.get()));
	}

}
