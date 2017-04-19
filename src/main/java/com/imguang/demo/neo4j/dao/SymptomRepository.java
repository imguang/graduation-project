package com.imguang.demo.neo4j.dao;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;

import com.imguang.demo.neo4j.entity.Symptom;

public interface SymptomRepository extends GraphRepository<Symptom>{

	@Query("MATCH (s:Symptom {id:{0}}) RETURN s")
	Symptom getSymptomFromId(String id);
}
