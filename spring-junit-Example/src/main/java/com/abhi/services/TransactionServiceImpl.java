package com.abhi.services;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abhi.domain.StatisticsTO;
import com.abhi.domain.TransactionTO;
import com.abhi.exception.FutureDateException;
import com.abhi.exception.OlderTransactionException;
import com.abhi.repositories.TransactionRepository;
import com.abhi.utility.CalculateUtility;

/**
 * @author Abhishek
 *
 */
@Service
public class TransactionServiceImpl implements TransactionService {

	@Autowired
	private TransactionRepository transactionRepository;

	@Autowired
	private CalculateUtility calculateUtility;

	@Override
	public void saveTransaction(TransactionTO transactionTO) {
		if (transactionTO.getTimestamp().after(new Date())) {
			throw new FutureDateException("Future Date");
		}
		Date dNow = new Date(); // Instantiate a Date object
		Calendar cal = Calendar.getInstance();
		cal.setTime(dNow);
		cal.add(Calendar.SECOND, -60);
		dNow = cal.getTime();

		if (transactionTO.getTimestamp().before(dNow)) {
			throw new OlderTransactionException("Transaction is older than 60 seconds");
		}

		transactionRepository.save(transactionTO);

	}

	@Override
	public void deleteTransaction() {
		transactionRepository.deleteAll();
	}

	@Override
	public StatisticsTO getStatistics() {
		StatisticsTO statisticsTO = new StatisticsTO();
		Date dNow = new Date(); // Instantiate a Date object
		Calendar cal = Calendar.getInstance();
		cal.setTime(dNow);
		cal.add(Calendar.SECOND, -60);
		dNow = cal.getTime();
		List<TransactionTO> transactionTOs = transactionRepository.find(dNow);

		statisticsTO.setSum(calculateUtility.sum(transactionTOs).setScale(2, BigDecimal.ROUND_HALF_UP).toString());
		statisticsTO.setAvg(calculateUtility.average(transactionTOs).setScale(2, BigDecimal.ROUND_HALF_UP).toString());
		statisticsTO.setMax(calculateUtility.max(transactionTOs).setScale(2, BigDecimal.ROUND_HALF_UP).toString());
		statisticsTO.setMin(calculateUtility.min(transactionTOs).setScale(2, BigDecimal.ROUND_HALF_UP).toString());
		statisticsTO.setCount(BigInteger.valueOf(transactionTOs.size()));

		return statisticsTO;
	}

}
