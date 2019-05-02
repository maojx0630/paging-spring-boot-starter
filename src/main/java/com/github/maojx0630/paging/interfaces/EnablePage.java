package com.github.maojx0630.paging.interfaces;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 开启分页注解，加在mapper方法上
 * @author : MaoJiaXing
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface EnablePage {

	/**
	 * 从request中获取size的name
	 * @return request中分页长度参数名
	 */
	String pageSize() default "pageSize";

	/**
	 * 从request中获取no的name
	 * @return request中第几页的参数名字
	 */
	String pageNo() default "pageNo";

	/**
	 * 是否获取总数
	 * @return 是否需要获取总数 默认为true获取
	 */
	boolean isEnablePageCount() default true;

	/**
	 * 手动设置size优先级高于request获取
	 * @return 手动设置的分页长度会覆盖参数中的
	 */
	int pageSizeInt() default 0;
}
