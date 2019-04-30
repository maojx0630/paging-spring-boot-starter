package com.github.maojx630.page.page;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author: MaoJiaXing
 * @date: 2019-04-29 08:37
 * @description:
 */
class PageList<T> extends ArrayList<T> {

	private PageAble pageAble;

	public PageList() {
		super();
	}

	PageList(Collection<? extends T> c, PageAble pageAble) {
		super(c);
		this.pageAble=pageAble;
	}

	PageAble getPageAble() {
		return pageAble;
	}

	public void setPageAble(PageAble pageAble) {
		this.pageAble = pageAble;
	}
}
