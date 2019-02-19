package com.abhi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.abhi.domain.StatisticsTO;
import com.abhi.domain.TransactionTO;
import com.abhi.services.TransactionService;

/**
 * 
 * @author Abhishek
 *
 */
@RestController
public class TransactionController {

	@Autowired
	private TransactionService transactionServiceImpl;

	/**
	 *  Save Transaction
	 * @param transactionTO
	 * @return Response with status code.
	 */
	@RequestMapping(value = "/transactions", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<String> saveTransaction(@RequestBody TransactionTO transactionTO) {

		transactionServiceImpl.saveTransaction(transactionTO);
		return new ResponseEntity<String>("", HttpStatus.CREATED);

	}

	/**
	 *  Delete all Transactions
	 * @return Response with status code.
	 */
	@RequestMapping(value = "/transactions", method = RequestMethod.DELETE, produces = "application/json")
	public ResponseEntity<String> deleteTransactions() {

		transactionServiceImpl.deleteTransaction();
		return new ResponseEntity<String>("", HttpStatus.NO_CONTENT);
	}

	/**
	 * Fetch Statistics of all transactions in last 60 sec.
	 * @return Statistics
	 */
	@RequestMapping(value = "/statistics", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<StatisticsTO> getStatistics() {

		StatisticsTO statisticsTO = null;
		statisticsTO = transactionServiceImpl.getStatistics();
		return new ResponseEntity<StatisticsTO>(statisticsTO, HttpStatus.OK);

	}

}
