package com.imguang.demo.clean.paper.formatter.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.imguang.demo.neo4j.entity.Paper;
import com.imguang.demo.utils.StringUtils;

/**
 * @author 书生
 * WOS文档的解析
 * 基于制表符分割
 */
@Component
public class WOSPaperFormatterImplV2 {
	
	private static Logger logger = LoggerFactory.getLogger(WOSPaperFormatterImplV2.class);

	public Paper splitOne(Map<String, String> oneMap){
		Paper paper = new Paper();
		paper.setAbstracts(oneMap.get("Z4"));
		paper.setAbstractsEN(oneMap.get("AB"));
		paper.setAuthor(oneMap.get("Z2"));
		if(paper.getAuthor() == null || "".equals(paper.getAuthor())){
			paper.setAuthor(oneMap.get("AU"));
		} else {
			paper.setAuthorEN(oneMap.get("AU"));
		}
		paper.setBeginPage(StringUtils.parseInt(oneMap.get("BP")));
		paper.setCitedNum(StringUtils.parseInt(oneMap.get("Z9")));
		paper.setDOI(oneMap.get("DI"));
		paper.setKeyWords(oneMap.get("Z5"));
		paper.setKeyWordsEN(oneMap.get("DE"));
		paper.setPageNum(StringUtils.parseInt(oneMap.get("PG")));
		paper.setPublisher(oneMap.get("Z3"));
		paper.setPublisherEN(oneMap.get("SO"));
		paper.setPublishYear(StringUtils.parseInt(oneMap.get("PY")));
		paper.setReferences(oneMap.get("CR"));
		paper.setReferencesNum(StringUtils.parseInt(oneMap.get("NR")));
		paper.setTitle(oneMap.get("Z1"));
		if(paper.getTitle() == null || "".equals(paper.getTitle())){
			paper.setTitle(oneMap.get("TI"));
		} else{
			paper.setTitleEN(oneMap.get("TI"));
		}
//		logger.info(paper+"");
		return paper;
	}
	
	public static int ll = 0;
	
	public Set<Paper> doFormat(String urlPath) throws IOException {
		logger.info("begin:" + urlPath);
		BufferedReader bufferedReader = new BufferedReader(new FileReader(urlPath));
		String line;
		String[] keys = {};
		String[] values = {};
		Set<Paper> temPapers = new HashSet<>();
		int cnt = 0;
		while((line = bufferedReader.readLine()) != null ){
			cnt ++;
			values = line.split("\t");
			if(cnt == 1){
				keys = values;
				continue;
			}
			Map<String, String> paper = new HashMap<>();
			if(keys.length != values.length){
				logger.error("keyNum not equal valueNum,cnt:" + cnt + ",url:" + urlPath);
				continue;
			}
			for(int i=0;i < keys.length;i++){
				paper.put(keys[i], values[i]);
			}
			temPapers.add(splitOne(paper));
			ll++;
		}
		bufferedReader.close();
		logger.info("done,recordNum is " + ll);
		return temPapers;
	}
	
	/**
	 * 遍历
	 * @param url
	 * @throws IOException 
	 */
	public Set<Paper> eachMethod(String url) throws IOException{
		File file = new File(url);
		Set<Paper> papers = new HashSet<>();
		if(file.isDirectory()){
			File[] files = file.listFiles();
			for (File file2 : files) {
				if(file2.getName().startsWith("download")){
					papers.addAll(doFormat(file2.getAbsolutePath()));
				}
			}
			logger.info("共有：" + papers.size() + "条！");
		} 
		logger.info("url isn't a directory!");
		return papers;
	}
	
	public static void main(String[] args) throws IOException {
		String urlPath = "C:\\Users\\书生\\Desktop\\论文";
		WOSPaperFormatterImplV2 paperFormatterImplV2 = new WOSPaperFormatterImplV2();
		paperFormatterImplV2.eachMethod(urlPath);
	}

}
