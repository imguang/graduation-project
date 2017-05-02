package com.imguang.demo.web.controller;

import java.io.IOException;

import org.apache.lucene.queryparser.classic.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.imguang.demo.web.common.controller.BaseController;
import com.imguang.demo.web.common.vo.Response;
import com.imguang.demo.web.controller.vo.BaseSearchResultVO;
import com.imguang.demo.web.service.LuceneQueryService;
import com.imguang.demo.web.service.PaperQueryService;
import com.imguang.demo.web.service.SplitWordsService;

@Controller
@RequestMapping("/search")
public class SearchController extends BaseController{
	
	@Autowired
	LuceneQueryService luceneQueryService;
	
	@Autowired
	PaperQueryService paperQueryService;
	
	@Autowired
	SplitWordsService splitWordsService;

	/**
	 * 关键词搜索
	 * @param words
	 * @return
	 * @throws ParseException 
	 * @throws IOException 
	 */
	@ResponseBody
	@RequestMapping("/item")
	public Response searchItem(@RequestParam("words")String words) throws IOException, ParseException{
		Long start = System.currentTimeMillis();
		System.out.println(System.currentTimeMillis() - start);
		System.out.println(words);
		BaseSearchResultVO basesearchResult = luceneQueryService.queryByTerm(words);
		basesearchResult.setPapers(paperQueryService.queryByItem(words));
		basesearchResult.setWords(splitWordsService.splitResult(words));
		System.out.println(System.currentTimeMillis() - start);
		return new Response().success(basesearchResult);
	}
	
}
