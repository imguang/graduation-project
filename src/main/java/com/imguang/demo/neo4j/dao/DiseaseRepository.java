package com.imguang.demo.neo4j.dao;


import java.util.List;
import java.util.Set;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;

import com.imguang.demo.neo4j.entity.Disease;

public interface DiseaseRepository extends GraphRepository<Disease> {

	@Query("MATCH (d:Disease {id:{0}}) RETURN d")
	Disease getDiseaseFromId(String id);
	
	@Query("MATCH (d:Disease)-[]->(m:Medicine {id:{0}}) return d")
	List<Disease> getDiseaseFromMedicineId(String id);
	
	@Query("MATCH (d:Disease) where d.diseaseName contains {0} return d")
	Set<Disease> getDiseaseByDiseaseName(String name);
}
