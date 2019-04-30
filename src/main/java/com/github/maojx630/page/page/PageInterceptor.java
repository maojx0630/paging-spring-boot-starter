package com.github.maojx630.page.page;

import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.scripting.defaults.DefaultParameterHandler;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;


/**
 * @author: MaoJiaXing
 * @date: 2019-04-28 16:43
 * @description: mybatis拦截器用户修改分页以及获取总数
 */
@Intercepts({@Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class, Integer.class})})
@Component
public class PageInterceptor implements Interceptor {

	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		PageAble pageAble=PageUtils.getAbel();
		if(pageAble==null){
			return invocation.proceed();
		}
		RoutingStatementHandler handler = (RoutingStatementHandler) invocation.getTarget();
		StatementHandler delegate = (StatementHandler) getFieldValue(handler, "delegate");
		BoundSql boundSql = delegate.getBoundSql();
		MappedStatement mappedStatement = (MappedStatement) getFieldValue(delegate, "mappedStatement");
		Connection connection = (Connection) invocation.getArgs()[0];
		pageInterceptor(pageAble, boundSql, mappedStatement, connection);
		return invocation.proceed();
	}

	private void pageInterceptor(PageAble pageAble, BoundSql boundSql, MappedStatement mappedStatement,
	                             Connection connection) {
		if (pageAble.isEnablePageCount()) {
			PageUtils.setCount(this.setTotalRecord(pageAble, boundSql, mappedStatement, connection));
		}
		String pageSql = this.getPageSql(pageAble, new StringBuffer(boundSql.getSql()));
		setFieldValue(boundSql, pageSql);
	}

	private PageAble setTotalRecord(PageAble pageAble, BoundSql boundSql, MappedStatement mappedStatement,
	                                Connection connection) {
		String sql = boundSql.getSql();
		String countSql = this.getCountSql(sql);
		List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
		BoundSql countBoundSql = new BoundSql(mappedStatement.getConfiguration(), countSql, parameterMappings,
				boundSql.getParameterObject());
		ParameterHandler parameterHandler = new DefaultParameterHandler(mappedStatement, boundSql.getParameterObject()
				, countBoundSql);
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = connection.prepareStatement(countSql);
			parameterHandler.setParameters(pstmt);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				int totalRecord = rs.getInt(1);
				pageAble.setCount(totalRecord);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return pageAble;
	}

	private String getCountSql(String sql) {
		int index = sql.toLowerCase().indexOf("from");
		return "select count(*) " + sql.substring(index);
	}

	private String getPageSql(PageAble pageAble, StringBuffer sqlBuffer) {
		int offset = (pageAble.getPageNo() - 1) * pageAble.getPageSize();
		sqlBuffer.append(" limit ").append(offset).append(",").append(pageAble.getPageSize());
		return sqlBuffer.toString();
	}

	@Override
	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}

	@Override
	public void setProperties(Properties properties) {
	}


	private static Object getFieldValue(Object obj, String fieldName) {
		Object result = null;
		Field field = getField(obj, fieldName);
		if (field != null) {
			field.setAccessible(true);
			try {
				result = field.get(obj);
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	private static Field getField(Object obj, String fieldName) {
		Field field = null;
		for (Class<?> clazz = obj.getClass(); clazz != Object.class; clazz = clazz.getSuperclass()) {
			try {
				field = clazz.getDeclaredField(fieldName);
				break;
			} catch (NoSuchFieldException ignored) {
			}
		}
		return field;
	}

	private static void setFieldValue(Object obj, String fieldValue) {
		Field field = getField(obj, "sql");
		if (field != null) {
			try {
				field.setAccessible(true);
				field.set(obj, fieldValue);
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}
		}
	}


}
