package cn.xhzy.crawler.cookie.sina.service;

import java.util.List;

import cn.xhzy.crawler.cookie.sina.entity.SysCookie;

public interface SysCookieService {
	/**
	 * 删除失效cookie
	 */
	public int deleteSysCookie(SysCookie cookie);

	/**
	 * 根据应用Key更新poolStatus
	 * 
	 * @param appKey
	 */
	public void updatePoolStatusByApp(String appKey);

	/**
	 * 获取指定数量的cookie
	 */
	public List<SysCookie> findPageSysCookies(String appKey, int pageSize);
}
