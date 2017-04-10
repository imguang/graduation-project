package com.imguang.demo.clean.paper.formatter.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;

import com.imguang.demo.clean.paper.TemPaper;
import com.imguang.demo.clean.paper.formatter.IPaperFormatter;
import com.imguang.demo.utils.IdGenerator;

/**
 * @author 书生
 * WOS文档的解析
 * 基于EndNote
 */
public class WOSPaperFormatterImpl implements IPaperFormatter {

	public static final String LINE_REGEXP = "^[A-Z][A-Z1-9].*$";
	public static final int DOI_PREFIX = "DOI ".length();
	public static int cnt_null_DI = 0;

	/**
	 * @param list
	 * @return
	 * 提取出引用论文的DOI值
	 */
	public static List<String> drawCRDOI(List<String> list){
		if(list == null || list.size() <= 0){
			return null;
		}
		List<String> DOIs = new ArrayList<String>();
		for (String string : list) {
			int beginIndex = string.lastIndexOf("DOI ");
			if(beginIndex == -1){
				continue;
			}
			DOIs.add(string.substring(beginIndex + DOI_PREFIX).trim());
		}
		return DOIs;
	}
	
	/**
	 * @param list
	 * @return
	 * 获得本文章的DOI值
	 */
	public static String getDOI(List<String> list){
		if(list == null || list.size() <= 0){
			cnt_null_DI++;
			return null;
		}
		return list.get(0).trim();
	}
	
	/**
	 * @param list
	 * @return
	 * 从list中第一个转成Integer
	 */
	public static Integer getIntegerFromList(List<String> list){
		if(list == null || list.size() <= 0){
			return null;
		}
		return Integer.valueOf(list.get(0).trim());
	}
	
	public TemPaper splitOne(Map<String, List<String>> oneMap){
		if(oneMap == null || oneMap.isEmpty()){
			return null;
		}
		TemPaper temPaper = new TemPaper();
		temPaper.setId(IdGenerator.getUUID());
		temPaper.setTitle(StringUtils.join(oneMap.get("TI"),' '));
		temPaper.setAbContent(StringUtils.join(oneMap.get("AB"),' '));
		temPaper.setAuthor(StringUtils.join(oneMap.get("AU"),';'));
		temPaper.setKey_word(StringUtils.join(oneMap.get("ID"),' '));
		temPaper.setLan_type(StringUtils.join(oneMap.get("LA"),' '));
		temPaper.setLink_path(null);
		temPaper.setLocal_path("tem");
		temPaper.setCitedReferenceIds(drawCRDOI(oneMap.get("CR")));
		temPaper.setDOI(getDOI(oneMap.get("DI")));
		temPaper.setTimesCited(getIntegerFromList(oneMap.get("TC")));
		temPaper.setPublishYear(getIntegerFromList(oneMap.get("PY")));
		System.out.println(temPaper);
		return temPaper;
	}
	
	public List<TemPaper> doFormat(String urlPath) throws IOException {
		List<TemPaper> temPapers = new ArrayList<TemPaper>();
		List<String> lines = FileUtils.readLines(new File(urlPath),"UTF-8");
		String nowName = null;
		List<String> nowList = new ArrayList<String>();
		Map<String, List<String>> nowMap = new HashMap<String, List<String>>();
		int cnt = 0;
		int size = lines.size();
		for(int i=0;i < size;i++){//逐行分析
			String oneLine = lines.get(i);
			if(oneLine.equals("") || oneLine.length() < 2){
				continue;
			}
			String name = oneLine.substring(0, 2);
			String content = oneLine.substring(2).trim();
			if(oneLine.matches(LINE_REGEXP)){
				nowName = name;
				nowList = new ArrayList<String>();
				nowList.add(content);
				if(name.equals("FN")){//文件开始
					System.out.println("file name is:"+content.trim());
					continue;
				}
				if (name.equals("VR")) {
					System.out.println("version num is:" + content);
					continue;
				}
				if (name.equals("PT")) {//一条记录的开始
					cnt ++;
					System.out.println("the " + cnt + " record is begin");
					continue;
				}
				if (name.equals("ER")) {//一条记录结束
					TemPaper temPaper = splitOne(nowMap);
					if(temPaper != null){
						temPapers.add(temPaper);
					}
					nowMap.clear();
					System.out.println("the " + cnt + " record is end");
					continue;
				}
				if(name.equals("EF")){//文件结束
					System.out.println("deal done!");
					break;
				}
			} else {
				nowList.add(content);
			}
			if(lines.get(i+1).matches(LINE_REGEXP)){
				nowMap.put(nowName, nowList);
			}
		}
		System.out.println("共 "+temPapers.size()+" 条！");
		return temPapers;
	}

	public static void main(String[] args) {
		String tem = "﻿FN".trim();
		String tem3 = "FN".trim();
		System.out.println(Integer.valueOf(tem.charAt(0)));
		System.out.println(tem3.length());
		System.out.println(tem3.equals(tem));
		String tem4 = "ER";
		System.out.println(tem4.length());
		System.out.println(tem4.equals("ER"));
//		String tem2 = "   Labazzo, Kristen";
//		System.out.println(tem.matches(LINE_REGEXP));
//		System.out.println(tem2.matches(LINE_REGEXP));
	}
	
}
