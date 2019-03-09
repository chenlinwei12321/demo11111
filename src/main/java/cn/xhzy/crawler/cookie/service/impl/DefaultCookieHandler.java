package cn.xhzy.crawler.cookie.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.xhzy.crawler.basic.utils.SpringUtils;
import cn.xhzy.crawler.cookie.service.CookieHandler;
import cn.xhzy.crawler.cookie.sina.entity.SysCookie;
import cn.xhzy.crawler.cookie.sina.service.SysCookieService;

/**
 * Default Cookie Handler
 */
public class DefaultCookieHandler implements CookieHandler {

	private static Logger logger = LoggerFactory.getLogger(DefaultCookieHandler.class);

	private SysCookieService sysCookieService = SpringUtils.getBean("sysCookieService");

	@Override
    public List<SysCookie> findCookies(String appKey, int poolSize) {
		return sysCookieService.findPageSysCookies(appKey, poolSize);
	}

	@Override
    public void invalidCookie(SysCookie cookie) {
		if (cookie == null) {
			return;
		}

		sysCookieService.deleteSysCookie(cookie);

		logger.info("cookie[{}]已经失效...", cookie.getId());
	}

	@Override
    public void initPool(String appKey) {
		sysCookieService.updatePoolStatusByApp(appKey);
	}

}
