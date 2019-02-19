package com.abhi;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.abhi.exception.FutureDateException;
import com.abhi.exception.OlderTransactionException;

/**
 * @author Abhishek
 *
 */
@ControllerAdvice
public class CustomRestExceptionHandler{
	
	
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<String> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
    	System.out.println(e.getLocalizedMessage());
    	if(e.getLocalizedMessage().contains("InvalidFormatException")){
    		return new ResponseEntity<String>("",HttpStatus.UNPROCESSABLE_ENTITY);
    	}else{
    		return new ResponseEntity<String>("",HttpStatus.BAD_REQUEST);
    	}
     
    }
	
	@ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY, reason = "")
    @ExceptionHandler(FutureDateException.class)
    public void handleFutureDateException() {
     
    }
	
	@ResponseStatus(value = HttpStatus.NO_CONTENT, reason = "")
    @ExceptionHandler(OlderTransactionException.class)
    public void handleOlderTransactionException() {
     
    }
	
	@ResponseStatus(value = HttpStatus.EXPECTATION_FAILED, reason = "")
    @ExceptionHandler(Exception.class)
    public void handleException() {
     
    }
	
	}