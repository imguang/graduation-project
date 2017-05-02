package com.imguang.test.service;

import java.io.IOException;

import org.apache.lucene.queryparser.classic.ParseException;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.imguang.demo.web.controller.vo.BaseSearchResultVO;
import com.imguang.demo.web.service.LuceneQueryService;
import com.imguang.demo.web.service.PaperQueryService;
import com.imguang.test.common.BaseJunit4Test;

public class QueryServiceTest extends BaseJunit4Test{

	@Autowired
	LuceneQueryService luceneQueryService;
	
	@Autowired
	PaperQueryService paperQueryService;
	
	@Test
	@Ignore
	public void LuceneQueryServiceTest() throws IOException, ParseException{
		BaseSearchResultVO baseSearchResultVO = luceneQueryService.queryByTerm("感冒");
		System.out.println(baseSearchResultVO);
	}
	
	@Test
	public void paperServiceTest() throws ParseException, IOException{
		paperQueryService.queryByItem("子痫早期");
	}
	
}
