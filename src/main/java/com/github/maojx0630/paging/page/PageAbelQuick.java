package com.github.maojx0630.paging.page;


/**
 * 可以用来快速构造PageAble对象
 *
 * @author : MaoJiaXing
 */
public interface PageAbelQuick {

	/**
	 * 获取页码
	 * @author MaoJiaXing
	 * @return int 页码数值
	 */
	int getPageNo();

	/**
	 * 获取每页长度
	 * @author MaoJiaXing
	 * @return int 每页长度数值
	 */
	int getPageSize();

	/**
	 * 是否获取总数
	 * @author MaoJiaXing
	 * @return boolean  是否开启获取总数
	 */
	default boolean isEnablePageCount() {
		return true;
	}
}
