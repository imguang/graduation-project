package com.imguang.demo.web.controller;


import java.io.IOException;

import org.apache.lucene.queryparser.classic.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.imguang.demo.neo4j.entity.Disease;
import com.imguang.demo.neo4j.service.DiseaseService;
import com.imguang.demo.web.common.vo.Response;
import com.imguang.demo.web.controller.vo.BaseSearchResultVO;
import com.imguang.demo.web.service.LuceneQueryService;

@Controller
@RequestMapping("/test")
public class Test {
	
	@Autowired
	DiseaseService diseaseService;
	
	@Autowired
	LuceneQueryService luceneQueryService;
	
	@ResponseBody
	@RequestMapping("/a")
	public Response test(@RequestParam("name")String name) throws IOException, ParseException{
		Long start = System.currentTimeMillis();
		System.out.println(System.currentTimeMillis() - start);
		System.out.println(name);
		BaseSearchResultVO basesearchResult = luceneQueryService.queryByTerm(name);
		System.out.println(System.currentTimeMillis() - start);
		return new Response().success(basesearchResult);
	}
	
//	@RequestMapping("/test")
//	@ResponseBody
//	public Response test(String a){
//		return new Response().success(a);
//	}
}
