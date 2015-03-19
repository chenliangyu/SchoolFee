package org.school.fee.support.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.springframework.core.convert.converter.Converter;

public class StringToDateConverter implements Converter<String, Date>{
	private String format;
	private Logger logger = org.slf4j.LoggerFactory.getLogger(StringToDateConverter.class);
	
	public Date convert(String dateStr) {
		// TODO Auto-generated method stub
		SimpleDateFormat sdf = new SimpleDateFormat(getFormat());
		try{
			return sdf.parse(dateStr);
		}catch(Exception e){
			logger.error(e.getMessage());
		}
		return null;
	}

	/**
	 * @return the format
	 */
	public String getFormat() {
		return format;
	}


	/**
	 * @param format the format to set
	 */
	public void setFormat(String format) {
		this.format = format;
	}

}
