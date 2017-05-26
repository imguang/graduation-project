package com.imguang.demo.neo4j.service;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.imguang.demo.neo4j.dao.PaperRepository;
import com.imguang.demo.neo4j.entity.Paper;

@Component
public class PaperService {

	@Autowired
	private PaperRepository paperRepository;
	
	public Set<Paper> getByTitle(String title){
		return paperRepository.findByTitle(title);
	}
	
	public void saveBath(Iterable<Paper> papers){
		paperRepository.save(papers);
	}
	
	public List<Paper> getAll(){
		return (List<Paper>) paperRepository.findAllPapers();
	}
	
	public Paper findById(Long id){
		return paperRepository.findById(id);
	}
	
	public List<Paper> findByTagAndPage(String tag,Integer start){
		return paperRepository.findByTag(tag, start * 10);
	}
}
