package org.school.fee.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;







import org.apache.shiro.util.StringUtils;
import org.school.fee.dao.StudentDao;
import org.school.fee.models.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
		if(keyword!=null&& !keyword.equals("")){
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
	
	
	public Page<Student> findStudent(Pageable page, String keyword,
			Integer sex, Integer ageStart, Integer ageEnd) {
		// TODO Auto-generated method stub
		Query query = createFindQuery(keyword, sex, ageStart, ageEnd);
		query.skip(page.getOffset());
		query.limit(page.getPageSize());
		Sort sort = page.getSort();
		if(sort!=null){
			query.with(sort);
		}
		List<Student> students = mongoTemplate.find(query, Student.class);
		long total = CountStudent(keyword, sex, ageStart, ageEnd);
		return new PageImpl<Student>(students,page,total);
	}

	public long CountStudent(String keyword, Integer sex, Integer ageStart,
			Integer ageEnd) {
		Query query = createFindQuery(keyword, sex, ageStart, ageEnd);
		return mongoTemplate.count(query, Student.class);
	}
}
