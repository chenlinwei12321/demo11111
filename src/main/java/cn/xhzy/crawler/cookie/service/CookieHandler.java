package cn.xhzy.crawler.cookie.service;

import java.util.List;

import cn.xhzy.crawler.cookie.sina.entity.SysCookie;

/**
 * Cookie Handler
 */
public interface CookieHandler {

	public void initPool(String appKey);

	public List<SysCookie> findCookies(String appKey, int poolSize);

	public void invalidCookie(SysCookie cookie);

}
