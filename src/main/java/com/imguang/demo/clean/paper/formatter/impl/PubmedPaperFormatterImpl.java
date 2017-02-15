package com.imguang.demo.clean.paper.formatter.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.imguang.demo.clean.paper.TemPaper;
import com.imguang.demo.clean.paper.formatter.IPaperFormatter;

/**
 * @author 书生
 * pubmed 格式化实现类
 *
 */
public class PubmedPaperFormatterImpl implements IPaperFormatter {

	public final static String TITLE_BEGIN = "TI  - ";
	public final static String TITLE_END = "PG  - ";
	public final static String AB_BEGIN = "AB  - ";
	public final static String AB_END = "FAU - ";
	
	public List<TemPaper> doFormat(String wholeInfo) throws IOException {
		List<TemPaper> temPapers = new ArrayList<TemPaper>();
		TemPaper temPaper = new TemPaper();
		temPaper.setTitle(extractMethod(wholeInfo, TITLE_BEGIN, TITLE_END));
		temPaper.setAbContent(extractMethod(wholeInfo, AB_BEGIN, AB_END));
		temPaper.setLan_type("EN");
		System.out.println(temPaper);
		return null;
	}
	
	public String extractMethod(String wholeInfo,String begin,String end){
		int numBegin = begin.length();
		int beginIndex = wholeInfo.indexOf(begin);
		int endIndex = wholeInfo.indexOf(end);
		if(beginIndex >= 0 && endIndex >= 0 && beginIndex + numBegin <= endIndex){
			return wholeInfo.substring(beginIndex + numBegin, endIndex).replace('\n', ' ');
		} else {
			return "no exist!";
		}
	}

}
