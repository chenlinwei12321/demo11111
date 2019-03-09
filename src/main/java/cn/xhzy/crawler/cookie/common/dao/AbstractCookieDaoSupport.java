package cn.xhzy.crawler.cookie.common.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import cn.xhzy.crawler.basic.dao.AbstractBaseDao;


public abstract class AbstractCookieDaoSupport<T> extends AbstractBaseDao<T> {

	@Autowired
	private JdbcTemplate jdbcTemplateCookie;

	protected JdbcTemplate getJdbcTemplateCookie() {
		return jdbcTemplateCookie;
	}
}
