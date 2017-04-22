package com.imguang.demo.web.service;

import java.io.IOException;
import java.util.List;

import org.apache.lucene.queryparser.classic.ParseException;
import org.springframework.beans.factory.annotation.Autowired;

import com.imguang.demo.search.QueryUtil;
import com.imguang.demo.search.entity.HitsEntity;

public class LuceneQueryService {

	@Autowired
	private QueryUtil queryUtil;

	/**
	 * 查询
	 * 
	 * @param term
	 *            查询词
	 * @throws ParseException
	 * @throws IOException
	 */
	public void queryByTerm(String term) throws IOException, ParseException {
		/*
		 * 搜索策略
		 * 1.首先搜索名称如果搜到则返回
		 * 2.如果没有对应名称，则搜索对应属性
		 * 3.如果还是没有，则用Word2Vec发现新词 @TODO
		 */
		//first
		List<HitsEntity> hitsEntities = queryUtil.searchByPropertiesNameAndValue("name", term);
		if(hitsEntities.size() > 0){
			for (HitsEntity hitsEntity : hitsEntities) {
				
			}
		}
	
	}

}
