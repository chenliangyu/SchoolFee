package org.school.fee.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;





import org.bson.types.ObjectId;
import org.school.fee.dao.PaymentDao;
import org.school.fee.models.Payment;
import org.school.fee.support.enums.PayMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Repository;

import com.mongodb.AggregationOutput;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;

@Repository
public class PaymentDaoImpl implements PaymentDao {
	private Logger logger = LoggerFactory.getLogger(PaymentDaoImpl.class);
	@Autowired
	MongoTemplate mongoTemplate;

	public Page<Payment> findPayment(Pageable page, ObjectId studentId,
			String studentName, String klass, String school, ObjectId feeId,
			String feeName, Boolean notClear, PayMethod payMethod,Date startDate, Date endDate) {
		// TODO Auto-generated method stub
		/*List<Criteria> criterias = buildCriteria(studentId, studentName, klass, school, feeId, feeName, startDate, endDate);
		long total = countPayment(criterias,studentId, studentName, klass, school, feeId,
				feeName, notClear, startDate, endDate);
		String[] fields = new String[]{"studentId","studentName","klass",
				"school","feeId","feeName","feeMoney","payDate","expireDate"};
		Aggregation agg = newAggregation(
			match((new Criteria()).orOperator(criterias.toArray(new Criteria[criterias.size()]))),
			unwind("money"),
			doGroup(fields),
			doProject(fields).andExpression("feeMoney>0").as("notClear"),
			match(where("notClear").is(true)),
			sort(page.getSort()),
			skip(page.getOffset()),
			limit(page.getPageSize())
		);
		AggregationResults<Payment> results = mongoTemplate.aggregate(agg,Payment.class,Payment.class);*/
		Query query = buildQuery(studentId, studentName, klass, school, feeId, feeName, notClear,payMethod, startDate, endDate);
		query.with(page);
		List<Payment> payments = mongoTemplate.find(query, Payment.class);
		/*
		
		DBCollection dbCollection = mongoTemplate.getCollection("payment");
		StringBuilder json = new StringBuilder();
		String[] fields = new String[]{"studentId","studentName","klass",
				"school","feeId","feeName","feeMoney","payDate","expireDate"};
		json.append("[");
		json.append(buildMatch(studentId, studentName, klass, school, feeId, feeName, startDate, endDate));
		if(notClear!=null){
			json.append("{'$unwind':'$money'},");
			json.append(buildGroup(fields)+",");
			json.append(buildProject(fields)+",");
			json.append("{$match:{'notClear':"+notClear+"}},");
		}
		Iterator<Order> it = page.getSort().iterator();
		Order order = it.next();
		int number = order.getDirection().ordinal();
		int orderValue = number == 0?1:-1;
		json.append("{$sort:{'"+order.getProperty()+"':"+orderValue+"}},");
		json.append("{$skip:"+page.getOffset()+"},");
		json.append("{$limit:"+page.getPageSize()+"}");
		json.append("]");
		logger.debug("find final query:{}",json);
		List<DBObject> pipeline = (List<DBObject>)JSON.parse(json.toString());
		AggregationOutput output = dbCollection.aggregate(pipeline);
		Iterable<DBObject> iterable = output.results();
		List<Payment> payments = new ArrayList<Payment>();
		Iterator<DBObject> outputIt = iterable.iterator();
		while(outputIt.hasNext()){
			DBObject dbObject = outputIt.next();
			payments.add(mongoTemplate.getConverter().read(Payment.class, dbObject));
		}*/
		long total = countPayment(studentId, studentName, klass, school, feeId, feeName, notClear,payMethod,startDate, endDate);
		return new PageImpl<Payment>(payments, page, total);
	}
	
	private Query buildQuery(ObjectId studentId,
			String studentName, String klass, String school, ObjectId feeId,
			String feeName, Boolean notClear,PayMethod payMethod,Date startDate, Date endDate){
		Query query = new Query();
		if(studentId!=null){
			query.addCriteria(Criteria.where("studentId").is(studentId));
			if(feeName!=null){
				query.addCriteria(Criteria.where("feeName").regex(feeName));
			}
		}else{
			if(feeId!=null){
				query.addCriteria(Criteria.where("feeId").is(feeId));
			}else{
				if(feeName!=null){
					query.addCriteria(Criteria.where("feeName").regex(feeName));
				}
			}
			
			if(klass!=null){
				query.addCriteria(Criteria.where("klass").regex(klass));
			}
			if(school!=null){
				query.addCriteria(Criteria.where("school").regex(school));
			}
			if(studentName!=null){
				query.addCriteria(Criteria.where("studentName").regex(studentName));
			}
			
		}
		if(payMethod!=null){
			query.addCriteria(Criteria.where("payMethod").is(payMethod.ordinal()));
		}
		if(startDate!=null && endDate!=null && startDate.equals(endDate)){
			query.addCriteria(Criteria.where("feeStartDate").is(startDate));
			query.addCriteria(Criteria.where("feeEndDate").is(endDate));
		}else if(startDate !=null || endDate!= null){
			List<Criteria> dateCriterias = new ArrayList<Criteria>();
			if(startDate!=null){
				dateCriterias.add(Criteria.where("feeStartDate").gte(startDate));
			}
			if(endDate!=null){
				dateCriterias.add(Criteria.where("feeEndDate").lte(endDate));
			}
			query.addCriteria(new Criteria().andOperator(dateCriterias.toArray(new Criteria[dateCriterias.size()])));
		}
		if(notClear!=null){
			query.addCriteria(Criteria.where("payResults.status").is(notClear?1:0));
		}
		return query;
	}
	
	
/*	private Query buildQuery(ObjectId studentId,
			String studentName, String klass, String school, ObjectId feeId,
			String feeName, Boolean notClear, Date startDate, Date endDate){
		Query query = new Query();
		return query;
	}*/
	
	public long countPayment(ObjectId studentId,
			String studentName, String klass, String school, ObjectId feeId,
			String feeName, Boolean notClear,PayMethod payMethod,Date startDate, Date endDate){
		
		Query query = buildQuery(studentId, studentName, klass, school, feeId, feeName, notClear, payMethod,startDate, endDate);
		long total = mongoTemplate.count(query, Payment.class);
		
		
		/*String[] fields = new String[]{"feeMoney"};
		DBCollection dbCollection = mongoTemplate.getCollection("payment");
		StringBuilder json = new StringBuilder();
		json.append("[");
		json.append(buildMatch(studentId, studentName, klass, school, feeId, feeName, startDate, endDate));
		if(notClear!=null){
			json.append("{'$unwind':'$money'},");
			json.append(buildGroup(fields)+",");
			json.append(buildProject(fields)+",");
			json.append("{$match:{'notClear':"+notClear+"}},");
		}
		json.append("{$group:{'_id':null,count:{$sum:1}}}");
		json.append("]");
		logger.debug("count final query:{}",json);
		List<DBObject> pipeline = (List<DBObject>)JSON.parse(json.toString());
		AggregationOutput output = dbCollection.aggregate(pipeline);
		Iterable<DBObject> iterable = output.results();
		Iterator<DBObject> outputIt = iterable.iterator();
		int total = 0;
		while(outputIt.hasNext()){
			DBObject dbObject = outputIt.next();
			total = (Integer)dbObject.get("count");
		}*/
		return total;
	}
	
/*	private List<Criteria> buildCriteria(ObjectId studentId,
			String studentName, String klass, String school, ObjectId feeId,
			String feeName,Date startDate, Date endDate){
		List<Criteria> criterias = new ArrayList<Criteria>();
		if(studentId!=null){
			criterias.add(where("studentId").is(studentId));
			if(feeName!=null){
				criterias.add(where("feeName").regex(".*"+feeName+".*"));
			}
		}else{
			if(feeId!=null){
				criterias.add(where("feeId").is(feeId));
			}else{
				if(feeName!=null){
					criterias.add(where("feeName").regex(".*"+feeName+".*"));
				}
			}
			if(studentName!=null){
				criterias.add(where("studentName").regex(".*"+studentName+".*"));
			}
			if(klass!=null){
				criterias.add(where("klass").regex(".*"+klass+".*"));
			}
			if(school!=null){
				criterias.add(where("school").regex(".*"+school+".*"));
			}
		}
		List<Criteria> andCriteria = new ArrayList<Criteria>();
		if(startDate!=null){
			andCriteria.add(where("payDate").gte(startDate));
		}
		if(endDate!=null){
			andCriteria.add(where("payDate").lte(endDate));
		}
		criterias.add(new Criteria().andOperator(andCriteria.toArray(new Criteria[andCriteria.size()])));
		return criterias;
	}*/
	private String buildMatch(ObjectId studentId,
			String studentName, String klass, String school, ObjectId feeId,
			String feeName,Date startDate, Date endDate){
		if(studentId!=null||studentName!=null||klass!=null||school!=null||feeId!=null||feeName!=null||startDate!=null||endDate!=null){
			StringBuilder result = new StringBuilder();
			result.append("{'$match':{");
			if(studentId!=null){
				result.append("'studentId':"+JSON.serialize(studentId));
				if(feeName!=null){
					result.append(",'feeName':{'$regex':'"+feeName+"'}");
				}
			}else{
				if(studentName!=null){
					result.append("'studentName':{'$regex':'"+studentName+"'},");
				}
				if(klass!=null){
					result.append("'klass':{'$regex':'"+klass+"'},");
				}
				if(school!=null){
					result.append("'school':{'$regex':'"+school+"'},");
				}
				if(feeId!=null){
					result.append("'feeId':"+JSON.serialize(feeId));
				}else{
					if(feeName!=null){
						result.append("'feeName':{'$regex':'"+feeName+"'}");
					}
				}
			}
			if(startDate!=null || endDate!= null){
				result.append(",'payDate':{");
				if(startDate!=null){
					result.append("'$gte':"+JSON.serialize(startDate)+",");
				}
				if(endDate!=null){
					result.append("'$lte':"+JSON.serialize(endDate));
				}
				result.append("}");
			}
			result.append("}},");
			logger.debug("build criteria string : {}",result);
			return result.toString();
		}else{
			return "";
		}
	}
	private String buildGroup(String[] fields){
		String returnValue = "{'$group':{'_id':'$_id','money':{'$last':'$money'},'payTotal':{'$sum':'$money.money'}%s}}";
		StringBuilder result = new StringBuilder();
		for(String field : fields){
			result.append(",'"+field+"':{'$last':'$"+field+"'}");
		}
		String formatStr = String.format(returnValue, result);
		logger.debug("build group json string :{}",formatStr);
		return formatStr;
	}
	private String buildProject(String[] fields){
		String returnValue = "{'$project':{'notClear':{'$lt':['$payTotal','$feeMoney']}%s}}";
		StringBuilder result = new StringBuilder();
		for(String field :fields){
			result.append(",'"+field+"':1");
		}
		String formatStr = String.format(returnValue, result);
		logger.debug("build project json string:{}");
		return formatStr;
	}
	/*
	
	
	private GroupOperation doGroup(String[] fields){
		GroupOperation result = group("id");
		for(String field : fields){
			result = result.last(field).as(field);
		}
		return result.push("money").as("money").sum("money").as("payTotal");
	}
	private ProjectionOperation doProject(String[] fields){
		ProjectionOperation projectOperation = project(fields);
		return projectOperation.and("money").as("money").andExpression("feeMoney > payTotal").as("notClear");
	}*/

	public List<Payment> findNotClearPaymentByExpireDate(Date expireDate) {
		// TODO Auto-generated method stub
		Query query = buildQuery(null, null, null, null, null, null, true,null, null, expireDate);
		/*
		
		DBCollection dbCollection = mongoTemplate.getCollection("payment");
		StringBuilder json = new StringBuilder();
		String[] fields = new String[]{"studentId","studentName","klass",
				"school","feeId","feeName","feeMoney","payDate","expireDate"};
		json.append("[");
		json.append("{'$match':{'expireDate':"+JSON.serialize(expireDate)+"}},");
		json.append("{'$unwind':'$money'},");
		json.append(buildGroup(fields)+",");
		json.append(buildProject(fields)+",");
		json.append("{$match:{'notClear':true}}");
		json.append("]");
		logger.debug("final query :{}",json);
		List<DBObject> pipeline = (List<DBObject>)JSON.parse(json.toString());
		AggregationOutput output = dbCollection.aggregate(pipeline);
		Iterable<DBObject> iterable = output.results();
		List<Payment> payments = new ArrayList<Payment>();
		Iterator<DBObject> outputIt = iterable.iterator();
		while(outputIt.hasNext()){
			DBObject dbObject = outputIt.next();
			payments.add(mongoTemplate.getConverter().read(Payment.class, dbObject));
		}*/
		return mongoTemplate.find(query, Payment.class);
	}
}
