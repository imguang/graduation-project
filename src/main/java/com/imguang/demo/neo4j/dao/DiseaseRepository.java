package com.imguang.demo.neo4j.dao;


import java.util.List;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;

import com.imguang.demo.neo4j.entity.Disease;

public interface DiseaseRepository extends GraphRepository<Disease> {

	@Query("MATCH (d:Disease {id:{0}}) RETURN d")
	Disease getDiseaseFromId(String id);
	
	@Query("MATCH (d:Disease)-[]->(m:Medicine {id:{0}}) return d")
	List<Disease> getDiseaseFromMedicineId(String id);
}
