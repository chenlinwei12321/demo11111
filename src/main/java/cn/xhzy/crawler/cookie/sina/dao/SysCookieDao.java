package cn.xhzy.crawler.cookie.sina.dao;

import java.util.List;

import cn.xhzy.crawler.cookie.sina.entity.SysCookie;

public interface SysCookieDao {
	/**
	 * 根据app key更新
	 * 
	 * @param appKey
	 */
	public void updatePoolStatusByApp(String appKey);

	/**
	 * 删除cookie
	 */
	public int deleteSysCookie(Integer id);

	/**
	 * 获取指定数量的cookie
	 */
	public List<SysCookie> findPageSysCookies(int pageSize);

	/**
	 * 批量更新appKey
	 */
	public void batchUpdateByAppKey(String ids, String appKey);
}
