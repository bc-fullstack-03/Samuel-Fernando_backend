package com.samuelfernando.sysmapparrot.config;

import org.springframework.data.domain.Page;

public class PageableResponse {
	public int pageNumber;
	public int numberOfElements;
	public int size;
	public long totalElements;
	public int totalPages;
	public boolean hasNext;
	public boolean hasPrevious;
	public boolean isFirst;
	public boolean isLast;
	
	public PageableResponse() {
	}
	
	public static PageableResponse of(Page<?> page) {
		PageableResponse response = new PageableResponse();
		
		response.pageNumber = page.getNumber();
		response.numberOfElements = page.getNumberOfElements();
		response.size = page.getSize();
		response.totalElements = page.getTotalElements();
		response.totalPages = page.getTotalPages();
		response.hasNext = page.hasNext();
		response.hasPrevious = page.hasPrevious();
		response.isFirst = page.isFirst();
		response.isLast = page.isLast();
		
		return response;
	}
}
