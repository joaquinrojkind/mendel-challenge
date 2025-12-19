//package com.mendel.exchange.api.exception;
//
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//
//import javax.persistence.EntityNotFoundException;
//
//@ControllerAdvice
//public class ExchangeOrdersExceptionHandler {
//
//	@ExceptionHandler({ RuntimeException.class })
//	public ResponseEntity handleRuntimeException(RuntimeException exception) {
//		ApiErrorDto errorPayload = ApiErrorDto.builder()
//				.status(HttpStatus.INTERNAL_SERVER_ERROR.value())
//				.code(HttpStatus.INTERNAL_SERVER_ERROR.name())
//				.message("Internal error while processing the exchange")
//				.build();
//		return ResponseEntity
//			.status(errorPayload.getStatus())
//			.body(errorPayload);
//	}
//
//	@ExceptionHandler({ EntityNotFoundException.class })
//	public ResponseEntity handleEntityNotFoundException(EntityNotFoundException exception) {
//		ApiErrorDto errorPayload = ApiErrorDto.builder()
//				.status(HttpStatus.NOT_FOUND.value())
//				.code(HttpStatus.NOT_FOUND.name())
//				.message("Exchange order not found")
//				.build();
//		return ResponseEntity
//				.status(errorPayload.getStatus())
//				.body(errorPayload);
//	}
//
//	@ExceptionHandler({ SecurityException.class })
//	public ResponseEntity handleSecurityException(SecurityException exception) {
//		ApiErrorDto errorPayload = ApiErrorDto.builder()
//				.status(HttpStatus.UNAUTHORIZED.value())
//				.code(HttpStatus.UNAUTHORIZED.name())
//				.message("Invalid authentication token")
//				.build();
//		return ResponseEntity
//				.status(errorPayload.getStatus())
//				.body(errorPayload);
//	}
//
//}
