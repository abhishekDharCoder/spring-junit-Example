package com.abhi.repositories;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Component;

import com.abhi.domain.TransactionTO;

/**
 * @author Abhishek
 *
 */
@Component
public class TransactionRepository {

	List<TransactionTO> transaction=new ArrayList<>();
	
	public synchronized TransactionTO save(TransactionTO to){
		transaction.add(to);
		return to;
	}
	
	public synchronized void deleteAll(){
		transaction.removeAll(transaction);
	}

	public synchronized List<TransactionTO> find(Date dNow) {
		List<TransactionTO> transactionTo=new ArrayList<>();
		
		for(TransactionTO to:transaction){
			if(to.getTimestamp().after(dNow)){
				transactionTo.add(to);
			}
		}
		
		transaction.retainAll(transactionTo);
		
		return transactionTo;
	}

}
