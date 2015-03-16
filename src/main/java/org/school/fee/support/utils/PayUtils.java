package org.school.fee.support.utils;

import java.util.Date;

import org.joda.time.DateTime;
import org.school.fee.models.PayResult;
import org.school.fee.models.Payment;
import org.school.fee.support.enums.InstalmentMethod;

public class PayUtils {
	public static Date getNextExpireDate(Payment payment){
		if(payment.getPayResults().size() != 0){
			Date expireDate = payment.getPayResults().get(payment.getPayResults().size() - 1).getExpireDate();
			DateTime expire = new DateTime(expireDate);
			switch(InstalmentMethod.values()[payment.getInstalmentMethod()]){
				case Week:
					return expire.plusWeeks(1).toDate();
				case Month:
					return expire.plusMonths(1).toDate();
			}
		}else{
			DateTime now = DateTime.now();
			switch(InstalmentMethod.values()[payment.getInstalmentMethod()]){
				case Week:
					DateTime thisweek = now.dayOfWeek().setCopy(payment.getExpireDayOfWeek());
					if(thisweek.isAfterNow()){
						return thisweek.toDate();
					}else{
						return thisweek.plusWeeks(1).toDate();
					}
				case Month:
					DateTime thisMonth = now.dayOfMonth().setCopy(payment.getExpireDayOfMonth());
					if(thisMonth.isAfterNow()){
						return thisMonth.toDate();
					}else{
						return thisMonth.plusMonths(1).toDate();
					}
			}
		}
		return null;
	}
}
