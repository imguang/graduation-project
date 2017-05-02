package com.imguang.demo.web.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.queryparser.classic.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.imguang.demo.neo4j.entity.Paper;
import com.imguang.demo.neo4j.service.PaperService;
import com.imguang.demo.search.QueryUtil;
import com.imguang.demo.search.entity.HitsEntity;

@Service
public class PaperQueryService {

	private static Logger log = LoggerFactory.getLogger(PaperQueryService.class);

	@Autowired
	private QueryUtil queryUtil;
	
	@Autowired
	private PaperService paperService;
	
	public List<Paper> queryByItem(String term) throws ParseException, IOException{
		List<Paper> papers = new ArrayList<>();
		List<HitsEntity> hitsEntities = queryUtil.searchPaper(term);
		log.info("论文搜索得到：" + hitsEntities.size() + "条");
		for (HitsEntity hitsEntity : hitsEntities) {
			papers.add(paperService.findById(hitsEntity.getGraphId()));
		}
		return papers;
	}
	
}
