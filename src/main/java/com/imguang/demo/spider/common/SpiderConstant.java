package com.imguang.demo.spider.common;

import us.codecraft.webmagic.Site;

public class SpiderConstant {

	public final static Site SITE =  Site.me().setTimeOut(20000)
			.setUserAgent(
					"Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.143 Safari/537.36")
			.setRetryTimes(3).setSleepTime(2210).setCycleRetryTimes(3);
	
}
