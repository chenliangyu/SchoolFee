package org.school.fee.converters;

import java.math.BigDecimal;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

@ReadingConverter
public class DoubleToBigDecimalConverter implements Converter<Double, BigDecimal>{

	public BigDecimal convert(Double money) {
		// TODO Auto-generated method stub
		return new BigDecimal(money);
	}

}
