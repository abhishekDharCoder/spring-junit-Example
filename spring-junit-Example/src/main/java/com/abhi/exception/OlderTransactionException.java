package com.abhi.exception;

/**
 * @author Abhishek
 *
 */
public class OlderTransactionException  extends RuntimeException{
	
	public OlderTransactionException(String errorMessage){  
		  super(errorMessage);  
		 }  

}
