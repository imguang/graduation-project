package com.imguang.demo.web.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.lucene.queryparser.classic.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.imguang.demo.neo4j.entity.Disease;
import com.imguang.demo.neo4j.entity.Medicine;
import com.imguang.demo.neo4j.entity.Symptom;
import com.imguang.demo.neo4j.service.DiseaseService;
import com.imguang.demo.neo4j.service.MedicineService;
import com.imguang.demo.neo4j.service.SymptomService;
import com.imguang.demo.search.QueryUtil;
import com.imguang.demo.search.entity.HitsEntity;
import com.imguang.demo.web.controller.vo.BaseSearchResultVO;
import com.imguang.demo.web.controller.vo.DiseaseSearchResultVO;
import com.imguang.demo.web.controller.vo.MedicineSearchResultVO;
import com.imguang.demo.web.controller.vo.SymptomSearchResultVO;

@Service
public class LuceneQueryService {

	private static Logger log = LoggerFactory.getLogger(LuceneQueryService.class);

	@Autowired
	private QueryUtil queryUtil;

	@Autowired
	private DiseaseService diseaseService;

	@Autowired
	private MedicineService medicineService;

	@Autowired
	private SymptomService symptomService;

	// abstract_content etiology treatment_detail prevent nursing function
	// conponent cause
	private static final String[] properties = { "abstract_content", "etiology", "treatment_detail", "prevent",
			"nursing", "function", "conponent", "cause" };

	
	/**
	 * 封装最高得分的实体
	 * @param hightest
	 * @return 
	 */
	private BaseSearchResultVO getFlagEntity(HitsEntity hightest){
		BaseSearchResultVO baseSearchResultVO = null;
		switch (hightest.getFlag()) {
		case "disease": {
			Disease disease = diseaseService.getDiseaseFromGraphId(hightest.getGraphId());
			DiseaseSearchResultVO diseaseSearchResultVO = new DiseaseSearchResultVO();
			baseSearchResultVO = diseaseSearchResultVO;
			diseaseSearchResultVO.setDisease(disease);
			break;
		}
		case "medicine":
			Medicine medicine = medicineService.getMedicineFromGraphId(hightest.getGraphId());
			MedicineSearchResultVO medicineSearchResultVO = new MedicineSearchResultVO();
			baseSearchResultVO = medicineSearchResultVO;
			medicineSearchResultVO.setMedicine(medicine);
			break;
		case "symptom":
			Symptom symptom = symptomService.getSymptomFromGraphId(hightest.getGraphId());
			SymptomSearchResultVO symptomSearchResultVO = new SymptomSearchResultVO();
			baseSearchResultVO = symptomSearchResultVO;
			symptomSearchResultVO.setSymptom(symptom);
			break;
		default:
			log.error("类型不存在！");
			break;
		}
		return baseSearchResultVO;
	}
	
	/**
	 * 获得可能感兴趣
	 * @param hitsEntities
	 * @return
	 */
	private List<String> getSearchRelations(List<HitsEntity> hitsEntities){
		List<String> searchRelations = new ArrayList<>();
		for (int i = 1; i < hitsEntities.size(); i++) {
			if(i > 5) //只要五个关联
				break;
			searchRelations.add(hitsEntities.get(i).getName());
		}
		return searchRelations;
	}
	
	/**
	 * 查询
	 * 
	 * @param term
	 *            查询词
	 * @throws ParseException
	 * @throws IOException
	 */
	public BaseSearchResultVO queryByTerm(String term) throws IOException, ParseException {

		BaseSearchResultVO baseSearchResultVO = null;

		/*
		 * 搜索策略 1.首先搜索名称如果搜到则返回 2.如果没有对应名称，则搜索对应属性 
		 */
		// first
		log.info("开始检索");
		List<HitsEntity> hitsEntities = queryUtil.searchByPropertiesNameAndValue("name", term);
		if (hitsEntities.size() > 0) {
			log.info("命中名称检索，检索到" + hitsEntities.size() + "条记录！");
			log.info(hitsEntities.toString());
			//得到最高得分，进行封装
			baseSearchResultVO = getFlagEntity(hitsEntities.get(0));
			baseSearchResultVO.setSearchRelations(getSearchRelations(hitsEntities));
			return baseSearchResultVO;
		}
		log.info("没有查询到实体，开始查询属性");
		// 2. 对相应属性进行查询
		// abstract_content etiology treatment_detail prevent nursing
		// function conponent cause pervent
		List<HitsEntity> hitsPropertiesEntity = new ArrayList<>();
		for (String property : properties) {
			List<HitsEntity> temHits = queryUtil.searchByPropertiesNameAndValue(property, term);
			log.info("查询" + property + "属性：" + temHits);
			for (HitsEntity hitsEntity : temHits) {
				if (hitsPropertiesEntity.contains(hitsEntity)) {
					for (HitsEntity hitsEntity2 : hitsPropertiesEntity) {
						if (hitsEntity2.equals(hitsEntity)) {
							hitsEntity2.setScore(hitsEntity.getScore() + hitsEntity2.getScore());
							break;
						}
					}
				} else {
					hitsPropertiesEntity.add(hitsEntity);
				}
			}
		}
		//排序
		Collections.sort(hitsPropertiesEntity);
		log.info("查询完毕：" + hitsPropertiesEntity);
		if(hitsPropertiesEntity.size() > 0){
			baseSearchResultVO = getFlagEntity(hitsPropertiesEntity.get(0));
			baseSearchResultVO.setSearchRelations(getSearchRelations(hitsPropertiesEntity));
		}
		return baseSearchResultVO;

	}
	/**
	 * 查询策略
	 * @param term
	 *            查询词
	 * @throws ParseException
	 * @throws IOException
	 */
	public BaseSearchResultVO queryByTerm2(String term) throws IOException, ParseException {
		
		BaseSearchResultVO baseSearchResultVO = null;
		
		/*
		 * 搜索策略 1.首先搜索名称如果搜到则返回 2.如果没有对应名称，则搜索对应属性
		 * 3.如果没有搜索到，则使用word2vec发现新词
		 * 实体排序使用PageRank算法
		 */
		// first
		log.info("开始检索");
		List<HitsEntity> hitsEntities = queryUtil.searchByPropertiesNameAndValue("name", term);
		if (hitsEntities.size() > 0) {
			log.info("命中名称检索，检索到" + hitsEntities.size() + "条记录！");
			log.info(hitsEntities.toString());
			//得到最高得分，进行封装
			baseSearchResultVO = getFlagEntity(hitsEntities.get(0));
			baseSearchResultVO.setSearchRelations(getSearchRelations(hitsEntities));
			return baseSearchResultVO;
		}
		log.info("没有查询到实体，开始查询属性");
		// 2. 对相应属性进行查询
		// abstract_content etiology treatment_detail prevent nursing
		// function conponent cause pervent
		List<HitsEntity> hitsPropertiesEntity = new ArrayList<>();
		for (String property : properties) {
			List<HitsEntity> temHits = queryUtil.searchByPropertiesNameAndValue(property, term);
			log.info("查询" + property + "属性：" + temHits);
			for (HitsEntity hitsEntity : temHits) {
				if (hitsPropertiesEntity.contains(hitsEntity)) {
					for (HitsEntity hitsEntity2 : hitsPropertiesEntity) {
						if (hitsEntity2.equals(hitsEntity)) {
							hitsEntity2.setScore(hitsEntity.getScore() + hitsEntity2.getScore());
							break;
						}
					}
				} else {
					hitsPropertiesEntity.add(hitsEntity);
				}
			}
		}
		//排序
		Collections.sort(hitsPropertiesEntity);
		log.info("查询完毕：" + hitsPropertiesEntity);
		if(hitsPropertiesEntity.size() > 0){
			baseSearchResultVO = getFlagEntity(hitsPropertiesEntity.get(0));
			baseSearchResultVO.setSearchRelations(getSearchRelations(hitsPropertiesEntity));
			return baseSearchResultVO;
		}
		//3 word2vec
		Word2VecService word2VecService = new Word2VecService();
		List<HitsEntity> word2vecHitsEntities = new ArrayList<>();
		List<String> items = word2VecService.findWords(term);
		for (String string : items) {
			word2vecHitsEntities.addAll(queryUtil.searchByPropertiesNameAndValue("name",string));
		}
		//排序
		Collections.sort(hitsPropertiesEntity);
		if(word2vecHitsEntities.size() > 0){
			baseSearchResultVO = getFlagEntity(word2vecHitsEntities.get(0));
			baseSearchResultVO.setSearchRelations(getSearchRelations(word2vecHitsEntities));
		}
		return baseSearchResultVO;
		
	}

}
