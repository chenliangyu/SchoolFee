package org.school.fee.support.utils;

public class PaginationCriteria {
	private int page;
	private int totalCount;
	private int pageSize = 20;
	
	public PaginationCriteria(int pageSize,int totalCount) {
		this.pageSize = pageSize;
		this.totalCount = totalCount;
	}
	
	public PaginationCriteria(int totalCount){
		this.totalCount = totalCount;
	}
	
	public int getTotalPage(){
		return (int)Math.ceil(this.totalCount/this.pageSize);
	}
	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getNextPage() {
		return page<getTotalPage()?++page:getTotalPage();
	}

	public int getPrevPage() {
		return page > 1 ?--page:1;
	}

	public int getStartRow(){
		return this.page == 1?0 :(this.page - 1) * this.pageSize;
	}
	public int getEndRow(){
		int end = this.page * this.pageSize - 1;
		if(end>=totalCount){
			return totalCount;
		}else{
			return end;
		}
	}
}
