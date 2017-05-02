package com.imguang.demo.search;

import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Procceed {

	private static CreateIndex createIndex;
	private static Logger logger = LoggerFactory.getLogger(Procceed.class);
	private static ApplicationContext ac = null;

	private static void init() {
		ac = new ClassPathXmlApplicationContext("spring-mybatis.xml");
		createIndex = ac.getBean(CreateIndex.class);
	}

	public static void main(String[] args) {
		init();
		try {
			createIndex.addAll();
//			createIndex.addPaper();
//			createIndex.addToIndexByLable("symptom");
//			ArrayList<Symptom> arrayList = new ArrayList<>();
//			arrayList.add(symptomService.getSymptomFromId("3852"));
//			createIndex.addSymptom(arrayList);
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
		logger.info("程序停止");
	}

}
