package com.github.maojx0630.page.page;

import java.util.List;


/**
 * @author: MaoJiaXing
 * @date: 2019-04-28 16:44
 * @description: 分页工具类
 */
public class PageUtils {

	private static final ThreadLocal<PageAble> PAGE_ABLE_THREAD_LOCAL = new ThreadLocal<>();

	private static final ThreadLocal<PageAble> COUNT = new ThreadLocal<>();

	static PageAble getAbel() {
		PageAble pageAble = PAGE_ABLE_THREAD_LOCAL.get();
		PAGE_ABLE_THREAD_LOCAL.remove();
		return pageAble;
	}

	static PageAble getNotClearAbel() {
		return PAGE_ABLE_THREAD_LOCAL.get();
	}

	static PageAble getCount() {
		PageAble pageAble = COUNT.get();
		COUNT.remove();
		return pageAble;
	}

	static void setCount(PageAble pageAble) {
		COUNT.set(pageAble);
	}

	public static void clear() {
		PAGE_ABLE_THREAD_LOCAL.remove();
		COUNT.remove();
	}

	public static void start(PageAble pageAble) {
		PAGE_ABLE_THREAD_LOCAL.set(pageAble);
	}

	public static void start(int pageNo) {
		PAGE_ABLE_THREAD_LOCAL.set(PageAble.of(pageNo, false));
	}

	public static void start(int pageNo, int pageSize) {
		PAGE_ABLE_THREAD_LOCAL.set(PageAble.of(pageNo, pageSize, false));
	}

	public static void start(int pageNo, boolean enablePageCount) {
		PAGE_ABLE_THREAD_LOCAL.set(PageAble.of(pageNo, enablePageCount));
	}

	public static void start(int pageNo, int pageSize, boolean enablePageCount) {
		PAGE_ABLE_THREAD_LOCAL.set(PageAble.of(pageNo, pageSize, enablePageCount));
	}

	public static void start(PageAbelQuick pageAbelQuick) {
		PAGE_ABLE_THREAD_LOCAL.set(PageAble.of(pageAbelQuick.getPageNo(), pageAbelQuick.getPageSize(),
				pageAbelQuick.isEnablePageCount()));
	}

	public static <T> Page<T> get(List<T> results) {
		return new Page<>(results);
	}

}
