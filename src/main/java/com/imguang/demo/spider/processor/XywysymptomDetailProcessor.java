package com.imguang.demo.spider.processor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.imguang.demo.mysql.dao.XywySymptomUrlMapper;
import com.imguang.demo.mysql.model.XywySymptomUrl;
import com.imguang.demo.neo4j.entity.Disease;
import com.imguang.demo.neo4j.entity.Symptom;
import com.imguang.demo.neo4j.service.SymptomService;
import com.imguang.demo.spider.common.SpiderConstant;
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

	private static ApplicationContext ac = null;
	private static SymptomService service = null;
	private static XywySymptomUrlMapper xywySymptomUrlMapper = null;

	private static Logger log = LoggerFactory.getLogger(XywysymptomDetailProcessor.class);

	/**
	 * @param page
	 * @param symptom
	 *            symptom的介绍抓取
	 */
	private void doAbstract(Html html, Symptom symptom) {
		symptom.setName(html.xpath("//div[@class='jb-name fYaHei gre']/text()").toString());
		symptom.setAbstractContent(
				html.xpath("//div[@class='zz-articl fr f14']").toString().replaceAll("<a.*>分享到</a>", ""));
	}

	/**
	 * @param html
	 * @param symptom
	 *            病因抓取
	 */
	private void doCause(Html html, Symptom symptom) {
		symptom.setCause(html.xpath("//div[@class='zz-articl fr f14']").toString());
	}

	/**
	 * @param html
	 * @param symptom
	 *            预防抓取
	 */
	private void doPrevent(Html html, Symptom symptom) {
		symptom.setPrevent(html.xpath("//div[@class='zz-articl fr f14']").toString());
	}
	
	private void doImgUrl(Html html, Symptom symptom){
		symptom.setImgUrl(html.xpath("div[@class='rec-imgbox fl bor mr15']/img/@src").toString());
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
		case IMG:
			doImgUrl(page.getHtml(), symptom);
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

	private static void init() {
		ac = new ClassPathXmlApplicationContext("spring-mybatis.xml");
		service = ac.getBean(SymptomService.class);
		xywySymptomUrlMapper = ac.getBean(XywySymptomUrlMapper.class);
	}

	private static void spiderImgUrl(){
		Map<String, Integer> map = new HashMap<>();
		map.put("start", 0);
		map.put("limit", 7000);
		List<XywySymptomUrl> xywySymptomUrls = xywySymptomUrlMapper.selectByLimit(map);
		log.info(xywySymptomUrls.size() + "");
		// 爬取详情
		int cnt = 0;
		Spider spider = null;
		List<Symptom> symptoms = null;
		for (XywySymptomUrl xywySymptomUrl : xywySymptomUrls) {// 遍历
			String beginUrl = xywySymptomUrl.getUrl();
			cnt++;
			if (cnt % 100 == 1) {
				log.info("第" + cnt + "-" + (cnt + 99) + "条开始爬取！");
				spider = Spider.create(new XywysymptomDetailProcessor()).thread(1);
				symptoms = new ArrayList<>();
			}
			String id = beginUrl.substring(20, beginUrl.length() - 12);
			Symptom symptom = service.getSymptomFromId(id);
//			System.out.println(symptom);
			if (symptom != null && symptom.getImgUrl() == null) {//数据库中不存在再进行爬取
				log.info("加入id:" + id);
				symptoms.add(symptom);
				Request request = new Request(SymptomType.IMG.getUrl().replace("{id}", id));
				request.putExtra("type", SymptomType.IMG);
				request.putExtra("symptom", symptom);
				spider.addRequest(request);
			}
			if (cnt % 100 == 0 || cnt == xywySymptomUrls.size()) {
				spider.run();
				spider.close();
				spider = null;
				log.info("爬取进度" + cnt + "/" + xywySymptomUrls.size() + "，加入数据库中..");
				service.saveBatch(symptoms);
				log.info("加入成功！");
			}
		}
	}
	
	public static void main(String[] args) {
		init();
		spiderImgUrl();
		// //爬取列表
		// Request beginRequest = new Request();
		// beginRequest.setUrl("http://zzk.xywy.com/p/neike.html");
		// beginRequest.putExtra("flag", 0);
		// Spider ListSpider = Spider.create(new
		// XywySymptomListProcessor()).addRequest(beginRequest).thread(1);
		// ListSpider.run();
		// ListSpider.close();
		// ListSpider = null;
		// List<String> symptomlinks = XywySymptomListProcessor.symptomlinks;
		// log.info("共抓取：" + symptomlinks.size() + "条记录！");
//		Map<String, Integer> map = new HashMap<>();
//		map.put("start", 0);
//		map.put("limit", 7000);
//		List<XywySymptomUrl> xywySymptomUrls = xywySymptomUrlMapper.selectByLimit(map);
//		log.info(xywySymptomUrls.size() + "");
//		// 爬取详情
//		int cnt = 0;
//		Spider spider = null;
//		List<Symptom> symptoms = null;
//		for (XywySymptomUrl xywySymptomUrl : xywySymptomUrls) {// 遍历
//			String beginUrl = xywySymptomUrl.getUrl();
//			cnt++;
//			if (cnt % 100 == 1) {
//				log.info("第" + cnt + "-" + (cnt + 99) + "条开始爬取！");
//				spider = Spider.create(new XywysymptomDetailProcessor()).thread(1);
//				symptoms = new ArrayList<>();
//			}
//			String id = beginUrl.substring(20, beginUrl.length() - 12);
//			if (service.getSymptomFromId(id) == null) {//数据库中不存在再进行爬取
//				log.info("加入id:" + id);
//				Symptom symptom = new Symptom();
//				symptom.setId(id);
//				symptoms.add(symptom);
//				for (SymptomType symptomType : SymptomType.values()) {
//					Request request = new Request(symptomType.getUrl().replace("{id}", id));
//					request.putExtra("type", symptomType);
//					request.putExtra("symptom", symptom);
//					spider.addRequest(request);
//				}
//			}
//			if (cnt % 100 == 0 || cnt == xywySymptomUrls.size()) {
//				spider.run();
//				spider.close();
//				spider = null;
//				log.info("爬取进度" + cnt + "/" + xywySymptomUrls.size() + "，加入数据库中..");
//				service.saveBatch(symptoms);
//				log.info("加入成功！");
//			}
//		}
	}

}
