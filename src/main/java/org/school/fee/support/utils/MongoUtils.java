package org.school.fee.support.utils;

import com.mongodb.util.JSON;

public class MongoUtils {
	public static <T> T convertToDBObject(String jsonString,Class<T> klass){
		T t = (T)JSON.parse(jsonString);
		return t;
	}
}
