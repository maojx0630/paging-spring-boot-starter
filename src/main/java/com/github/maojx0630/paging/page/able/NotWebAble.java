package com.github.maojx0630.paging.page.able;

import com.github.maojx0630.paging.interfaces.PageAbelQuick;
import com.github.maojx0630.paging.page.PageAble;
import com.github.maojx0630.paging.page.PageTool;
import org.apache.ibatis.binding.MapperMethod;

/**
 * @author: MaoJiaXing
 * date: 2019-05-14 16:55
 * description:
 */
public class NotWebAble implements AbleInterface{

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
}
