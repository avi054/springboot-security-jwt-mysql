package edu.aviral.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestApiExceptionHandler {
	
	private static final Logger logger = LoggerFactory.getLogger(RestApiExceptionHandler.class);

//	@ExceptionHandler(Exception.class)
//	public void handleException(Exception ex) throws Exception {
//		logger.error("{}", "************ UNKNOWN EXCEPTION ************");
//		logger.error("Exception message: {}", ex.getMessage());
//		ex.printStackTrace();
//		throw ex;
//	}
	
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> handleException(Exception ex) throws Exception {
		logger.error("{}", "************ UNKNOWN EXCEPTION ************");
		logger.error("Exception message: {}", ex.getMessage());
		ex.printStackTrace();
		
		ErrorResponse errorResponse = new ErrorResponse();
		errorResponse.setMessage(ex.getMessage());
		
		if(ex instanceof AuthenticationException) {
			AuthenticationException authEx = (AuthenticationException)ex;
			logger.info("{}","True");
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
		}

		throw ex;
	}
}
