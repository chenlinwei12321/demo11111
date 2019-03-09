package cn.xhzy.crawler.cookie.common.config;

import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class ConfigInit {

	private static Logger logger = LoggerFactory.getLogger(ConfigInit.class);

	private static final String COOKIE_PROPS = "/cookie.properties";

	private static ConfigInit configInit = new ConfigInit();

	private static Properties props;

	static {
		InputStream inStream = null;

		try {
			inStream = ConfigInit.class.getResourceAsStream(COOKIE_PROPS);
			props = new Properties();
			props.load(inStream);
		} catch (Exception e) {
			logger.error("加载cookie配置文件失败[{}]", COOKIE_PROPS, e);
		} finally {
			IOUtils.closeQuietly(inStream);
		}
	}

	private ConfigInit() {
	}

	public static ConfigInit get() {
		return configInit;
	}

	public static String getValue(String key) {
		if (props == null) {
			return null;
		}

		return (String) props.get(key);
	}

	public static int getInt(String key) {
		if (props == null || "".equals(props.getProperty(key))) {
			return 0;
		}

		return Integer.parseInt((String) props.get(key));
	}
}
