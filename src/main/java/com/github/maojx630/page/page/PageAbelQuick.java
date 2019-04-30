package com.github.maojx630.page.page;


/**
 * @author: MaoJiaXing
 * @date: 2019-04-28 16:43
 * @description: 可以用来快速构造PageAble对象
 */
public interface PageAbelQuick {

	/**
	* 获取页码
	* <br/>
	* @return int
	* @author MaoJiaXing
	* @date 2019-04-29 09:02
	*/
	int getPageNo();

	/**
	* 获取每页长度
	* <br/>
	* @return int
	* @author MaoJiaXing
	* @date 2019-04-29 09:01
	*/
	int getPageSize();

	/**
	* 是否获取总数
	* <br/>
	* @return boolean
	* @author MaoJiaXing
	* @date 2019-04-29 09:01
	*/
	default boolean isEnablePageCount() {
		return true;
	}
}
