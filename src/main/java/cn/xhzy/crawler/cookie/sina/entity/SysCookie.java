package cn.xhzy.crawler.cookie.sina.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 系统cookie信息
 */
public class SysCookie implements Serializable {

	private static final long serialVersionUID = 8850496351968747316L;

	/**
	 * 主键
	 */
	private Integer id;

	/**
	 * 所属账号
	 */
	private Integer userId;

	/**
	 * cookie字符串
	 */
	private String cookies;

	/**
	 * 状态（0：待登录，1：正在登录）
	 */
	private Integer status;

	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 应用key
	 */
	private String appKey;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getCookies() {
		return cookies;
	}

	public void setCookies(String cookies) {
		this.cookies = cookies;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getAppKey() {
		return appKey;
	}

	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}

}