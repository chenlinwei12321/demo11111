package cn.xhzy.crawler.cookie.sina.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.xhzy.crawler.cookie.sina.dao.SysCookieDao;
import cn.xhzy.crawler.cookie.sina.entity.SysCookie;
import cn.xhzy.crawler.cookie.sina.service.SysCookieService;

@Transactional(value = "main")
@Service("sysCookieService")
public class SysCookieServiceImpl implements SysCookieService {

	@Autowired
	private SysCookieDao sysCookieDao;

	@Override
    public int deleteSysCookie(SysCookie cookie) {
		return sysCookieDao.deleteSysCookie(cookie.getId());
	}

	@Override
    public void updatePoolStatusByApp(String appKey) {
		sysCookieDao.updatePoolStatusByApp(appKey);
	}

	@Override
    public List<SysCookie> findPageSysCookies(String appKey, int pageSize) {
		List<SysCookie> cookies = sysCookieDao.findPageSysCookies(pageSize);

		if (cookies == null || cookies.isEmpty()) {
			return null;
		}

		StringBuffer ids = new StringBuffer();

		for (SysCookie cookie : cookies) {
			ids.append(cookie.getId());
			ids.append(",");
		}

		if (ids.length() > 0) {
			ids.deleteCharAt(ids.length() - 1);
			sysCookieDao.batchUpdateByAppKey(ids.toString(), appKey);
		}

		return cookies;
	}

}
