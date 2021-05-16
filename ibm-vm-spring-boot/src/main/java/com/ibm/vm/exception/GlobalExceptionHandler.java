package com.ibm.vm.exception;

import com.ibm.vm.model.ResponseMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ResponseEntity<ResponseMessage> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            WebRequest request) {
        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setCode(HttpStatus.UNPROCESSABLE_ENTITY.value());
        responseMessage.setMessage("Validation error. Check 'errors' field and Try again.");

        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            responseMessage.addValidationError(fieldError.getField(),
                    fieldError.getDefaultMessage());
        }
        return ResponseEntity.unprocessableEntity().body(responseMessage);
    }


    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ResponseMessage> handleAllUncaughtException(
            Exception exception,
            WebRequest request) {
        ResponseMessage responseMessage = new ResponseMessage();
        logger.error("Unknown error occurred: ", exception);
        responseMessage.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        responseMessage.setMessage(exception.getMessage());
        return new ResponseEntity<ResponseMessage>(responseMessage, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
