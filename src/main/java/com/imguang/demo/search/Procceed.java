package com.imguang.demo.search;

import java.io.IOException;
import java.util.ArrayList;

import org.ansj.lucene5.AnsjAnalyzer;
import org.ansj.lucene5.AnsjAnalyzer.TYPE;
import org.ansj.splitWord.analysis.IndexAnalysis;
import org.ansj.splitWord.analysis.ToAnalysis;
import org.apache.lucene.analysis.Analyzer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.imguang.demo.neo4j.entity.Symptom;
import com.imguang.demo.neo4j.service.DiseaseService;
import com.imguang.demo.neo4j.service.SymptomService;

public class Procceed {

	private static CreateIndex createIndex;
	private static Logger logger = LoggerFactory.getLogger(Procceed.class);
	private static ApplicationContext ac = null;
	private static DiseaseService diseaseService = null;
	private static SymptomService symptomService = null;
	private static Analyzer analyzer = new AnsjAnalyzer(TYPE.index_ansj);

	private static void init() {
		ac = new ClassPathXmlApplicationContext("spring-mybatis.xml");
		createIndex = ac.getBean(CreateIndex.class);
		diseaseService = ac.getBean(DiseaseService.class);
		symptomService = ac.getBean(SymptomService.class);
	}

	public static void main(String[] args) {
		init();
		try {
			createIndex.addAll();
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
