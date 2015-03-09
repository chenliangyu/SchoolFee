package org.school.fee.support.utils;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;

public class PageUtils {
	public static Pageable buildPageRequest(Integer page,Integer pageSize,String orderBy,String order){
		if(pageSize == null){
			pageSize = Constants.pageSize;
		}
		if(page == null){
			page = 0;
		}
		if(orderBy!=null){
			if(order == null){
				order = "desc";
			}
			return new PageRequest(page,pageSize,Direction.fromString(order),orderBy);
		}else{
			return new PageRequest(page,pageSize);
		}
	}
}
