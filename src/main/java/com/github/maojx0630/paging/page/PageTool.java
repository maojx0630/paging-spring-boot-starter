package com.github.maojx0630.paging.page;

import com.github.maojx0630.paging.interfaces.PageAbelQuick;
import org.apache.ibatis.binding.MapperMethod;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.mapping.ResultMapping;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author MaoJiaXing
 */
class PageTool {

	private static final String COUNT = "_COUNT";
	private static final List<ResultMapping> EMPTY = new ArrayList<>(0);

	private static final Map<String,MappedStatement> map=new ConcurrentHashMap<>();

	static MappedStatement getCountMappedStatement(MappedStatement ms) {
		String id=ms.getId()+COUNT;
		if(map.containsKey(id)){
			return map.get(id);
		}
		MappedStatement.Builder builder = new MappedStatement.Builder(ms.getConfiguration(), id, ms.getSqlSource(), ms.getSqlCommandType());
		builder.resource(ms.getResource());
		builder.fetchSize(ms.getFetchSize());
		builder.statementType(ms.getStatementType());
		builder.keyGenerator(ms.getKeyGenerator());
		if (ms.getKeyProperties() != null && ms.getKeyProperties().length != 0) {
			StringBuilder keyProperties = new StringBuilder();
			for (String keyProperty : ms.getKeyProperties()) {
				keyProperties.append(keyProperty).append(",");
			}
			keyProperties.delete(keyProperties.length() - 1, keyProperties.length());
			builder.keyProperty(keyProperties.toString());
		}
		builder.timeout(ms.getTimeout());
		builder.parameterMap(ms.getParameterMap());
		List<ResultMap> resultMaps = new ArrayList<>();
		ResultMap resultMap = new ResultMap.Builder(ms.getConfiguration(), ms.getId(), Long.class, EMPTY).build();
		resultMaps.add(resultMap);
		builder.resultMaps(resultMaps);
		builder.resultSetType(ms.getResultSetType());
		builder.cache(ms.getCache());
		builder.flushCacheRequired(ms.isFlushCacheRequired());
		builder.useCache(ms.isUseCache());
		MappedStatement countMs=builder.build();
		map.put(id,countMs);
		return countMs;
	}


	private static Field getField(Object obj) {
		Field field = null;
		for (Class<?> clazz = obj.getClass(); clazz != Object.class; clazz = clazz.getSuperclass()) {
			try {
				field = clazz.getDeclaredField("sql");
				break;
			} catch (NoSuchFieldException ignored) {
			}
		}
		return field;
	}

	static void setFieldValue(Object obj, String fieldValue) {
		Field field = getField(obj);
		if (field != null) {
			try {
				field.setAccessible(true);
				field.set(obj, fieldValue);
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}
		}
	}


	static PageAble getPageAbleByParamMap(MapperMethod.ParamMap map) {
		for (Object o : map.values()) {
			if (o instanceof PageAble) {
				return (PageAble) o;
			} else if (o instanceof PageAbelQuick) {
				return PageAble.of((PageAbelQuick) o);

			}
		}
		return null;
	}

}
