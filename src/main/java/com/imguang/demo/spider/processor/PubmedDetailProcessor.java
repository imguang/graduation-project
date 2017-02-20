package com.imguang.demo.spider.processor;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

@Component
public class PubmedDetailProcessor implements PageProcessor {

	
	private Logger logger = LoggerFactory.getLogger("running-log");
	private Site site = Site.me().setTimeOut(20000)
			.setUserAgent(
					"Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.143 Safari/537.36")
			.setRetryTimes(3).setSleepTime(8000).setCycleRetryTimes(3);
	
	public Site getSite() {
		return site;
	}

	public void process(Page page) {
		String pubId = (String) page.getRequest().getExtra("pubid");
		String context = page.getHtml().xpath("//pre").get();
		context = context.substring(6, context.length()-6);
		int length = context.length();
		logger.info("pubid:"+pubId+"共查询到"+ length +"字节数据");
		page.putField("pubId", pubId);
		page.putField("context", context);
		//page.setSkip(true);
	}

}
