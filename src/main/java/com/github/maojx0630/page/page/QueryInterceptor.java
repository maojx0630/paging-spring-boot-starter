package com.github.maojx0630.page.page;

import org.apache.ibatis.binding.MapperMethod;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;
import java.util.Properties;
import java.util.regex.Pattern;


/**
 * @author : MaoJiaXing
 */
@Component
@Intercepts({@Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class,
		RowBounds.class, ResultHandler.class})})
public class QueryInterceptor implements Interceptor {

	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		befor(invocation);
		Object object = invocation.proceed();
		PageAble pageAble = PageUtils.getCount();
		if (pageAble != null && object instanceof List) {
			return new PageList((Collection) object, pageAble);
		}
		return object;
	}

	@Override
	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}

	@Override
	public void setProperties(Properties properties) {
	}

	private void befor(Invocation invocation) {
		PageAble pageAble = PageUtils.getNotClearAbel();
		if (pageAble != null) {
			return;
		}
		Object parameter = invocation.getArgs()[1];
		if (parameter instanceof MapperMethod.ParamMap) {
			pageAble= getPageAbleByParamMap((MapperMethod.ParamMap) parameter);
		}else if(parameter instanceof PageAble){
			pageAble= (PageAble) parameter;
		}else if(parameter instanceof PageAbelQuick){
			pageAble=PageAble.of((PageAbelQuick) parameter);
		}
		if(pageAble == null){
			MappedStatement ms = (MappedStatement) invocation.getArgs()[0];
			pageAble=getPageAbleById(ms.getId());
		}
		if(pageAble!=null){
			PageUtils.start(pageAble);
		}
	}


	private static PageAble getPageAbleById(String id) {
		String className = id.substring(0, id.lastIndexOf("."));
		String methedName = id.substring(id.lastIndexOf(".") + 1);
		Method[] ms = new Method[0];
		try {
			ms = Class.forName(className).getMethods();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		for (Method m : ms) {
			if (m.getName().equals(methedName)) {
				EnablePage annotation = m.getAnnotation(EnablePage.class);
				if (annotation != null) {
					return getPageAbleByAnnotation(annotation);
				}
				return null;
			}
		}
		return null;
	}

	private static PageAble getPageAbleByParamMap(MapperMethod.ParamMap map) {
		for (Object o : map.entrySet()) {
			if (o instanceof PageAble) {
				return (PageAble) o;
			} else if (o instanceof PageAbelQuick) {
				return PageAble.of((PageAbelQuick) o);

			}
		}
		return null;
	}

	private static final Pattern PATTERN = Pattern.compile("^\\+?[1-9][0-9]*$");

	private static PageAble getPageAbleByAnnotation(EnablePage enablePage) {
		ServletRequestAttributes servletRequestAttributes =
				(ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = servletRequestAttributes.getRequest();

		int pageSize = enablePage.pageSizeInt();
		if (pageSize == 0) {
			String size = request.getParameter(enablePage.pageSize());
			if (size != null && PATTERN.matcher(size).matches()) {
				pageSize = Integer.parseInt(size);
			}
		}
		int pageNo = 1;
		String no = request.getParameter(enablePage.pageNo());
		if (no != null && PATTERN.matcher(no).matches()) {
			pageNo = Integer.parseInt(no);
		}
		if (pageSize == 0) {
			return PageAble.of(pageNo, enablePage.isEnablePageCount());
		} else {
			return PageAble.of(pageNo, pageSize, enablePage.isEnablePageCount());
		}
	}
}
