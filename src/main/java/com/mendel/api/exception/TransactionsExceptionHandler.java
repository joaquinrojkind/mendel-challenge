package com.mendel.api.exception;

import com.mendel.persistence.repository.exception.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class TransactionsExceptionHandler {

	@ExceptionHandler({ RuntimeException.class })
	public ResponseEntity handleRuntimeException(RuntimeException exception) {
		ApiErrorDto errorPayload = ApiErrorDto.builder()
				.status(HttpStatus.INTERNAL_SERVER_ERROR.value())
				.code(HttpStatus.INTERNAL_SERVER_ERROR.name())
				.message("Internal error while processing the transaction")
				.build();
		return ResponseEntity
			.status(errorPayload.getStatus())
			.body(errorPayload);
	}

	@ExceptionHandler({ EntityNotFoundException.class })
	public ResponseEntity handleEntityNotFoundException(EntityNotFoundException exception) {
		ApiErrorDto errorPayload = ApiErrorDto.builder()
				.status(HttpStatus.NOT_FOUND.value())
				.code(HttpStatus.NOT_FOUND.name())
				.message("Transaction not found")
				.build();
		return ResponseEntity
				.status(errorPayload.getStatus())
				.body(errorPayload);
	}

}
