package com.imguang.demo.spider;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.imguang.demo.mysql.dao.PubmedMapper;
import com.imguang.demo.mysql.dao.XywyDiseaseUrlMapper;
import com.imguang.demo.mysql.model.XywyDiseaseUrl;
import com.imguang.demo.neo4j.entity.Disease;
import com.imguang.demo.neo4j.entity.Medicine;
import com.imguang.demo.neo4j.entity.Symptom;
import com.imguang.demo.neo4j.service.DiseaseService;
import com.imguang.demo.neo4j.service.MedicineService;
import com.imguang.demo.neo4j.service.SymptomService;
import com.imguang.demo.spider.pipeline.PubmedPipeline;
import com.imguang.demo.spider.pipeline.XywyDiseaseListPipeline;
import com.imguang.demo.spider.pipeline.XywySymptomListPipeline;
import com.imguang.demo.spider.processor.PubmedDetailProcessor;
import com.imguang.demo.spider.processor.PubmedListProcessor;
import com.imguang.demo.spider.processor.XywyDiseaseDetailProcessor;
import com.imguang.demo.spider.processor.XywyDiseaseListProcessor;
import com.imguang.demo.spider.processor.XywyMedcineProcessor;
import com.imguang.demo.spider.processor.XywySymptomListProcessor;
import com.imguang.demo.spider.scheduler.remover.DoNothingRemover;
import com.imguang.demo.spider.type.DiseaseType;

import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.scheduler.QueueScheduler;

public class Procceed {

	public static final String BASE_URL_MEDLINE = "https://www.ncbi.nlm.nih.gov/pubmed/{1}?report=medline&format=text";

	public static Logger logger = LoggerFactory.getLogger(Procceed.class);
	public static List<String> pubidList = new LinkedList<String>();

	private static PubmedPipeline pubmedPipeline = null;
	private static ApplicationContext ac = null;
	private static PubmedDetailProcessor pubmedDetailProcessor = null;
	private static PubmedListProcessor pubmedListProcessor = null;
	private static XywyMedcineProcessor xywyMedcineProcessor = null;

	private static XywyDiseaseListPipeline xywyDiseaseListPipeline = null;
	private static XywyDiseaseListProcessor xywyDiseaseListProcessor = null;
	private static XywySymptomListProcessor xywySymptomListProcessor = null;
	private static XywySymptomListPipeline xywySymptomListPipeline = null;
	private static SymptomService symptomService = null;
	private static MedicineService medicineService = null;
	private static DiseaseService diseaseService = null;

	private static XywyDiseaseUrlMapper xywyDiseaseUrlMapper = null;

	public static PubmedMapper pubmedMapper = null;

	public static String medline_factory(String pubid) {
		String temUrl = "";
		temUrl = BASE_URL_MEDLINE.replaceFirst("\\{1\\}", pubid);
		return temUrl;
	}

	public static void xywyDiseaseList() {// 疾病列表爬取
		String beginUrl = "http://jib.xywy.com/html/neike.html";
		logger.info("spider for disease beigin!");
		Request request = new Request();
		request.putExtra("flag", 0);
		request.setUrl(beginUrl);
		Spider.create(xywyDiseaseListProcessor).addPipeline(xywyDiseaseListPipeline).addRequest(request).thread(1)
				.run();
		logger.info("spider for list done!");
	}

	public static void spiderPubmedOfList() {// 论文列表爬取
		// target url @EXP
		String beginUrl = "https://www.ncbi.nlm.nih.gov/pubmed/?term=ischemic+placental+disease";
		logger.info("spider for list begin!");
		Request request = new Request();
		request.setUrl(beginUrl);
		request.putExtra("flag", 1);
		Spider pubmedListSpider = Spider.create(pubmedListProcessor)
				.setScheduler(new QueueScheduler().setDuplicateRemover(new DoNothingRemover())).thread(1);
		pubmedListSpider.addRequest(request).run();
		pubmedListSpider.close();
		logger.info("spider for list done!");
		logger.info("共爬取" + pubidList.size() + "条数据!");
	}

	public static void spiderPubmedOfDetail() {// 论文详情爬取
		Spider pubmedDetailSpider = Spider.create(pubmedDetailProcessor).addPipeline(pubmedPipeline).thread(1);
		logger.info("spider for detail begin!");
		for (String pubid : pubidList) {
			Request targetRequest = new Request();
			targetRequest.setUrl(medline_factory(pubid));
			targetRequest.putExtra("pubid", pubid);
			pubmedDetailSpider.addRequest(targetRequest);
		}
		pubmedDetailSpider.run();
		pubmedDetailSpider.close();
		logger.info("spider for detail done!");
	}

	private static void init() {// 初始化
		ac = new ClassPathXmlApplicationContext("spring-mybatis.xml");
		pubmedPipeline = (PubmedPipeline) ac.getBean("pubmedPipeline");
		pubmedDetailProcessor = (PubmedDetailProcessor) ac.getBean("pubmedDetailProcessor");
		pubmedListProcessor = (PubmedListProcessor) ac.getBean("pubmedListProcessor");
		pubmedMapper = (PubmedMapper) ac.getBean("pubmedMapper");
		xywyDiseaseListPipeline = ac.getBean(XywyDiseaseListPipeline.class);
		xywyDiseaseListProcessor = ac.getBean(XywyDiseaseListProcessor.class);
		xywyDiseaseUrlMapper = ac.getBean(XywyDiseaseUrlMapper.class);

		xywySymptomListPipeline = ac.getBean(XywySymptomListPipeline.class);
		xywySymptomListProcessor = ac.getBean(XywySymptomListProcessor.class);
		symptomService = ac.getBean(SymptomService.class);
		medicineService = ac.getBean(MedicineService.class);
		diseaseService = ac.getBean(DiseaseService.class);
		xywyMedcineProcessor = ac.getBean(XywyMedcineProcessor.class);
	}

	public static void symptomList() {// 症状列表爬取
		Request beginRequest = new Request();
		beginRequest.setUrl("http://zzk.xywy.com/p/neike.html");
		beginRequest.putExtra("flag", 0);
		Spider.create(xywySymptomListProcessor).addPipeline(xywySymptomListPipeline).addRequest(beginRequest).thread(1)
				.run();
	}

	/**
	 * @param medicineSet
	 *            药品详情爬取
	 */
	private final static String MEDICINE_URL = "http://yao.xywy.com/goods/{1}.htm";

	public static void medicineDetail(Set<String> medicineSet) {
		logger.info("开始爬取药品,此次共爬取" + medicineSet.size() + "条:" + medicineSet);
		Spider spider = Spider.create(xywyMedcineProcessor);
		List<Medicine> medicines = new ArrayList<>();
		for (String beginUrl : medicineSet) {
			String id = beginUrl;
			// 如果数据库中存在
			if (medicineService.getMedicineFromId(id) != null) {
				logger.info("id:" + id + "的药品已存在！");
				continue;
			}
			// 如果不存在则爬取
			beginUrl = MEDICINE_URL.replace("{1}", id);
			Medicine medicine = new Medicine();
			medicine.setId(id);
			medicines.add(medicine);
			Request request = new Request(beginUrl);
			request.putExtra("medicine", medicine);
			spider.addRequest(request);
		}
		spider.thread(1).run();
		spider.close();
		// logger.info("-----------------以下为爬取到的药品信息------------------");
		// logger.info(medicines.toString());
		// logger.info("---------------------------------------------------");
		logger.info("爬取成功，加入数据库中");
		medicineService.saveBath(medicines);
		logger.info("加入数据库成功！");
	}

	private static List<Disease> diseases;

	/**
	 * 疾病详情爬取
	 */
	public static void diseaseDetail(int start, int limit) {
		Map<String, Integer> map = new HashMap<>();
		map.put("start", start);
		map.put("limit", limit);
		Spider detailSpider = Spider.create(new XywyDiseaseDetailProcessor());
		diseases = new ArrayList<>();
		XywyDiseaseDetailProcessor.diseaseRelations = new HashMap<>();
		XywyDiseaseDetailProcessor.medicineRelations = new HashMap<>();
		XywyDiseaseDetailProcessor.symptomRelations = new HashMap<>();
		List<XywyDiseaseUrl> xywyDiseaseUrls = xywyDiseaseUrlMapper.selectByLimit(map);
		logger.info("开始爬取疾病实体(" + xywyDiseaseUrls.size() + "条):" + xywyDiseaseUrls);
		for (XywyDiseaseUrl xywyDiseaseUrl : xywyDiseaseUrls) {// 对每个链接加入
			String url = xywyDiseaseUrl.getUrl();
			String id = url.substring(27, url.length() - 4);
			if (diseaseService.getDiseaseFromId(id) != null) {
				continue;
			}
			Disease disease = new Disease();
			disease.setId(id);
			diseases.add(disease);
			for (DiseaseType diseaseType : DiseaseType.values()) {
				if (diseaseType == DiseaseType.NEOPATHY)
					continue;// 并发症最后再进行抓取
				Request request = new Request(diseaseType.getUrl().replace("{id}", id));
				request.putExtra("type", diseaseType);
				request.putExtra("disease", disease);
				detailSpider.addRequest(request);
			}
		}
		detailSpider.thread(1).run();
		detailSpider.close();
		StringBuffer stringBuffer = new StringBuffer();
		for (Disease disease : diseases) {
			stringBuffer.append(disease.getDiseaseName() + ";");
		}
		logger.info("爬取成功:" + stringBuffer.toString());
		// logger.info("---------------------以下是实体详细信息------------------------------------");
		// logger.info(diseases.toString());
		logger.info("--------------------以下是药品关联详细信息----------------------------------");
		logger.info(XywyDiseaseDetailProcessor.medicineRelations.toString());
		logger.info("--------------------以下是症状关联详细信息----------------------------------");
		logger.info(XywyDiseaseDetailProcessor.symptomRelations.toString());
		logger.info("----------------------------------------------------------------------");

	}
	
	
	/**
	 * 爬取图片URL
	 * @param start
	 * @param limit
	 */
	public static void diseaseImgUrl(int start, int limit) {
		Map<String, Integer> map = new HashMap<>();
		map.put("start", start);
		map.put("limit", limit);
		Spider detailSpider = Spider.create(new XywyDiseaseDetailProcessor());
		diseases = new ArrayList<>();
		List<XywyDiseaseUrl> xywyDiseaseUrls = xywyDiseaseUrlMapper.selectByLimit(map);
		logger.info("开始爬取疾病实体(" + xywyDiseaseUrls.size() + "条):" + xywyDiseaseUrls);
		for (XywyDiseaseUrl xywyDiseaseUrl : xywyDiseaseUrls) {// 对每个链接加入
			String url = xywyDiseaseUrl.getUrl();
			String id = url.substring(27, url.length() - 4);
			Disease disease = diseaseService.getDiseaseFromId(id);
			if(disease == null || disease.getImgUrl() != null){
				continue;
			}
			diseases.add(disease);
			Request request = new Request(DiseaseType.IMG.getUrl().replace("{id}", id));
			request.putExtra("type", DiseaseType.IMG);
			request.putExtra("disease", disease);
			detailSpider.addRequest(request);
		}
		detailSpider.thread(1).run();
		detailSpider.close();
		diseaseService.saveBath(diseases);
	}
	
	

	/**
	 * 添加疾病和药品关联
	 * 
	 * @param medicineRelation
	 */
	public static void addMedicineRelation(Map<String, List<String>> medicineRelation) {
		logger.info("添加疾病药品关联");
		for (Disease disease : diseases) {
			Set<Medicine> medicines = new HashSet<>();
			List<String> medicineIds = medicineRelation.get(disease.getId());
			for (String id : medicineIds) {// 用id从每个数据库中提取出对应实体
				Medicine medicine = medicineService.getMedicineFromId(id);
				if (medicine == null) {
					logger.error("数据库药品为空，出错！出错药品id为:" + id + ",疾病id为：" + disease.getId());
					logger.error("疾病对应药品id情况：" + medicineIds);
					continue;
				}
				medicines.add(medicine);
			}
			disease.setMedicines(medicines);
			logger.info("添加id:" + disease.getId() + "的药品成功！");
		}
	}

	/**
	 * 添加症状关联
	 * 
	 * @param symptomRelation
	 */
	public static void addSymptomRelation(Map<String, List<String>> symptomRelation) {
		logger.info("添加疾病药品关联");
		for (Disease disease : diseases) {
			Set<Symptom> symptoms = new HashSet<>();
			List<String> symptomIds = symptomRelation.get(disease.getId());
			for (String id : symptomIds) {// 用id从每个数据库中提取出对应实体
				Symptom symptom = symptomService.getSymptomFromId(id);
				if (symptom == null) {
					logger.error("数据库症状为空，出错！出错症状id为:" + id + ",疾病id为：" + disease.getId());
					logger.error("疾病对应症状id情况：" + symptomIds);
					continue;
				}
				symptoms.add(symptom);
			}
			disease.setSymptoms(symptoms);
			logger.info("添加id:" + disease.getId() + "的症状成功！");
		}
	}

	private static Set<String> unExistId = new HashSet<>();
	
	public static void addDiseaseRelation(int start,int limit) {
		Map<String, Integer> map = new HashMap<>();
		map.put("start", start);
		map.put("limit", limit);
		Spider detailSpider = Spider.create(new XywyDiseaseDetailProcessor());
		XywyDiseaseDetailProcessor.diseaseRelations = new HashMap<>();
		List<XywyDiseaseUrl> xywyDiseaseUrls = xywyDiseaseUrlMapper.selectByLimit(map);
		logger.info("开始爬取疾病实体(" + xywyDiseaseUrls.size() + "条):" + xywyDiseaseUrls);
		for (XywyDiseaseUrl xywyDiseaseUrl : xywyDiseaseUrls) {// 对每个链接加入
			String url = xywyDiseaseUrl.getUrl();
			String id = url.substring(27, url.length() - 4);
			Disease disease = new Disease();
			disease.setId(id);
			Request request = new Request(DiseaseType.NEOPATHY.getUrl().replace("{id}", id));
			request.putExtra("type", DiseaseType.NEOPATHY);
			request.putExtra("disease", disease);
			detailSpider.addRequest(request);
		}
		detailSpider.thread(1).run();
		detailSpider.close();
		logger.info("关系如下：" + XywyDiseaseDetailProcessor.diseaseRelations);
		logger.info("开始添加关系");
		List<Disease> diseases = new ArrayList<>();
		for (Map.Entry<String, List<String>> entry : XywyDiseaseDetailProcessor.diseaseRelations.entrySet()) {
			String id = entry.getKey();
			List<String> diseaseRelations = entry.getValue();
			Disease disease = diseaseService.getDiseaseFromId(id);
			if(disease == null){
				logger.error("不存在疾病id:" + id);
				unExistId.add(id);
				continue;
			}
			if(disease.getDisease() == null){//如果没有关系
				disease.setDisease(new HashSet<>());
			}
			for (String idTo : diseaseRelations) {
				Disease diseaseTo = diseaseService.getDiseaseFromId(idTo);
				if(diseaseTo == null){
					logger.error("不存在疾病id:" + idTo + ",在添加关系时,关联id为:" + id);
					unExistId.add(idTo);
					continue;
				}
				disease.getDisease().add(diseaseTo);
			}
			diseases.add(disease);
		}
		diseaseService.saveBath(diseases);
	}

	public static void main(String[] args) throws IOException {
		init();
//		for(int i=0;i < 97;i ++){
//			logger.info("开始爬取第" + (i * 50 + 1) + "-" + (i * 50 + 50) + "条");
//			addDiseaseRelation(i*50, 50);
//		}
		for(int i=0;i < 97;i ++){
			logger.info("开始爬取第" + (i * 50 + 1) + "-" + (i * 50 + 50) + "条");
			diseaseImgUrl(i*50, 50);
		}
		
		
//		FileWriter fileWriter = new FileWriter("F:\\noExists\\disease");
//		for (String string : unExistId) {
//			fileWriter.write(string + "\n");
//			fileWriter.flush();
//		}
//		fileWriter.close();

		// for(int i=60;i < 97;i ++){
		// logger.info("开始爬取第" + (i * 50 + 1) + "-" + (i * 50 + 50) + "条");
		// diseaseDetail(i * 50,50);
		// medicineDetail(XywyDiseaseDetailProcessor.getMedicineSet());
		// addMedicineRelation(XywyDiseaseDetailProcessor.medicineRelations);
		// addSymptomRelation(XywyDiseaseDetailProcessor.symptomRelations);
		// logger.info("爬取成功，存入数据库中！");
		// diseaseService.saveBath(diseases);
		// logger.info("存入数据库成功！");
		// }
		//
		// logger.info(diseases.toString());
		// logger.info(XywyDiseaseDetailProcessor.diseaseRelations.toString());
		// logger.info(XywyDiseaseDetailProcessor.medicineRelations.toString());
		// logger.info(XywyDiseaseDetailProcessor.symptomRelations.toString());
	}

}
