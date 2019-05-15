package com.github.maojx0630.paging;

import com.github.maojx0630.paging.interfaces.SqlCountAndPaging;
import com.github.maojx0630.paging.page.DefaultMysqlSqlCountAndPaging;
import com.github.maojx0630.paging.page.QueryInterceptor;
import com.github.maojx0630.paging.page.able.AbleInterface;
import com.github.maojx0630.paging.page.able.NotWebAble;
import com.github.maojx0630.paging.page.able.WebAble;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnNotWebApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
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
	public QueryInterceptor getQueryInterceptor(SqlCountAndPaging sqlCountAndPaging, AbleInterface ableInterface){
		return new QueryInterceptor(sqlCountAndPaging,ableInterface);
	}

	@Bean
	@ConditionalOnWebApplication
	@ConditionalOnMissingBean(AbleInterface.class)
	public AbleInterface webAbleInterface(){
		return new WebAble();
	}

	@Bean
	@ConditionalOnNotWebApplication
	@ConditionalOnMissingBean(AbleInterface.class)
	public AbleInterface webNotWebAble(){
		return new NotWebAble();
	}
}
