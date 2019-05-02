package com.github.maojx0630.paging.page;

import com.github.maojx0630.paging.interfaces.PageAbelQuick;

import java.util.List;


/**
 * 分页工具类
 * @author : MaoJiaXing
 */
public class PageUtils {

	private static final ThreadLocal<PageAble> PAGE_ABLE_THREAD_LOCAL = new ThreadLocal<>();


	static PageAble getAbel() {
		PageAble pageAble = PAGE_ABLE_THREAD_LOCAL.get();
		PAGE_ABLE_THREAD_LOCAL.remove();
		return pageAble;
	}

	/**
	 * 清除page分页信息
	 */
	public static void clear(){
		PAGE_ABLE_THREAD_LOCAL.remove();
	}

	/**
	 * 开启分页
	 * @param pageAble pageAble对象
	 */
	public static void start(PageAble pageAble) {
		PAGE_ABLE_THREAD_LOCAL.set(pageAble);
	}

	/**
	 * 开启分页
	 * @param pageNo 页码
	 */
	public static void start(int pageNo) {
		PAGE_ABLE_THREAD_LOCAL.set(PageAble.of(pageNo));
	}

	/**
	 * 开启分页
	 * @param pageNo 页码
	 * @param pageSize 页长
	 */
	public static void start(int pageNo, int pageSize) {
		PAGE_ABLE_THREAD_LOCAL.set(PageAble.of(pageNo, pageSize));
	}

	/**
	 * 开启分页
	 * @param pageNo 页码
	 * @param enablePageCount 是否获取总数
	 */
	public static void start(int pageNo, boolean enablePageCount) {
		PAGE_ABLE_THREAD_LOCAL.set(PageAble.of(pageNo, enablePageCount));
	}

	/**
	 * 开启分页
	 * @param pageNo 页码
	 * @param pageSize 页长
	 * @param enablePageCount 是否获取总数
	 */
	public static void start(int pageNo, int pageSize, boolean enablePageCount) {
		PAGE_ABLE_THREAD_LOCAL.set(PageAble.of(pageNo, pageSize, enablePageCount));
	}

	/**
	 * 开启分页
	 * @param pageAbelQuick 实现pageAbelQuick接口对象
	 */
	public static void start(PageAbelQuick pageAbelQuick) {
		PAGE_ABLE_THREAD_LOCAL.set(PageAble.of(pageAbelQuick.getPageNo(), pageAbelQuick.getPageSize(),
				pageAbelQuick.isEnablePageCount()));
	}

	/**
	 * 通过返回值获取总数
	 * @param results dao返回的list
	 * @param <T> 将要获取的类型
	 * @return 返回带有总数等信息的page对象
	 */
	public static <T> Page<T> get(List<T> results) {
		return new Page<>(results);
	}

}
