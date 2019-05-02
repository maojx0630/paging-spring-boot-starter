package com.github.maojx0630.paging;

import com.github.maojx0630.paging.interfaces.SqlCountAndPaging;
import com.github.maojx0630.paging.page.DefaultMysqlSqlCountAndPaging;
import com.github.maojx0630.paging.page.QueryInterceptor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * @author : MaoJiaXing
 */
@Configuration
public class PageStarterConfig {

	@Bean
	@ConditionalOnMissingBean(SqlCountAndPaging.class)
	public SqlCountAndPaging getSqlCountAndPaging(){
		return new DefaultMysqlSqlCountAndPaging();
	}

	@Bean
	@ConditionalOnMissingBean(QueryInterceptor.class)
	public QueryInterceptor getQueryInterceptor(SqlCountAndPaging sqlCountAndPaging){
		return new QueryInterceptor(sqlCountAndPaging);
	}

}
