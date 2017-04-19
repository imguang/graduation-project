package com.imguang.demo.neo4j.dao;


import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;

import com.imguang.demo.neo4j.entity.Disease;

public interface DiseaseRepository extends GraphRepository<Disease> {

	@Query("MATCH (d:Disease {id:{0}}) RETURN d")
	Disease getDiseaseFromId(String id);
	
}
