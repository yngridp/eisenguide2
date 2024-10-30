package com.eisenguide2.exception;

	public class UserNotFoundException extends RuntimeException {
	    public UserNotFoundException(Long userId) {
	        super("Usuário não encontrado por ID: " + userId);
	    }
	}
