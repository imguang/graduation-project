package com.imguang.demo.neo4j.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.imguang.demo.neo4j.dao.SymptomRepository;
import com.imguang.demo.neo4j.entity.Medicine;
import com.imguang.demo.neo4j.entity.Symptom;

@Component
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
	
	public Symptom getSymptomFromId(String id){
		return symptomRepository.getSymptomFromId(id);
	}
	
	public Symptom getSymptomFromGraphId(Long id){
		return symptomRepository.findOne(id);
	}
	
	public List<Symptom> getAll(){
		return (List<Symptom>) symptomRepository.findAll();
	}
	
	public Page<Symptom> getByPage(Pageable pageable){
		return symptomRepository.findAll(pageable);
	}
}
