package com.github.maojx0630.paging.interfaces;


import com.github.maojx0630.paging.page.PageAble;

/**
 * @author maojiaxing
 */
public interface SqlCountAndPaging {

	/**
	 * @author maojiaxing
	 * @param sql 原sql
	 * @return 获取总页sql
	 */
	String getCountSql(String sql);

	/**
	 * @author maojiaxing
	 * @param sql 原sql
	 * @param pageAble 分页参数
	 * @return 分页sql
	 */
	String getPagingSql(String sql, PageAble pageAble);
}
