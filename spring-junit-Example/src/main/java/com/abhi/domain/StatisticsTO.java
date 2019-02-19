package com.abhi.domain;

import java.math.BigDecimal;
import java.math.BigInteger;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Abhishek
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatisticsTO {
	
	private String sum;
	private String avg;
	private String max;
	private String min;
	private BigInteger count;

}
