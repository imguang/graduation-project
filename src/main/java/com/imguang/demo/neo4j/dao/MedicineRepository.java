package com.imguang.demo.neo4j.dao;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;

import com.imguang.demo.neo4j.entity.Medicine;

public interface MedicineRepository extends GraphRepository<Medicine> {

	@Query("MATCH (medicine:Medicine {id:{0}}) RETURN medicine")
	Medicine getMedicineFromId(String id);
}
