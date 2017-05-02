package com.imguang.demo.neo4j.dao;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;

import com.imguang.demo.neo4j.entity.Paper;

public interface PaperRepository extends GraphRepository<Paper> {

	@Query("MATCH (d:Paper) RETURN d")
	Iterable<Paper> findAllPapers();
	@Query("MATCH (d:Paper) where id(d)={0} return d")
	Paper findById(Long id);
	
}
