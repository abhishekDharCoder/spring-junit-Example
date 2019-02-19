package com.abhi.services;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.abhi.domain.TransactionTO;
import com.abhi.exception.FutureDateException;
import com.abhi.exception.OlderTransactionException;
import com.abhi.repositories.TransactionRepository;
import com.abhi.services.TransactionServiceImpl;
import com.abhi.utility.CalculateUtility;

@RunWith(MockitoJUnitRunner.class)
public class TransactionServiceImplTest {
	
	@Mock
	private TransactionRepository transactionRepository;

	@Mock
	private CalculateUtility calculateUtility;
	
	@InjectMocks
	private TransactionServiceImpl transactionServiceImpl=new TransactionServiceImpl();
	
	
	@Test
	public void saveTransactionTest(){
		TransactionTO transactionTO=new TransactionTO();
		Mockito.when(transactionRepository.save(Matchers.any(TransactionTO.class))).thenReturn(transactionTO);
		
		TransactionTO to=new TransactionTO();
		to.setAmount(new BigDecimal("12.22"));
		to.setTimestamp(new Date());
		
		transactionServiceImpl.saveTransaction(to);
		
		Mockito.verify(transactionRepository,Mockito.atLeastOnce()).save(Matchers.any(TransactionTO.class));
		
	}
	
	
	@Test(expected=FutureDateException.class)
	public void saveTransactionTest_FutureDateException(){
		
		Date dNow = new Date(); // Instantiate a Date object
		Calendar cal = Calendar.getInstance();
		cal.setTime(dNow);
		cal.add(Calendar.MINUTE, 3);
		dNow = cal.getTime();
		TransactionTO to=new TransactionTO();
		to.setAmount(new BigDecimal("12.22"));
		to.setTimestamp(dNow);
		transactionServiceImpl.saveTransaction(to);
	}
	
	@Test(expected=OlderTransactionException.class)
	public void saveTransactionTest_OlderTransactionException(){
		
		Date dNow = new Date(); // Instantiate a Date object
		Calendar cal = Calendar.getInstance();
		cal.setTime(dNow);
		cal.add(Calendar.MINUTE, -3);
		dNow = cal.getTime();
		TransactionTO to=new TransactionTO();
		to.setAmount(new BigDecimal("12.22"));
		to.setTimestamp(dNow);
		transactionServiceImpl.saveTransaction(to);
	}

	

	@Test
	public void deleteTransactionTest() {
		Mockito.doNothing().when(transactionRepository).deleteAll();
		transactionServiceImpl.deleteTransaction();
		
		Mockito.verify(transactionRepository,Mockito.atLeastOnce()).deleteAll();
	}
	
	@Test
	public void getStatistics(){
		
		TransactionTO to=new TransactionTO();
		to.setAmount(new BigDecimal("12.22"));
		to.setTimestamp(new Date());
		
		List<TransactionTO> sampleList=new ArrayList<>();
		sampleList.add(to);
		sampleList.add(to);
		sampleList.add(to);
		sampleList.add(to);
		sampleList.add(to);
		sampleList.add(to);
		
		
		Mockito.when(transactionRepository.find(Matchers.any(Date.class))).thenReturn(sampleList);
		Mockito.when(calculateUtility.sum(sampleList)).thenReturn(new BigDecimal("12"));
		Mockito.when(calculateUtility.average(sampleList)).thenReturn(new BigDecimal("12"));
		Mockito.when(calculateUtility.max(sampleList)).thenReturn(new BigDecimal("12"));
		Mockito.when(calculateUtility.min(sampleList)).thenReturn(new BigDecimal("12"));
		
		transactionServiceImpl.getStatistics();
		
		Mockito.verify(calculateUtility,Mockito.atLeastOnce()).average(sampleList);
		} 
}
