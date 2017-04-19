package com.imguang.demo.neo4j.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.imguang.demo.neo4j.dao.MedicineRepository;
import com.imguang.demo.neo4j.entity.Medicine;

@Component
public class MedicineService {
	
	@Autowired
	MedicineRepository medicineRepository;
	
	public Medicine create(Medicine medicine){
		return medicineRepository.save(medicine);
	}
	
	public void saveBath(List<Medicine> medicines){
		medicineRepository.save(medicines);
	}
	
	public Medicine getMedicineFromId(String id){
		return medicineRepository.getMedicineFromId(id);
	}
	
	public List<Medicine> getAll(){
		return (List<Medicine>) medicineRepository.findAll();
	}
	
	public Page<Medicine> getByPage(Pageable pageable){
		return medicineRepository.findAll(pageable);
	}
}
