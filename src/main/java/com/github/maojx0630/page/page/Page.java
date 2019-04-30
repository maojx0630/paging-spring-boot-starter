package com.github.maojx0630.page.page;

import java.util.List;


/**
 * 对分页的基本数据进行一个简单的封装
 * @author : MaoJiaXing
 */
public class Page<T> {
	/**
	 * 第N页
	 */
	private int pageNo;
	/**
	 * 每页N条
	 */
	private int pageSize;
	/**
	 * 总条数
	 */
	private int totalRecord;
	/**
	 * 总页数
	 */
	private int totalPage;
	/**
	 * 是否为空
	 */
	private boolean empty;
	/**
	 * 是否为最后一页
	 */
	private boolean end;

	private List<T> results;

	Page(List<T> results) {
		if(results instanceof PageList){
			PageAble pageAble=((PageList<T>) results).getPageAble();
			this.pageNo = pageAble.getPageNo();
			this.pageSize = pageAble.getPageSize();
			this.totalRecord = pageAble.getCount();
			this.results = results;
			this.setTotalPage(totalRecord % pageSize == 0 ? totalRecord / pageSize : totalRecord / pageSize + 1);
			empty = results == null || results.isEmpty();
			end = pageNo == totalPage;
		}else {
			throw new RuntimeException("did not open page or enablePageCount not true");
		}

	}


	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getTotalRecord() {
		return totalRecord;
	}

	public void setTotalRecord(int totalRecord) {
		this.totalRecord = totalRecord;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public boolean isEmpty() {
		return empty;
	}

	public void setEmpty(boolean empty) {
		this.empty = empty;
	}

	public boolean isEnd() {
		return end;
	}

	public void setEnd(boolean end) {
		this.end = end;
	}

	public List<T> getResults() {
		return results;
	}

	public void setResults(List<T> results) {
		this.results = results;
	}
}
