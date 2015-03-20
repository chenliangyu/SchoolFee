package org.school.fee.batch;

import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.school.fee.models.Payment;
import org.school.fee.service.MessageService;
import org.school.fee.service.PaymentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

public class BatchJob {
	private Logger logger = LoggerFactory.getLogger(BatchJob.class);
	@Autowired
	PaymentService paymentService;
	@Autowired
	MessageService messageService;
	
	@Scheduled(cron="*/1 * * * * ?")
	public void execute(){  
		try{
			List<Payment> payments = paymentService.findNotClearPaymentByDate(new Date());
			logger.debug("find not clear payment :{}",payments.size());
			Iterator<Payment> it = payments.iterator();
			while(it.hasNext()){
				Payment payment = it.next();
				messageService.sendMessage(payment);
			}
		}catch(Exception ex){  
			ex.printStackTrace();  
		}  
	}
}
