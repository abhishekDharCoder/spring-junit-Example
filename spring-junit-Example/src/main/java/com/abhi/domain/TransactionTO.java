package com.abhi.domain;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;

/**
 * @author Abhishek
 *
 */
@Data
public class TransactionTO {
	
	private BigDecimal amount;
	private Date timestamp;

}
