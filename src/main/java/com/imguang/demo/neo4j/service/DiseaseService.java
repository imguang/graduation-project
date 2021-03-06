package com.imguang.demo.neo4j.service;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.imguang.demo.neo4j.dao.DiseaseRepository;
import com.imguang.demo.neo4j.entity.Disease;

@Component
public class DiseaseService {
	
	@Autowired
	DiseaseRepository diseaseRepository;
	
	public Set<Disease> getByName(String name){
		return diseaseRepository.getDiseaseByDiseaseName(name);
	}
	
	public Disease create(Disease disease){
		return diseaseRepository.save(disease);
	}
	
	public void saveBath(List<Disease> diseases){
		diseaseRepository.save(diseases);
	}
	
	public Disease getDiseaseFromId(String id){
		return diseaseRepository.getDiseaseFromId(id);
	}
	
	public Disease getDiseaseFromGraphId(Long id){
		return diseaseRepository.findOne(id);
	}
	
	public List<Disease> getAll(){
		return (List<Disease>) diseaseRepository.findAll();
	}
	
	public Page<Disease> getByPage(Pageable pageable){
		Page<Disease> rePage = diseaseRepository.findAll(pageable);
		return rePage;
	}
	
	public List<Disease> getDiseaseFromMedicineId(String id){
		return diseaseRepository.getDiseaseFromMedicineId(id);
	}
}
