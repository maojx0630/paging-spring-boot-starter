package com.github.maojx0630.paging.page;


import com.github.maojx0630.paging.interfaces.PageAbelQuick;

/**
 * 分页信息
 * @author : MaoJiaXing
 */
public class PageAble implements PageAbelQuick {

	private int pageNo;

	private int pageSize = 10;

	private boolean enablePageCount = true;

	private long count;

	private PageAble(int pageNo) {
		this.pageNo = pageNo;
	}

	private PageAble(int pageNo, boolean enablePageCount) {
		this.pageNo = pageNo;
		this.enablePageCount = enablePageCount;
	}

	private PageAble(int pageNo, int pageSize) {
		this.pageNo = pageNo;
		this.pageSize = pageSize;
	}

	private PageAble(int pageNo, int pageSize, boolean enablePageCount) {
		this.pageNo = pageNo;
		this.pageSize = pageSize;
		this.enablePageCount = enablePageCount;
	}

	public static PageAble of(int pageNo) {
		return new PageAble(pageNo);
	}

	public static PageAble of(int pageNo, int pageSize) {
		return new PageAble(pageNo, pageSize);
	}

	public static PageAble of(int pageNo, boolean enablePageCount) {
		return new PageAble(pageNo, enablePageCount);
	}

	public static PageAble of(int pageNo, int pageSize, boolean enablePageCount) {
		return new PageAble(pageNo, pageSize, enablePageCount);
	}

	public static PageAble of(PageAbelQuick pageAbelQuick) {
		return new PageAble(pageAbelQuick.getPageNo(), pageAbelQuick.getPageSize(), pageAbelQuick.isEnablePageCount());
	}

	@Override
	public int getPageNo() {
		return pageNo;
	}

	@Override
	public int getPageSize() {
		return pageSize;
	}

	@Override
	public boolean isEnablePageCount() {
		return enablePageCount;
	}

	public long getCount() {
		return count;
	}

	public void setCount(long count) {
		this.count = count;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
}
