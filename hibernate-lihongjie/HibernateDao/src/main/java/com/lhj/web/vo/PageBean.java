package com.lhj.web.vo;

import java.util.ArrayList;
import java.util.List;

public class PageBean {
	
	private int totalRows; // 记录查询的全部结果数量
	private int totalPages; // 自动计算总的分页数量
	private int currentPage; // 当前页数
	private int pageSize; // 每页显示的记录个数
	private List data = new ArrayList();  // 每页显示的数据
	
	public int getTotalRows() {
		return totalRows;
	}
	public void setTotalRows(int totalRows) {
		this.totalRows = totalRows;
	}
	public int getTotalPages() {
		/*
		 * 
		 * 分页算法：
		 * if(总行数%每页显示个数==0){
		 *     总行数/每页显示个数
		 * }else{
		 * 	   总行数/每页显示个数+1
		 * }
		 * */ 
		return totalRows%pageSize==0?totalRows/pageSize:totalRows/pageSize+1;
	}
	
	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public List getData() {
		return data;
	}
	public void setData(List data) {
		this.data = data;
	}
	
	
}
