package com.imguang.demo.web.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.imguang.demo.neo4j.entity.Disease;
import com.imguang.demo.neo4j.service.DiseaseService;
import com.imguang.demo.web.common.vo.Response;
import com.imguang.demo.web.controller.vo.DiseaseSearchResultVO;

@Controller
@RequestMapping("/test")
public class Test {
	
	@Autowired
	DiseaseService diseaseService;
	
	@ResponseBody
	@RequestMapping("/a")
	public Response test(){
		Long start = System.currentTimeMillis();
		DiseaseSearchResultVO baseDiseaseSearchResultVO = new DiseaseSearchResultVO();
		Disease disease = diseaseService.getDiseaseAndRelationFromId("2384");
		System.out.println(System.currentTimeMillis() - start);
		baseDiseaseSearchResultVO.setDisease(disease);
		System.out.println(System.currentTimeMillis() - start);
		return new Response().success(baseDiseaseSearchResultVO);
	}
	
//	@RequestMapping("/test")
//	@ResponseBody
//	public Response test(String a){
//		return new Response().success(a);
//	}
}
