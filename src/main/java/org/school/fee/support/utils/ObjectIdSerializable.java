package org.school.fee.support.utils;

import java.io.IOException;

import org.bson.types.ObjectId;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class ObjectIdSerializable extends JsonSerializer<ObjectId>{
	@Override
	public void serialize(ObjectId value, JsonGenerator gen,
			SerializerProvider serializers) throws IOException,
			JsonProcessingException {
		// TODO Auto-generated method stub
	    gen.writeString(value.toString());
	}
}
