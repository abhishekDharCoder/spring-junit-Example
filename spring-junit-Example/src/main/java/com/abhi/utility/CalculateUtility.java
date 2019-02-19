package com.abhi.utility;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import org.springframework.stereotype.Component;

import com.abhi.domain.TransactionTO;

/**
 * @author Abhishek
 *
 */
@Component
public class CalculateUtility {

	/**
	 *  Sum all amounts in list
	 * @param list List of Transaction
	 * @return Sum
	 */
	public BigDecimal sum(List<TransactionTO> list) {
		BigDecimal value = new BigDecimal("0.00");

		for (TransactionTO to : list) {
			value = value.add(to.getAmount());
		}

		return value;
	}
	
	/**
	 * Average of all amounts in list
	 * @param list List of Transaction
	 * @return Average
	 */
	public BigDecimal average(List<TransactionTO> list) {
		BigDecimal value = new BigDecimal("0.00");
		if(!list.isEmpty()){
		value=sum(list).divide(new BigDecimal(list.size()), RoundingMode.HALF_UP);
		}
		return value;
	}
	
	/** 
	 * Max of all amount in the list
	 * @param list List of Transaction
	 * @return max amount
	 */
	public BigDecimal max(List<TransactionTO> list) {
		BigDecimal value = new BigDecimal("0.00");

		for (TransactionTO to : list) {
			if(value.compareTo(to.getAmount())<=0)
			value = to.getAmount();
		}

		return value;
	}
	
	/** 
	 * Min of all amount in the list
	 * @param list List of Transaction
	 * @return min amount
	 */
	public BigDecimal min(List<TransactionTO> list) {
		BigDecimal value = new BigDecimal("0.00");
		if(!list.isEmpty())
			value=list.get(0).getAmount();

		for (TransactionTO to : list) {
			if(value.compareTo(to.getAmount())>0)
			value = to.getAmount();
		}

		return value;
	}
}
