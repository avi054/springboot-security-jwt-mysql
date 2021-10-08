package edu.aviral.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorResponse {
	
	private String message;
	private String path;
	private String error;
	
}
