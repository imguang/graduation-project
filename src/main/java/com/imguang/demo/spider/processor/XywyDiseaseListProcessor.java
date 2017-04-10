package com.imguang.demo.spider.processor;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * @author 书生
 *	寻医问药网爬虫，爬取疾病列表
 */
@Component
public class XywyDiseaseListProcessor implements PageProcessor{

	private Logger logger = LoggerFactory.getLogger(XywyDiseaseListProcessor.class);
	private Site site = Site.me().setTimeOut(20000)
			.setUserAgent(
					"Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.143 Safari/537.36")
			.setRetryTimes(3).setSleepTime(8000).setCycleRetryTimes(3);
	private static String beginUrl = "http://jib.xywy.com/html/neike.html";
	
	public void process(Page page) {
		Request request = page.getRequest();
		int flag = (Integer) request.getExtra("flag");
		if(flag == 0){
			List<String> links = page.getHtml().xpath("//ul[@class='jbk-sed-menu bor pa none f12']/li/a/@href").all();
			links.add("http://jib.xywy.com/html/chuanranke.html");
			links.add("http://jib.xywy.com/html/nanke.html");
			links.add("http://jib.xywy.com/html/jingshenke.html");
			links.add("http://jib.xywy.com/html/xinlike.html");
			links.add("http://jib.xywy.com/html/yingyangke.html");
			links.add("http://jib.xywy.com/html/jizhenke.html");
			links.add("http://jib.xywy.com/html/ganbing.html");
			logger.info(links.toString());
			logger.info("类别 size:" + links.size());
			for (String string : links) {
				Request targetRequest = new Request();
				targetRequest.setUrl(string);
				targetRequest.putExtra("flag", 1);
				page.addTargetRequest(targetRequest);
			}
			page.setSkip(true);
		} else {
			List<String> links = page.getHtml().xpath("//div[@class='fl jblist-con-ill']").links().all();
			page.putField("links", links);
			logger.info("爬取到：" + links.size());
		}
	}

	public Site getSite() {
		return site;
	}

	public static void main(String[] args) {
		Request beginRequest = new Request();
		beginRequest.setUrl(beginUrl);
		beginRequest.putExtra("flag", 0);
		Spider.create(new XywyDiseaseListProcessor()).addRequest(beginRequest).thread(1).run();
	}
	
}
