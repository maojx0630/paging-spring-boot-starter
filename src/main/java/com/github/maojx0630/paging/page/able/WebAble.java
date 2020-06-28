package com.github.maojx0630.paging.page.able;

import com.github.maojx0630.paging.interfaces.EnablePage;
import com.github.maojx0630.paging.interfaces.PageAbelQuick;
import com.github.maojx0630.paging.page.PageAble;
import com.github.maojx0630.paging.page.PageTool;
import org.apache.ibatis.binding.MapperMethod;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.regex.Pattern;

/**
 * @author: MaoJiaXing
 * date: 2019-05-14 16:49
 * description:
 */
public class WebAble implements AbleInterface{

	@Override
	public PageAble getPageAble(PageAble pageAble, Object parameter, String id) {
		if (pageAble == null) {
			if (parameter instanceof MapperMethod.ParamMap) {
				pageAble = PageTool.getPageAbleByParamMap((MapperMethod.ParamMap) parameter);
			} else if (parameter instanceof PageAble) {
				pageAble = (PageAble) parameter;
			} else if (parameter instanceof PageAbelQuick) {
				pageAble = PageAble.of((PageAbelQuick) parameter);
			}
			if (pageAble == null) {
				pageAble = getPageAbleById(id);
			}
		}
		if(pageAble != null){
			if(pageAble.getPageNo()<1){
				pageAble.setPageNo(1);
			}
			if(pageAble.getPageSize()<1){
				pageAble.setPageSize(10);
			}
		}
		return pageAble;
	}

	private static final Pattern PATTERN = Pattern.compile("^\\+?[1-9][0-9]*$");

	private PageAble getPageAbleById(String id) {
		String className = id.substring(0, id.lastIndexOf("."));
		String methodName = id.substring(id.lastIndexOf(".") + 1);
		Method[] ms = new Method[0];
		try {
			ms = Class.forName(className).getMethods();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		for (Method m : ms) {
			if (m.getName().equals(methodName)) {
				EnablePage enablePage = m.getAnnotation(EnablePage.class);
				if (enablePage != null) {
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
				return null;
			}
		}
		return null;
	}
}
