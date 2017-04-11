package com.imguang.demo.neo4j.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.imguang.demo.neo4j.dao.SymptomRepository;
import com.imguang.demo.neo4j.entity.Symptom;

@Repository
public class SymptomService {
	
	@Autowired
	SymptomRepository symptomRepository;
	
	public Symptom create(Symptom symptom){
		return symptomRepository.save(symptom);
	}
	
	public void saveBatch(List<Symptom> symptoms){
		symptomRepository.save(symptoms);
	}
	
	public void deleteAll(){
		symptomRepository.deleteAll();
	}
}
