package com.imguang.demo.spider.processor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.imguang.demo.spider.common.SpiderConstant;
import com.imguang.demo.spider.entity.Symptom;
import com.imguang.demo.spider.type.SymptomType;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;

/**
 * @author 书生 抓取症状简介、原因、预防
 */
public class XywysymptomDetailProcessor implements PageProcessor {

	private static Logger log = LoggerFactory.getLogger(XywysymptomDetailProcessor.class);

	/**
	 * @param page
	 * @param symptom
	 *            symptom的介绍抓取
	 */
	private void doAbstract(Html html, Symptom symptom) {
		symptom.setAbstractContent(
				html.xpath("//div[@class='zz-articl fr f14']").toString()
				.replaceAll("<a.*>分享到</a>", ""));
	}
	
	/**
	 * @param html
	 * @param symptom
	 * 病因抓取
	 */
	private void doCause(Html html, Symptom symptom) {
		symptom.setCause(html.xpath("//div[@class='zz-articl fr f14']").toString());
	}
	
	/**
	 * @param html
	 * @param symptom
	 * 预防抓取
	 */
	private void doPrevent(Html html, Symptom symptom) {
		symptom.setPrevent(html.xpath("//div[@class='zz-articl fr f14']").toString());
	}

	@Override
	public void process(Page page) {
		Request request = page.getRequest();
		Symptom symptom = (Symptom) request.getExtra("symptom");
		SymptomType symptomType = (SymptomType) request.getExtra("type");
		switch (symptomType) {
		case ABSTRACTS:
			doAbstract(page.getHtml(), symptom);
			break;
		case CAUSE:
			doCause(page.getHtml(), symptom);
			break;
		case PREVENT:
			doPrevent(page.getHtml(), symptom);
			break;
		default:
			log.info("混入了个奇怪的东西...");
			log.info(page.toString());
			break;
		}
	}

	@Override
	public Site getSite() {
		return SpiderConstant.SITE;
	}

	public static void main(String[] args) {
		Spider spider = Spider.create(new XywysymptomDetailProcessor());
		String beginUrl = "http://zzk.xywy.com/5513_gaishu.html";
		String id = beginUrl.substring(20, beginUrl.length() - 12);
		Symptom symptom = new Symptom();
		symptom.setId(id);
		for (SymptomType symptomType : SymptomType.values()) {
			Request request = new Request(symptomType.getUrl().replace("{id}", id));
			request.putExtra("type", symptomType);
			request.putExtra("symptom", symptom);
			spider.addRequest(request);
		}
		spider.thread(1).run();
		spider.close();
		log.info(symptom.toString());
	}

}
