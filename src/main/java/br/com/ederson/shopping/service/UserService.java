package br.com.ederson.shopping.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.ederson.shopping.domain.User;
import br.com.ederson.shopping.repository.UserRepository;
import javassist.NotFoundException;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	public User save(User user) throws Exception {
		validateUserEmail(user);
		
		return userRepository.save(user);
	}

	public Optional<User> findById(Long id) {
		return userRepository.findById(id);
	}

	public List<User> findAll() {
		return userRepository.findAll();
	}

	public User update(Long id, User user) throws Exception {
		if (!id.equals(user.getId())) {
			throw new Exception("user.update.failure");
		}
		
		Optional<User> userFound = this.findById(id);
		
		if (!userFound.isPresent()) {
			throw new NotFoundException("user.notFound");
		}
		
		return this.save(user);
		
	}

	public Optional<User> delete(Long id) {
		Optional<User> user = this.findById(id);
		if (!user.isPresent()) {
			return Optional.empty();
		}
		userRepository.delete(user.get());
		return user;
	}
	
	private void validateUserEmail(User user) throws Exception {
		Optional<User> userFound = userRepository.findByEmail(user.getEmail());
		if (userFound.isPresent() && !userFound.get().getId().equals(user.getId())) {
			throw new Exception("user.already.exists");
		}
	}

}

