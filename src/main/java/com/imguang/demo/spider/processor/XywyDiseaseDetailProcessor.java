package com.imguang.demo.spider.processor;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.imguang.demo.neo4j.entity.Disease;
import com.imguang.demo.spider.type.DiseaseType;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.selector.Selectable;

/**
 * @author 书生 抓取疾病详情
 */
@Component
public class XywyDiseaseDetailProcessor implements PageProcessor {
	
	public static Map<String, List<String>> diseaseRelations = new HashMap<>();
	public static Map<String, List<String>> medicineRelations = new HashMap<>();
	public static Map<String, List<String>> symptomRelations = new HashMap<>();
	

	private static Logger logger = LoggerFactory.getLogger(XywyDiseaseDetailProcessor.class);
	private Site site = Site.me().setTimeOut(20000)
			.setUserAgent(
					"Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.143 Safari/537.36")
			.setRetryTimes(3).setSleepTime(8000).setCycleRetryTimes(3);
	
	/**
	 * @param page
	 * @param disease
	 * @param id
	 * 抓取基本信息
	 */
	private void doAbstracts(Page page,Disease disease){
		Html html = page.getHtml();
		disease.setDiseaseName(html.xpath("//div[@class='jb-name fYaHei gre']/text()").toString().trim());
		disease.setAbstractContent(
				html.xpath("//div[@class='jib-articl-con jib-lh-articl']/p/text()").toString().trim());
		List<Selectable> nodes = html.xpath("//div[@class='mt20 articl-know']").nodes();
		List<String> baseKnowledge = nodes.get(0).xpath("//span[@class='fl txt-right']/text()").all();
		List<String> treatmentKnowledge = nodes.get(1).xpath("//span[@class='fl txt-right']/text()").all();
		//抓取基本信息
		disease.setIsInsurance(baseKnowledge.get(0).trim());
		disease.setSicknessRate(baseKnowledge.get(1).trim());
		disease.setSusceptiblePopulation(baseKnowledge.get(2).trim());
		disease.setInfectionMode(baseKnowledge.get(3).trim());
		
		disease.setDepartments(treatmentKnowledge.get(0).trim());
		disease.setTreatmentMethod(treatmentKnowledge.get(1).trim());
		disease.setTreatmentCycle(treatmentKnowledge.get(2).trim());
		disease.setRecoveryRate(treatmentKnowledge.get(3).trim());
		disease.setTreatmentCost(treatmentKnowledge.get(5).trim());
		//抓取相关药品
		List<String> medicines = nodes.get(1).xpath("//span[@class='fl txt-right']").nodes().get(4).links().all();
		logger.info(medicines.toString());
		if(medicines != null){
			List<String> relations = medicineRelations.get(disease.getId());
			if(relations == null){
				relations = new ArrayList<>();
				medicineRelations.put(disease.getId(), relations);
			}
			for (String tem : medicines) {
				relations.add(tem.substring(26, tem.length() - 4));
			}
		}
		logger.info(disease.toString());
	}
	
	/**
	 * @param page
	 * @param disease
	 * 病因
	 */
	private void doCause(Page page,Disease disease){
		Html html = page.getHtml();
		disease.setEtiology(html.xpath("//div[@class='jib-articl fr f14 jib-lh-articl']").toString());
		logger.info(disease.toString());
	}
	
	/**
	 * @param page
	 * @param disease
	 * 预防
	 */
	private void doPrevent(Page page,Disease disease){
		Html html = page.getHtml();
		disease.setPrevent(html.xpath("//div[@class='jib-articl fr f14 jib-lh-articl']").toString());
		logger.info(disease.toString());
	}
	
	/**
	 * @param page
	 * 并发症
	 */
	private void doNeopathy(Page page,Disease disease){
		Html html = page.getHtml();
		List<String> neopathys = html.xpath("//span[@class='db f12 lh240 mb15']").links().all();
		if(neopathys == null || neopathys.size() <= 0) return;
		logger.info(neopathys.toString());
		List<String> relations = diseaseRelations.get(disease.getId());
		if(relations == null){
			relations = new ArrayList<>();
			diseaseRelations.put(disease.getId(), relations);
		}
		for (String tem : neopathys) {
			relations.add(tem.substring(27, tem.length() - 4));
		}
	}
	
	/**
	 * @param page
	 * @param disease
	 * 症状
	 */
	private void doSymptom(Page page,Disease disease){
		Html html = page.getHtml();
		List<String> symptoms = html.xpath("//span[@class='db f12 lh240 mb15']").links().all();
		if(symptoms == null || symptoms.size() <= 0) return;
		logger.info(symptoms.toString());
		List<String> relations = symptomRelations.get(disease.getId());
		if(relations == null){
			relations = new ArrayList<>();
			symptomRelations.put(disease.getId(), relations);
		}
		for (String tem : symptoms) {
			relations.add(tem.substring(20, tem.length() - 12));
		}
	}
	
	/**
	 * @param page
	 * @param disease
	 * 护理
	 */
	private void doNursing(Page page,Disease disease){
		Html html = page.getHtml();
		disease.setNursing(html.xpath("//div[@class='jib-articl fr f14 jib-lh-articl']").toString());
		logger.info(disease.toString());
	}
	
	/**
	 * @param page
	 * @param disease
	 * 治疗具体方案
	 */
	private void doTreat(Page page,Disease disease){
		Html html = page.getHtml();
		disease.setTreatmentDetail(html.xpath("//div[@class='jib-lh-articl']").toString());
		logger.info(disease.toString());
	}
	
	public void process(Page page) {
		Request request = page.getRequest();
		Disease disease = (Disease) request.getExtra("disease");
		DiseaseType type = (DiseaseType) request.getExtra("type");
		switch (type) {
		case ABSTRACTS:
			doAbstracts(page, disease);
			break;
		case CAUSE:
			doCause(page, disease);
			break;
		case PREVENT:
			doPrevent(page, disease);
			break;
		case NEOPATHY:
			doNeopathy(page, disease);
			break;
		case NURSING:
			doNursing(page, disease);
			break;
		case SYMPTOM:
			doSymptom(page, disease);
			break;
		case TREAT:
			doTreat(page, disease);
			break;
			
		default:
			logger.info("混入了一个奇怪的东西");
			logger.info(page.toString());
			break;
		}
	}

	public Site getSite() {
		return site;
	}
	
	public static void main(String[] args) {
		String url = "http://jib.xywy.com/il_sii_4558.htm";
		String id = url.substring(27, url.length() - 4);
		Spider detailSpider = Spider.create(new XywyDiseaseDetailProcessor());
		Disease disease = new Disease();
		disease.setId(id);
		for (DiseaseType diseaseType : DiseaseType.values()) {
			Request request = new Request(diseaseType.getUrl().replace("{id}", id));
			request.putExtra("type", diseaseType);
			request.putExtra("disease", disease);
			detailSpider.addRequest(request);
		}
		detailSpider.thread(1).run();
		detailSpider.close();
		logger.info(disease.toString());
		logger.info(diseaseRelations.toString());
		logger.info(medicineRelations.toString());
		logger.info(symptomRelations.toString());
	}

}
