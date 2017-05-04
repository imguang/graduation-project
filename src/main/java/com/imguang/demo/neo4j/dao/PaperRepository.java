package com.imguang.demo.neo4j.dao;

import java.util.List;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;

import com.imguang.demo.neo4j.entity.Paper;

public interface PaperRepository extends GraphRepository<Paper> {

	@Query("MATCH (d:Paper) RETURN d")
	Iterable<Paper> findAllPapers();

	@Query("MATCH (d:Paper) where id(d)={0} return d")
	Paper findById(Long id);

	@Query("MATCH (n:Paper) where  n.tag contains {0} return n order by n.publishYear desc,n.citedNum desc skip {1} limit 10")
	List<Paper> findByTag(String tag,Integer start);

}
