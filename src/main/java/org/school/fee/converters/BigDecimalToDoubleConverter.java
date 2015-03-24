package org.school.fee.converters;

import java.math.BigDecimal;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;

@WritingConverter
public class BigDecimalToDoubleConverter implements Converter<BigDecimal, Double>{

	public Double convert(BigDecimal money) {
		// TODO Auto-generated method stub
		return money.doubleValue();
	}

}
