package cn.xhzy.crawler.cookie.common;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.xhzy.crawler.cookie.common.config.ConfigInit;
import cn.xhzy.crawler.cookie.service.CookieHandler;
import cn.xhzy.crawler.cookie.service.impl.DefaultCookieHandler;
import cn.xhzy.crawler.cookie.sina.entity.SysCookie;

public class CookieProvider {

	private static Logger logger = LoggerFactory.getLogger(CookieProvider.class);

	private static final int DEFAULT_POOL_SIZE = ConfigInit.getInt("max_cookie_size");

	private CookieHandler handler;

	private BlockingQueue<SysCookie> queue = new LinkedBlockingQueue<SysCookie>();

	private int poolSize = DEFAULT_POOL_SIZE;

	private static CookieProvider instance = new CookieProvider();

	private CookieProvider() {
		
		this.handler = new DefaultCookieHandler();
	}

	public static CookieProvider get() {
		return instance;
	}

	public void setHandler(CookieHandler handler) {
		if (handler == null) {
			this.handler = new DefaultCookieHandler();
		} else {
			this.handler = handler;
		}
	}

	/**
	 * 初始化缓存池
	 */
	public void initPool(final String appKey, final int poolSize) {
		if (poolSize <= 0) {
			this.poolSize = DEFAULT_POOL_SIZE;
		} else {
			this.poolSize = poolSize;
		}

		handler.initPool(appKey);
		List<SysCookie> cookies = handler.findCookies(appKey, this.poolSize);

		if (cookies == null || cookies.isEmpty()) {
			logger.error("无法初始化cookie池，没有获取到cookie...");
			return;
		}

		for (SysCookie sysCookie : cookies) {
			queue.offer(sysCookie);
		}

		ExecutorService executors = Executors.newSingleThreadExecutor();
		executors.execute(monitor(appKey));
		executors.shutdown();

		logger.info("Cookie 初始化完成...");
	}

	private Runnable monitor(final String appKey) {
		return new Runnable() {
			@Override
            public void run() {
				while (true) {
					int size = queue.size();

					if (size < (CookieProvider.this.poolSize / 2)) {
						List<SysCookie> cos = handler.findCookies(appKey, (CookieProvider.this.poolSize - size));

						if (cos == null) {
							logger.error("无法初始化cookie池，没有获取到cookie...");
							continue;
						}

						for (SysCookie sysCookie : cos) {
							try {
								queue.put(sysCookie);
							} catch (InterruptedException e) {
							}
							logger.info("已往cookie池补充cookie：[{}]", sysCookie.getId());
						}
					}

					try {
						Thread.sleep(5000);
					} catch (InterruptedException e) {
					}
				}
			}
		};
	}

	public SysCookie findCookie() {
		try {
			// 从队列中取出一个cookie，并将其放入到队列的尾部。
			SysCookie cookie = queue.take();
			queue.offer(cookie);
			return cookie;
		} catch (InterruptedException e) {
			logger.error("没有可用的Cookie，等待Cookie时异常...");
		}

		return null;
	}

	public void invalidCookie(SysCookie cookie) {
		queue.remove(cookie);
		handler.invalidCookie(cookie);
	}
}
