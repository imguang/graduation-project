package com.imguang.demo.spider.processor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.imguang.demo.neo4j.entity.Disease;
import com.imguang.demo.spider.common.SpiderConstant;
import com.imguang.demo.spider.type.DiseaseType;
import com.imguang.demo.utils.StringUtils;

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

	public static Set<String> getMedicineSet() {
		Set<String> medicineSet = new HashSet<>();
		Collection<List<String>> lists = medicineRelations.values();
		for (List<String> list : lists) {
			medicineSet.addAll(list);
		}
		return medicineSet;
	}

	/**
	 * @param page
	 * @param disease
	 * @param id
	 *            抓取基本信息
	 */
	private void doAbstracts(Page page, Disease disease) {
		Html html = page.getHtml();
		disease.setDiseaseName(
				StringUtils.trimString(html.xpath("//div[@class='jb-name fYaHei gre']/text()").toString()));
		disease.setAbstractContent(html.xpath("//div[@class='jib-articl-con jib-lh-articl']/p/text()").toString());
		List<Selectable> nodes = html.xpath("//div[@class='mt20 articl-know']").nodes();
		List<String> baseKnowledge = nodes.get(0).xpath("//span[@class='fl txt-right']/text()").all();
		List<String> treatmentKnowledge = nodes.get(1).xpath("//span[@class='fl txt-right']/text()").all();
		// 抓取基本信息
		disease.setIsInsurance(StringUtils.trimString(baseKnowledge.get(0)));
		disease.setSicknessRate(StringUtils.trimString(baseKnowledge.get(1)));
		disease.setSusceptiblePopulation(StringUtils.trimString(baseKnowledge.get(2)));
		disease.setInfectionMode(StringUtils.trimString(baseKnowledge.get(3)));

		disease.setDepartments(StringUtils.trimString(treatmentKnowledge.get(0)));
		disease.setTreatmentMethod(StringUtils.trimString(treatmentKnowledge.get(1)));
		disease.setTreatmentCycle(StringUtils.trimString(treatmentKnowledge.get(2)));
		disease.setRecoveryRate(StringUtils.trimString(treatmentKnowledge.get(3)));
		if(treatmentKnowledge.size() == 6){
			disease.setTreatmentCost(StringUtils.trimString(treatmentKnowledge.get(5)));
		} else {
			disease.setTreatmentCost(StringUtils.trimString(treatmentKnowledge.get(4)));
		}
		// 抓取相关药品
		List<String> medicines = nodes.get(1).xpath("//span[@class='fl txt-right']").nodes().get(4).links().all();
		logger.debug(medicines.toString());
		List<String> relations = new ArrayList<>();
		medicineRelations.put(disease.getId(), relations);
		if (medicines != null) {
			for (String tem : medicines) {
				relations.add(tem.substring(26, tem.length() - 4));
			}
		}
		logger.debug(disease.toString());
	}

	/**
	 * @param page
	 * @param disease
	 *            病因
	 */
	private void doCause(Page page, Disease disease) {
		Html html = page.getHtml();
		disease.setEtiology(html.xpath("//div[@class='jib-articl fr f14 jib-lh-articl']").toString());
		logger.debug(disease.toString());
	}

	/**
	 * @param page
	 * @param disease
	 *            预防
	 */
	private void doPrevent(Page page, Disease disease) {
		Html html = page.getHtml();
		disease.setPrevent(html.xpath("//div[@class='jib-articl fr f14 jib-lh-articl']").toString());
		logger.debug(disease.toString());
	}

	/**
	 * @param page
	 *            并发症
	 */
	private void doNeopathy(Page page, Disease disease) {
		Html html = page.getHtml();
		List<String> neopathys = html.xpath("//span[@class='db f12 lh240 mb15']").links().all();
		List<String> relations = new ArrayList<>();
		diseaseRelations.put(disease.getId(), relations);
		if (neopathys == null || neopathys.size() <= 0)
			return;
		logger.debug(neopathys.toString());
		for (String tem : neopathys) {
			relations.add(tem.substring(27, tem.length() - 4));
		}
	}

	/**
	 * @param page
	 * @param disease
	 *            症状
	 */
	private void doSymptom(Page page, Disease disease) {
		Html html = page.getHtml();
		List<String> symptoms = html.xpath("//span[@class='db f12 lh240 mb15']").links().all();
		List<String> relations = new ArrayList<>();
		symptomRelations.put(disease.getId(), relations);
		if (symptoms == null || symptoms.size() <= 0)
			return;
		logger.debug(symptoms.toString());
		for (String tem : symptoms) {
			relations.add(tem.substring(20, tem.length() - 12));
		}
	}

	/**
	 * @param page
	 * @param disease
	 *            护理
	 */
	private void doNursing(Page page, Disease disease) {
		Html html = page.getHtml();
		disease.setNursing(html.xpath("//div[@class='jib-articl fr f14 jib-lh-articl']").toString());
		logger.debug(disease.toString());
	}
	
	/**
	 * @param page
	 * @param disease
	 *            治疗具体方案
	 */
	private void doTreat(Page page, Disease disease) {
		Html html = page.getHtml();
		disease.setTreatmentDetail(html.xpath("//div[@class='jib-lh-articl']").toString());
		logger.debug(disease.toString());
	}
	
	private void doImg(Page page, Disease disease){
		Html html = page.getHtml();
		disease.setImgUrl(html.xpath("//div[@class='jib-articl-imgbox bor fr']/img/@src").toString());
		logger.info("获得图片URL：" + disease.getImgUrl());
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
		case IMG:
			doImg(page, disease);
			break;

		default:
			logger.debug("混入了一个奇怪的东西");
			logger.debug(page.toString());
			break;
		}
	}

	public Site getSite() {
		return SpiderConstant.SITE;
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
		logger.debug(disease.toString());
		logger.debug(diseaseRelations.toString());
		logger.debug(medicineRelations.toString());
		logger.debug(symptomRelations.toString());
	}

}
