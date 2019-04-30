package com.github.maojx0630.page;

import com.github.maojx0630.page.page.PageInterceptor;
import com.github.maojx0630.page.page.QueryInterceptor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author : MaoJiaXing
 */
@Configuration
public class PageStarterConfig {

	@Bean
	@ConditionalOnMissingBean(PageInterceptor.class)
	public PageInterceptor getPageInterceptor(){
		return new PageInterceptor();
	}

	@Bean
	@ConditionalOnMissingBean(QueryInterceptor.class)
	public QueryInterceptor getQueryInterceptor(){
		return new QueryInterceptor();
	}
}
