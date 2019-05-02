package com.github.maojx0630.paging.page;

import com.github.maojx0630.paging.interfaces.EnablePage;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.regex.Pattern;

/**
 * @author MaoJiaXing
 */
class PageRequestUtils {

	private static final Pattern PATTERN = Pattern.compile("^\\+?[1-9][0-9]*$");

	static PageAble getPageAbleById(String id) {
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
