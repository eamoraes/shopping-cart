package br.com.ederson.shopping.response;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Response<T> {

	private T data;
	private Set<String> errors;

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public Set<String> getErrors() {
		if (errors == null) {
			errors = new HashSet<>();
		}
		return errors;
	}

	public void setErrors(Set<String> errors) {
		this.errors = errors;
	}

	public static <T> Response<T> success(T data) {
		Response<T> response = new Response<>();
		response.setData(data);
		return response;
	}

	public static <T> Response<T> errors(String... errors) {
		Response<T> response = new Response<>();
		response.setErrors(new HashSet<>(Arrays.asList(errors)));
		return response;
	}

	public static <T> Response<T> error(String message) {
		Response<T> response = new Response<>();
		response.getErrors().add(message);
		return response;
	}

}
