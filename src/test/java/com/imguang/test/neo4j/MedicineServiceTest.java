package com.imguang.test.neo4j;

import java.io.IOException;

import org.apache.lucene.queryparser.classic.ParseException;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.imguang.demo.neo4j.dao.DiseaseRepository;
import com.imguang.demo.neo4j.entity.Disease;
import com.imguang.demo.neo4j.entity.Medicine;
import com.imguang.demo.neo4j.service.DiseaseService;
import com.imguang.demo.neo4j.service.MedicineService;
import com.imguang.demo.search.QueryUtil;
import com.imguang.demo.web.controller.vo.DiseaseSearchResultVO;
import com.imguang.test.common.BaseJunit4Test;

public class MedicineServiceTest extends BaseJunit4Test{
	
	@Autowired
	MedicineService medicineService;
	
	@Autowired
	DiseaseService diseaseService;

	@Autowired
	DiseaseRepository diseaseRepository;
	
	@Autowired
	QueryUtil queryUtil;
	
	@Test
	@Ignore
	public void getMedicineService(){
		Long start = System.currentTimeMillis();
		Disease disease = diseaseService.getDiseaseFromGraphId(new Long(2384));
		System.out.println(System.currentTimeMillis() - start);
		DiseaseSearchResultVO baseDiseaseSearchResultVO = new DiseaseSearchResultVO();
		baseDiseaseSearchResultVO.setDisease(disease);
		System.out.println(System.currentTimeMillis() - start);
	}
	
	@Test
	@Ignore
	public void searchItemTest() throws IOException, ParseException{
		queryUtil.searchByPropertiesNameAndValue("name", "胎盘");
		queryUtil.searchByPropertiesNameAndValue("abstract_content", "胎盘");
	}
	
	@Test
	@Ignore
	public void findMedicineTest() throws IOException, ParseException{
		Medicine medicine = medicineService.getMedicineFromGraphId(new Long(6852));
		System.out.println(medicine.getName());
		System.out.println(medicine.getDiseases().size());
	}
	
}
