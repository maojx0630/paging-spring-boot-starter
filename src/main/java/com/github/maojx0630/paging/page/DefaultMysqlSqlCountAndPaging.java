package com.github.maojx0630.paging.page;


import com.github.maojx0630.paging.interfaces.SqlCountAndPaging;

/**
 * @author MaoJiaXing
 */
public class DefaultMysqlSqlCountAndPaging implements SqlCountAndPaging {

	@Override
	public String getCountSql(String sql) {
		return "select count(*) " + sql.substring(sql.toLowerCase().indexOf("from"));
	}

	@Override
	public String getPagingSql(String sql, PageAble pageAble) {
		return sql+" limit "+(pageAble.getPageNo() - 1) * pageAble.getPageSize()+","+pageAble.getPageSize();
	}
}
