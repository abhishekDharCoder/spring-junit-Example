package com.abhi.services;

import com.abhi.domain.StatisticsTO;
import com.abhi.domain.TransactionTO;
import com.abhi.exception.FutureDateException;
import com.abhi.exception.OlderTransactionException;

/**
 *  Transaction Service
 * @author Abhishek
 *
 */
public interface TransactionService {
	
	
	/**
	 *  Save Transaction
	 * @param transactionTO
	 */
	public void saveTransaction(TransactionTO transactionTO);
	/**
	 * Delete all the Transaction
	 * 
	 */
	public void deleteTransaction();
	/**
	 * Fetch Statistics of all the Transactions in last 60 secs
	 * @return
	 */
	public StatisticsTO getStatistics();

}
