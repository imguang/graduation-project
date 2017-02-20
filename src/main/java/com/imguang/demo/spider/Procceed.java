package com.imguang.demo.spider;

import static org.hamcrest.CoreMatchers.nullValue;

import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.imguang.demo.dao.PubmedMapper;
import com.imguang.demo.spider.pipeline.PubmedPipeline;
import com.imguang.demo.spider.processor.PubmedDetailProcessor;
import com.imguang.demo.spider.processor.PubmedListProcessor;
import com.imguang.demo.spider.scheduler.remover.DoNothingRemover;

import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.scheduler.QueueScheduler;

public class Procceed {

	public static final String BASE_URL_MEDLINE = "https://www.ncbi.nlm.nih.gov/pubmed/{1}?report=medline&format=text";
	
	public static Logger logger = LoggerFactory.getLogger("com.imguang.demo.procceed");
	public static List<String> pubidList = new LinkedList<String>();

	public static PubmedPipeline pubmedPipeline = null;
	private static ApplicationContext ac = null;
	public static PubmedDetailProcessor pubmedDetailProcessor = null;
	public static PubmedListProcessor pubmedListProcessor = null;
	
	public static PubmedMapper pubmedMapper = null;
	
	public static String medline_factory(String pubid){
		String temUrl = "";
		temUrl = BASE_URL_MEDLINE.replaceFirst("\\{1\\}", pubid);
		return temUrl;
	}
	
	public static void spiderOfList(){
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
	}
	
	public static void main(String[] args) {
		// String keyWord="ischemic placental disease";
		// rule1
		// http://www.medlive.cn/pubmed/pubmed_search.do?page=34&q=ischemic+placental+disease
		// gain pubmedId method1
		// String temUrl =
		// "http://www.medlive.cn/pubmed/pubmed_search.do?q=ischemic+placental+disease&page=";
		// logger.info("spider begin!");
		// Request request = new Request();
		// request.setUrl(temUrl+"1");
		// request.putExtra("pageNum", 1);
		// request.putExtra("urlTemp", temUrl);
		// Spider pubmedListSpider = Spider.create(new
		// PubmedListProcessor()).thread(1);
		// pubmedListSpider.addRequest(request).run();
		// pubmedListSpider.close();
		// //
		// int totNum = pubidList.size();
		// logger.info("爬取结束，共爬取" + totNum + "条数据！\n");
		// logger.info("spider end!");
		// method 2
		init();
		spiderOfList();
		//test
		spiderOfDetail();
		
	}

}
