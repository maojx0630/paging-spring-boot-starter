package com.github.maojx630.page.page;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author: MaoJiaXing
 * @date: 2019-04-28 16:43
 * @description: 开启分页注解，加在mapper方法上
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface EnablePage {

	/**
	 * 从request中获取size的name
	 */
	String pageSize() default "pageSize";

	/**
	 * 从request中获取no的name
	 */
	String pageNo() default "pageNo";

	/**
	 * 是否获取总数
	 */
	boolean isEnablePageCount() default true;

	/**
	 * 手动设置size优先级高于request获取
	 */
	int pageSizeInt() default 0;
}
