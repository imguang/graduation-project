package com.imguang.test.neo4j;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.imguang.demo.clean.paper.formatter.impl.WOSPaperFormatterImplV2;
import com.imguang.demo.neo4j.entity.Paper;
import com.imguang.demo.neo4j.service.PaperService;
import com.imguang.test.common.BaseJunit4Test;

public class PaperTest extends BaseJunit4Test{

	@Autowired
	PaperService paperService;
	
	@Autowired
	WOSPaperFormatterImplV2 wosPaperFormatterImplV2;
	
	@Test
	@Ignore
	public void addPaperTest() throws IOException{
		String urlPath = "C:\\Users\\书生\\Desktop\\论文";
		Set<Paper> papers = wosPaperFormatterImplV2.eachMethod(urlPath);
		paperService.saveBath(papers);
	}
	
	@Test
	public void addRelationTest(){
		List<Paper> papers = (List<Paper>) paperService.getAll();
		List<Paper> citedPapers = new ArrayList<>();
		for (Paper paper : papers) {
			if(paper.getCitedNum() != null && paper.getCitedNum() > 0){
				citedPapers.add(paper);
			}
		}
		int cnt = 0;
		for (Paper paper : papers) {
			if(paper.getReferencesNum() == null || paper.getReferencesNum() <= 0){
				continue;
			}
			String[] refernece = paper.getReferences().split(";");
			paper.setPapers(new HashSet<>());
			for (String string : refernece) {
				for (Paper citedPaper : citedPapers) {
					if(!string.contains(String.valueOf(citedPaper.getPublishYear()))){
						continue;
					}
					if(!string.contains(citedPaper.getPublisher())){
						continue;
					}
					String author = "";
					if(citedPaper.getAuthor() != null){
						author = citedPaper.getAuthor().split(";")[0].trim();
					}
					if(!string.contains(author)){
						continue;
					}
					paper.getPapers().add(citedPaper);
					cnt++;
					break;
				}
			}
		}
		paperService.saveBath(papers);
		System.out.println("re.........:" + cnt);
	}
	
}
