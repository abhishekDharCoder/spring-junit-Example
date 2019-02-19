package com.abhi.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.time.Instant;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;

import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.abhi.domain.StatisticsTO;
import com.abhi.domain.TransactionTO;
import com.abhi.exception.FutureDateException;
import com.abhi.exception.OlderTransactionException;
import com.abhi.services.TransactionService;

@RunWith(SpringRunner.class)
@WebMvcTest
public class TransactionControllerTest {
	
	@Autowired
	MockMvc mockMvc;
	
	@MockBean
	private TransactionService transactionServiceImpl;
	
	
	@Test
	public void saveTransactionTest_sucess() throws Exception{
		
		
	Mockito.doNothing().when(transactionServiceImpl).saveTransaction(Matchers.any(TransactionTO.class));
		JSONObject body=new JSONObject();
		body.put("amount", "12.33");
		body.put("timestamp",Instant.now());
		
		 ResultActions resultActions = mockMvc.perform(post("/transactions")
                 .content(body.toString())
                 .contentType(MediaType.APPLICATION_JSON_UTF8));
         MockHttpServletResponse mockResponse = resultActions.andReturn()
                 .getResponse();
         
         
        Mockito.verify(transactionServiceImpl,Mockito.atLeastOnce()).saveTransaction(Matchers.any(TransactionTO.class));
		Assert.assertEquals(201, mockResponse.getStatus());
	}
	
	@Test
	public void saveTransactionTest_jsonParsingIssue() throws Exception{
		
		
	Mockito.doNothing().when(transactionServiceImpl).saveTransaction(Matchers.any(TransactionTO.class));
		JSONObject body=new JSONObject();
		body.put("amount", "12.33");
		body.put("timestamp",new Date());
		
		 ResultActions resultActions = mockMvc.perform(post("/transactions")
                 .content(body.toString())
                 .contentType(MediaType.APPLICATION_JSON_UTF8));
         MockHttpServletResponse mockResponse = resultActions.andReturn()
                 .getResponse();
         
         
		Assert.assertEquals(422, mockResponse.getStatus());
	}
	
	@Test
	public void saveTransactionTest_jsonInvalid() throws Exception{
		
		
	Mockito.doNothing().when(transactionServiceImpl).saveTransaction(Matchers.any(TransactionTO.class));
		
		 ResultActions resultActions = mockMvc.perform(post("/transactions")
                 .content("{")
                 .contentType(MediaType.APPLICATION_JSON_UTF8));
         MockHttpServletResponse mockResponse = resultActions.andReturn()
                 .getResponse();
         
         
		Assert.assertEquals(400, mockResponse.getStatus());
	}
	
	@Test
	public void saveTransactionTest_OldTransaction() throws Exception{
		
		
		JSONObject body=new JSONObject();
		body.put("amount", "12.33");
		body.put("timestamp",Instant.now());
		
	Mockito.doThrow(OlderTransactionException.class).when(transactionServiceImpl).saveTransaction(Matchers.any(TransactionTO.class));
		
		 ResultActions resultActions = mockMvc.perform(post("/transactions")
                 .content(body.toString())
                 .contentType(MediaType.APPLICATION_JSON_UTF8));
         MockHttpServletResponse mockResponse = resultActions.andReturn()
                 .getResponse();
         
         
		Assert.assertEquals(204, mockResponse.getStatus());
	}
	
	@Test
	public void saveTransactionTest_FutureTransaction() throws Exception{
		
		
		JSONObject body=new JSONObject();
		body.put("amount", "12.33");
		body.put("timestamp",Instant.now());
		
	Mockito.doThrow(FutureDateException.class).when(transactionServiceImpl).saveTransaction(Matchers.any(TransactionTO.class));
		
		 ResultActions resultActions = mockMvc.perform(post("/transactions")
                 .content(body.toString())
                 .contentType(MediaType.APPLICATION_JSON_UTF8));
         MockHttpServletResponse mockResponse = resultActions.andReturn()
                 .getResponse();
         
         
		Assert.assertEquals(422, mockResponse.getStatus());
	}
	
	@Test
	public void saveTransactionTest_genericException() throws Exception{
		
		
		JSONObject body=new JSONObject();
		body.put("amount", "12.33");
		body.put("timestamp",Instant.now());
		
	Mockito.doThrow(RuntimeException.class).when(transactionServiceImpl).saveTransaction(Matchers.any(TransactionTO.class));
		
		 ResultActions resultActions = mockMvc.perform(post("/transactions")
                 .content(body.toString())
                 .contentType(MediaType.APPLICATION_JSON_UTF8));
         MockHttpServletResponse mockResponse = resultActions.andReturn()
                 .getResponse();
         
         
		Assert.assertEquals(417, mockResponse.getStatus());
	}
	
	@Test
	public void deleteTransactionTest() throws Exception{
		
	Mockito.doNothing().when(transactionServiceImpl).deleteTransaction();
		
		 ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.delete("/transactions")
                 .contentType(MediaType.APPLICATION_JSON_UTF8));
         MockHttpServletResponse mockResponse = resultActions.andReturn()
                 .getResponse();
         
         
		Assert.assertEquals(204, mockResponse.getStatus());
	}
	
	@Test
	public void getStatisticsTest() throws Exception{
		
		StatisticsTO statisticsTO=new StatisticsTO();
		statisticsTO.setAvg("12.28");
	Mockito.when(transactionServiceImpl.getStatistics()).thenReturn(statisticsTO);
		
		 ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/statistics")
                 .contentType(MediaType.APPLICATION_JSON_UTF8));
         MockHttpServletResponse mockResponse = resultActions.andReturn()
                 .getResponse();
         
		Assert.assertNotNull(mockResponse.getContentAsString());
	}

}
