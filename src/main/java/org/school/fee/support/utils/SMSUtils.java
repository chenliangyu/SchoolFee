package org.school.fee.support.utils;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SMSUtils {
	private static Logger logger = LoggerFactory.getLogger(SMSUtils.class);
	public static void sendSMS(List<String> phones,String msg){
		for(String phone : phones){
			sendSMS(phone, msg);
		}
	}
	public static void sendSMS(String phone,String msg){
		logger.debug("send a sms message to {},the content is {}",phone,msg);
	}
}
