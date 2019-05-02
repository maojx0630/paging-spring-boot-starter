package com.github.maojx0630.paging.page;


import com.github.maojx0630.paging.interfaces.PageAbelQuick;
import com.github.maojx0630.paging.interfaces.SqlCountAndPaging;
import org.apache.ibatis.binding.MapperMethod;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.sql.SQLException;
import java.util.List;
import java.util.Properties;


/**
 * @author MaoJiaXing
 */
@Intercepts({@Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}),
		@Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class, CacheKey.class, BoundSql.class})})
public class QueryInterceptor implements Interceptor {

	private SqlCountAndPaging sqlCountAndPaging;

	public QueryInterceptor(SqlCountAndPaging sqlCountAndPaging) {
		this.sqlCountAndPaging = sqlCountAndPaging;
	}

	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		PageAble pageAble = PageUtils.getAbel();
		Object[] args = invocation.getArgs();
		Object parameter = args[1];
		MappedStatement ms = (MappedStatement) args[0];
		RowBounds rowBounds = (RowBounds) args[2];
		ResultHandler resultHandler = (ResultHandler) args[3];
		Executor executor = (Executor) invocation.getTarget();
		CacheKey cacheKey;
		BoundSql boundSql;
		if (args.length == 4) {
			boundSql = ms.getBoundSql(parameter);
			cacheKey = executor.createCacheKey(ms, parameter, rowBounds, boundSql);
		} else {
			cacheKey = (CacheKey) args[4];
			boundSql = (BoundSql) args[5];
		}
		pageAble = getPageAble(pageAble, parameter, ms.getId());
		if (pageAble != null) {
			if (pageAble.isEnablePageCount()) {
				pageAble.setCount(getCount(executor, ms, parameter, resultHandler, boundSql));
			}
			updateSql(boundSql, pageAble);
			List<Object> object = executor.query(ms, parameter, rowBounds, resultHandler, cacheKey, boundSql);
			if (pageAble.isEnablePageCount()) {
				return new PageList<>(object, pageAble);
			}else {
				return object;
			}

		} else {
			return executor.query(ms, parameter, rowBounds, resultHandler, cacheKey, boundSql);
		}
	}

	private PageAble getPageAble(PageAble pageAble, Object parameter, String id) {
		if (pageAble == null) {
			if (parameter instanceof MapperMethod.ParamMap) {
				pageAble = PageTool.getPageAbleByParamMap((MapperMethod.ParamMap) parameter);
			} else if (parameter instanceof PageAble) {
				pageAble = (PageAble) parameter;
			} else if (parameter instanceof PageAbelQuick) {
				pageAble = PageAble.of((PageAbelQuick) parameter);
			}
			if (pageAble == null) {
				pageAble = PageRequestUtils.getPageAbleById(id);
			}
		}
		return pageAble;
	}

	@Override
	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}

	@Override
	public void setProperties(Properties properties) {
	}

	private Long getCount(Executor executor, MappedStatement ms, Object parameter,
	                      ResultHandler resultHandler, BoundSql boundSql) throws SQLException {
		MappedStatement countMs = PageTool.getCountMappedStatement(ms);
		CacheKey countKey = executor.createCacheKey(countMs, parameter, RowBounds.DEFAULT, boundSql);
		BoundSql countBoundSql = new BoundSql(countMs.getConfiguration(), sqlCountAndPaging.getCountSql(boundSql.getSql()), boundSql.getParameterMappings(), parameter);
		Object countResultList = executor.query(countMs, parameter, RowBounds.DEFAULT, resultHandler, countKey, countBoundSql);
		return (Long) ((List) countResultList).get(0);
	}

	private void updateSql(BoundSql boundSql, PageAble pageAble) {
		PageTool.setFieldValue(boundSql, sqlCountAndPaging.getPagingSql(boundSql.getSql(), pageAble));
	}


}
