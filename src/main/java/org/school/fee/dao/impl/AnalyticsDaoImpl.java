package org.school.fee.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.bson.types.ObjectId;
import org.school.fee.dao.AnalyticsDao;
import org.school.fee.models.PayAnalytics;
import org.school.fee.support.enums.PayMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.mongodb.AggregationOutput;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;

import freemarker.template.utility.StringUtil;

@Repository
public class AnalyticsDaoImpl implements AnalyticsDao {
	private Logger logger = LoggerFactory.getLogger(AnalyticsDaoImpl.class);
	@Autowired
	MongoTemplate mongoTemplate;
	
	public Page<PayAnalytics> listAnalytics(Pageable pageable,ObjectId feeId,String feeName,
			String studentName,String klass,String school,Boolean notClear,PayMethod payMethod,
			Date startDate,Date endDate) {
		// TODO Auto-generated method stub
		DBCollection dbCollection = mongoTemplate.getCollection("payment");
		StringBuilder json = new StringBuilder();
		json.append("[");
		json.append(buildMatch(feeId,feeName,studentName,klass,school,notClear,payMethod,startDate,endDate));
		json.append("{'$unwind':'$money'},");
		json.append("{'$group':{");
			json.append("'_id':{'feeId':'$feeId','studentId':'$studentId'},");
			json.append("'klass':{'$last':'$klass'},");
			json.append("'school':{'$last':'$school'},");
			json.append("'feeName':{'$last':'$feeName'},");
			json.append("'payMethod':{'$last':'$payMethod'},");
			json.append("'feeStartDate':{'$last':'$feeStartDate'},");
			json.append("'feeEndDate':{'$last':'$feeEndDate'},");
			json.append("'payTotal':{'$sum':'$money.money'},");
			json.append("'total':{'$sum':'$feeMoney'},");
			json.append("'createDate':{'$last':'$createDate'}");
		json.append("}},");
		json.append("{'$group':{");
			json.append("'_id':{'feeId':'$_id.feeId','school':'$school','klass':'$klass'},");
			json.append("'feeName':{'$last':'$feeName'},");
			json.append("'feeStartDate':{'$last':'$feeStartDate'},");
			json.append("'feeEndDate':{'$last':'$feeEndDate'},");
			json.append("onepayStudentNumber:{$sum:{$cond:{if:{$eq:['$payMethod',0]},then:1,else:0}}},");
			json.append("instalmentStudentNumber:{$sum:{$cond:{if:{$eq:['$payMethod',1]},then:1,else:0}}},");
			json.append("onepayNotClearStudentNumber:{$sum:{$cond:{if:{$and:[{$eq:['$payMethod',0]},{$gt:['$total','$payTotal']}]},then:1,else:0}}},");
			json.append("instalmentNotClearStudentNumber:{$sum:{$cond:{if:{$and:[{$eq:['$payMethod',1]},{$gt:['$total','$payTotal']}]},then:1,else:0}}},");
			json.append("instalmentPayTotal:{$sum:{$cond:{if:{$and:[{$eq:['$payMethod',1]}]},then:'$payTotal',else:0}}},");
			json.append("onepayPayTotal:{$sum:{$cond:{if:{$and:[{$eq:['$payMethod',0]}]},then:'$payTotal',else:0}}},");
			json.append("onepayTotal:{$sum:{$cond:{if:{$and:[{$eq:['$payMethod',0]}]},then:'$total',else:0}}},");
			json.append("instalmentTotal:{$sum:{$cond:{if:{$and:[{$eq:['$payMethod',1]}]},then:'$total',else:0}}},");
			json.append("'createDate':{'$last':'$createDate'}");
		json.append("}},");
		json.append("{'$group':{");
			json.append("'_id':'$_id.feeId',");
			json.append("'feeName':{'$last':'$feeName'},");
			json.append("'startDate':{'$last':'$feeStartDate'},");
			json.append("'endDate':{'$last':'$feeEndDate'},");
			json.append("'result':{'$push':{'klass':'$_id.klass','school':'$_id.school','onepayStudentNumber':'$onepayStudentNumber',");
			json.append("'instalmentStudentNumber':'$instalmentStudentNumber','onepayNotClearStudentNumber':'$onepayNotClearStudentNumber',");
			json.append("'instalmentNotClearStudentNumber':'$instalmentNotClearStudentNumber','onepayPayTotal':'$onepayPayTotal',");
			json.append("'onepayTotal':'$onepayTotal','instalmentPayTotal':'$instalmentPayTotal','instalmentTotal':'$instalmentTotal'}},");
			json.append("'createDate':{'$last':'$createDate'}");
		json.append("}},");
		json.append("{$sort:{'createDate':-1,payTotal:-1}},");
		json.append("{$skip:"+pageable.getOffset()+"},");
		json.append("{$limit:"+pageable.getPageSize()+"}");
		json.append("]");
		logger.debug("find final query:{}",json);
		List<DBObject> pipeline = (List<DBObject>)JSON.parse(json.toString());
		AggregationOutput output = dbCollection.aggregate(pipeline);
		Iterable<DBObject> iterable = output.results();
		List<PayAnalytics> payAnalyticses = new ArrayList<PayAnalytics>();
		Iterator<DBObject> outputIt = iterable.iterator();
		while(outputIt.hasNext()){
			DBObject dbObject = outputIt.next();
			payAnalyticses.add(mongoTemplate.getConverter().read(PayAnalytics.class, dbObject));
		}
		long total = countAnalytics();
		return new PageImpl<PayAnalytics>(payAnalyticses,pageable,total);
	}
	private Object buildMatch(ObjectId feeId, String feeName,String studentName, String klass,
			String school, Boolean notClear, PayMethod payMethod,Date startDate, Date endDate) {
		Boolean hasFilter = false;
		StringBuilder json = new StringBuilder();
		json.append("{'$match':{");
		if(feeId!=null){
			json.append("feeId:"+JSON.serialize(feeId));
			hasFilter = true;
		}
		if(feeName!=null && !feeName.equals("")){
			if(hasFilter)json.append(",");
			json.append("'feeName':'"+feeName);
			hasFilter = true;
		}
		if(studentName!=null && !studentName.equals("")){
			if(hasFilter)json.append(",");
			json.append("'studentName':'"+studentName+"'");
			hasFilter = true;
		}
		if(klass!=null && !klass.equals("")){
			if(hasFilter)json.append(",");
			json.append("'klass':'"+klass+"'");
			hasFilter = true;
		}
		if(school!=null && !school.equals("")){
			if(hasFilter)json.append(",");
			json.append("'school':'"+school+"'");
			hasFilter = true;
		}
		if(notClear!=null){
			if(hasFilter)json.append(",");
			json.append("'payResults.status':"+ (notClear?1:0));
			hasFilter = true;
		}
		if(payMethod!=null){
			if(hasFilter)json.append(",");
			json.append("'payMethod':"+payMethod.ordinal());
			hasFilter = true;
		}
		if(startDate!=null || endDate!=null){
			if(hasFilter)json.append(",");
			json.append("'payResults.expireDate':{");
			if(startDate!=null){
				json.append("'$gte':"+JSON.serialize(startDate));
				hasFilter = true;
			}
			if(endDate!=null){
				if(hasFilter)json.append(",");
				json.append("'$lte':"+JSON.serialize(endDate));
				hasFilter = true;
			}
			json.append("}");
		}
		json.append("}},");
		if(hasFilter){
			return json.toString();
		}else{
			return "";
		}
	}
	public long countAnalytics(){
		StringBuilder json = new StringBuilder();
		json.append("[");
		json.append("{$group:{");
			json.append("'_id':{feeId:'$feeId'}");
		json.append("}},");
		json.append("{$group:{");
			json.append("'_id':null,'count':{$sum:1}");
		json.append("}}");
		json.append("]");
		DBCollection dbCollection = mongoTemplate.getCollection("payment");
		logger.debug("count by final query:{}",json);
		List<DBObject> pipeline = (List<DBObject>)JSON.parse(json.toString());
		AggregationOutput output = dbCollection.aggregate(pipeline);
		Iterable<DBObject> iterable = output.results();
		Iterator<DBObject> outputIt = iterable.iterator();
		while(outputIt.hasNext()){
			DBObject dbObject = outputIt.next();
			return (Integer)dbObject.get("count");
		}
		return 0;
	}
}
