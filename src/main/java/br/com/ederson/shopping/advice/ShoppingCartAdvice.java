package br.com.ederson.shopping.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import br.com.ederson.shopping.response.Response;
import javassist.NotFoundException;

@RestControllerAdvice
public class ShoppingCartAdvice {

	@ExceptionHandler(Exception.class)
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	public ResponseEntity<Response<?>> handlerException(Exception ex) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(Response.errors(ex.getMessage()));
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Response<?>> handlerBeanValidation(MethodArgumentNotValidException ex) {
		Response<?> response = new Response<>();
		ex.getBindingResult().getAllErrors().forEach(err -> response.getErrors().add(err.getDefaultMessage()));
		return ResponseEntity.badRequest().body(response);
	}
	
	@ExceptionHandler(NotFoundException.class)
	@ResponseStatus(code = HttpStatus.NOT_FOUND)
	public ResponseEntity<Response<?>> handlerUserNotFoundException(NotFoundException ex) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Response.error(ex.getMessage()));
	}
	
}
