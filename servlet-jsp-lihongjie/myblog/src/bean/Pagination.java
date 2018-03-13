package bean;

import java.util.List;

/**
 * 分页的对象,使用泛型
 * @author Administrator
 *
 */
public class Pagination<T> {
	//每页的记录数
	private int pageSize = 6;
	//当前页
	private int pageNumber = 1;
	//总记录数
	private int maxElements;
	//当前页的所有记录
	private List<T> pageList;
	//当前页的第一条记录
	private int offset;
	
	
	public Pagination(int pageSize, int pageNumber, int maxElements) {
		super();
		this.pageSize = pageSize;
		this.pageNumber = pageNumber;
		this.maxElements = maxElements;
	}
	public Pagination() {
		super();
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getPageNumber() {
		return pageNumber;
	}
	//修改pageNumber
	//页码，防止溢出     上一页 3,4,5  下一页
	public void setPageNumber(int pageNumber) {
		int total = (int) Math.ceil(maxElements/pageSize);//向上取整
		//页码最大和最小的临界值
		if(pageNumber > total) {
			pageNumber = total;
		}else if (pageNumber <= 1) {
			pageNumber = 1;
		}else {
			this.pageNumber = pageNumber;
		}
	}
	public int getMaxElements() {
		return maxElements;
	}
	public void setMaxElements(int maxElements) {
		this.maxElements = maxElements;
	}
	public List<T> getPageList() {
		return pageList;
	}
	public void setPageList(List<T> pageList) {
		this.pageList = pageList;
	}
	public int getOffset() {
		return offset;
	}
	
	//偏移量:当前页的第一条索引记录
	
	public void setOffset(int offset) {
		if(offset <=0 ) {
			this.offset = 0;
		}else if(offset > maxElements) {
			this.offset = maxElements - 1;
		}else {
			this.offset = offset;
		}
	}
	
	
}
