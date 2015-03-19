package org.school.fee.support.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.joda.time.DateTime;
import org.junit.Test;

import static org.junit.Assert.*;

public class StringToDateTest {

	@Test
	public void testSimpleDate() throws ParseException{
		String format = "yyyy-MM-dd";
		String str = "2014-3-15 22:22:00";
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Date date = sdf.parse(str);
		DateTime dateTime = new DateTime(2014, 3, 15, 0, 0, 0);
		assertEquals(date, dateTime.toDate());
		
		format = "yyyy-MM-dd HH:mm:ss";
		str = "2014-3-15 00:00:00";
		sdf = new SimpleDateFormat(format);
		date = sdf.parse(str);
		dateTime = new DateTime(2014, 3, 15, 0, 0, 0);
		assertEquals(date, dateTime.toDate());
	}
}
