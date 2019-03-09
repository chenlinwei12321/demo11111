package cn.xhzy.crawler.cookie.sina.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Service;

import cn.xhzy.crawler.cookie.common.dao.AbstractCookieDaoSupport;
import cn.xhzy.crawler.cookie.sina.dao.SysCookieDao;
import cn.xhzy.crawler.cookie.sina.entity.SysCookie;

@Service("sysCookieDao")
public class SysCookieDaoImpl extends AbstractCookieDaoSupport<SysCookie> implements SysCookieDao {

	@Override
    public void updatePoolStatusByApp(String appKey) {
		String sql = "update xhzy_weibo_cookie set app_key = null where app_key = ?";

		List<Object> params = new ArrayList<Object>();
		params.add(appKey);

		getJdbcTemplateCookie().update(sql, params.toArray());
	}

	@Override
    public int deleteSysCookie(Integer id) {
		String sql = "delete from xhzy_weibo_cookie where id = ?";

		List<Object> params = new ArrayList<Object>();
		params.add(id);

		return getJdbcTemplateCookie().update(sql, params.toArray());
	}

	@Override
    public List<SysCookie> findPageSysCookies(int pageSize) {
		String sql = "select id, cookies from xhzy_weibo_cookie where app_key is null order by create_time asc limit ? for update";

		List<Object> params = new ArrayList<Object>();
		params.add(pageSize);

		final List<SysCookie> cookieList = new ArrayList<SysCookie>();
		getJdbcTemplateCookie().query(sql, params.toArray(), new RowCallbackHandler() {
			@Override
            public void processRow(ResultSet rs) throws SQLException {
				SysCookie cookie = new SysCookie();
				cookie.setId(rs.getInt("id"));
				cookie.setCookies(rs.getString("cookies"));
				cookieList.add(cookie);
			}
		});

		return cookieList;
	}

	@Override
    public void batchUpdateByAppKey(String ids, String appKey) {
		String sql = "update xhzy_weibo_cookie set app_key = ? where id in(" + ids + ")";

		List<Object> params = new ArrayList<Object>();
		params.add(appKey);

		getJdbcTemplateCookie().update(sql, params.toArray());
	}

}
