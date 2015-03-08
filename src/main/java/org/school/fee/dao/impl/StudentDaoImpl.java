package org.school.fee.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;



import org.school.fee.dao.StudentDao;
import org.school.fee.models.Student;
import org.school.fee.support.utils.PaginationCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class StudentDaoImpl implements StudentDao{
	
	@Autowired
	MongoTemplate mongoTemplate;

	private Query createFindQuery(String keyword,Integer sex, Integer ageStart, Integer ageEnd){
		Query query = new Query();
		if(ageStart!=null || ageEnd!=null){
			List<Criteria> criterias = new ArrayList<Criteria>();
			if(ageStart!=null){
				criterias.add(Criteria.where("age").gte(ageStart));
			}
			if(ageEnd != null){
				criterias.add(Criteria.where("age").lte(ageEnd));
			}
			query.addCriteria(new Criteria().andOperator(criterias.toArray(new Criteria[criterias.size()])));
		}
		if(keyword!=null){
			Pattern pattern = Pattern.compile("^.*"+keyword+".*$");
			List<Criteria> criterias = new ArrayList<Criteria>();
			criterias.add(Criteria.where("name").regex(pattern));
			criterias.add(Criteria.where("fatherName").regex(pattern));
			criterias.add(Criteria.where("motherName").regex(pattern));
			criterias.add(Criteria.where("klass").regex(pattern));
			criterias.add(Criteria.where("school").regex(pattern));
			criterias.add(Criteria.where("phone").regex(pattern));
			query.addCriteria(new Criteria().orOperator(criterias.toArray(new Criteria[criterias.size()])));
		}
		if(sex!=null){
			query.addCriteria(Criteria.where("sex").is(sex));
		}
		return query;
	}
	
	
	public List<Student> findStudent(PaginationCriteria page, String keyword,
			Integer sex, Integer ageStart, Integer ageEnd, String orderBy,
			String order) {
		// TODO Auto-generated method stub
		Query query = createFindQuery(keyword, sex, ageStart, ageEnd);
		query.skip(page.getStartRow());
		query.limit(page.getPageSize());
		if(orderBy!=null){
			if(order == null) {
				order = "asc";
			}
			query.with(new Sort(Direction.fromString(order),orderBy));
		}
		return mongoTemplate.find(query, Student.class);
	}

	@Override
	public long CountStudent(String keyword, Integer sex, Integer ageStart,
			Integer ageEnd) {
		Query query = createFindQuery(keyword, sex, ageStart, ageEnd);
		return mongoTemplate.count(query, Student.class);
	}
}
