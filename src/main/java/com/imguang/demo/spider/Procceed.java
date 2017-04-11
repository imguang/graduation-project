package com.imguang.demo.spider;


import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.imguang.demo.mysql.dao.PubmedMapper;
import com.imguang.demo.mysql.dao.XywyDiseaseUrlMapper;
import com.imguang.demo.mysql.dao.XywySymptomUrlMapper;
import com.imguang.demo.neo4j.service.SymptomService;
import com.imguang.demo.spider.pipeline.PubmedPipeline;
import com.imguang.demo.spider.pipeline.XywyDiseaseListPipeline;
import com.imguang.demo.spider.pipeline.XywySymptomListPipeline;
import com.imguang.demo.spider.processor.PubmedDetailProcessor;
import com.imguang.demo.spider.processor.PubmedListProcessor;
import com.imguang.demo.spider.processor.XywyDiseaseListProcessor;
import com.imguang.demo.spider.processor.XywySymptomListProcessor;
import com.imguang.demo.spider.scheduler.remover.DoNothingRemover;

import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.scheduler.QueueScheduler;

public class Procceed {

	public static final String BASE_URL_MEDLINE = "https://www.ncbi.nlm.nih.gov/pubmed/{1}?report=medline&format=text";
	
	public static Logger logger = LoggerFactory.getLogger("com.imguang.demo.procceed");
	public static List<String> pubidList = new LinkedList<String>();

	private static PubmedPipeline pubmedPipeline = null;
	private static ApplicationContext ac = null;
	private static PubmedDetailProcessor pubmedDetailProcessor = null;
	private static PubmedListProcessor pubmedListProcessor = null;
	
	private static XywyDiseaseListPipeline xywyDiseaseListPipeline = null;
	private static XywyDiseaseListProcessor xywyDiseaseListProcessor = null;
	private static XywySymptomListProcessor xywySymptomListProcessor = null;
	private static XywySymptomListPipeline xywySymptomListPipeline = null;
	private static SymptomService service = null;
	
	
	
	private static XywyDiseaseUrlMapper xywyDiseaseUrlMapper = null;
	
	public static PubmedMapper pubmedMapper = null;
	
	public static String medline_factory(String pubid){
		String temUrl = "";
		temUrl = BASE_URL_MEDLINE.replaceFirst("\\{1\\}", pubid);
		return temUrl;
	}
	
	public static void xywyList(){
		String beginUrl = "http://jib.xywy.com/html/neike.html";
		logger.info("spider for disease beigin!");
		Request request = new Request();
		request.putExtra("flag", 0);
		request.setUrl(beginUrl);
		Spider.create(xywyDiseaseListProcessor).addPipeline(xywyDiseaseListPipeline)
		.addRequest(request).thread(1).run();
		logger.info("spider for list done!");
	}
	
	public static void spiderPubmedOfList(){
		//target url @EXP
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
	
	public static void spiderOfDetail(){
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
	
	private static void init(){
		ac = new ClassPathXmlApplicationContext("spring-mybatis.xml");
		pubmedPipeline = (PubmedPipeline) ac.getBean("pubmedPipeline");
		pubmedDetailProcessor = (PubmedDetailProcessor) ac.getBean("pubmedDetailProcessor");
		pubmedListProcessor = (PubmedListProcessor) ac.getBean("pubmedListProcessor");
		pubmedMapper = (PubmedMapper)ac.getBean("pubmedMapper");
		xywyDiseaseListPipeline = ac.getBean(XywyDiseaseListPipeline.class);
		xywyDiseaseListProcessor = ac.getBean(XywyDiseaseListProcessor.class);
		xywyDiseaseUrlMapper = ac.getBean(XywyDiseaseUrlMapper.class);
		
		xywySymptomListPipeline = ac.getBean(XywySymptomListPipeline.class);
		xywySymptomListProcessor = ac.getBean(XywySymptomListProcessor.class);
		service = ac.getBean(SymptomService.class);
	}
	
	public static void symptomList(){
		Request beginRequest = new Request();
		beginRequest.setUrl("http://zzk.xywy.com/p/neike.html");
		beginRequest.putExtra("flag", 0);
		Spider.create(xywySymptomListProcessor).addPipeline(xywySymptomListPipeline).addRequest(beginRequest).thread(1).run();
	}
	
	public static void main(String[] args) {
		init();
		service.deleteAll();
//		symptomList();
	}

}
