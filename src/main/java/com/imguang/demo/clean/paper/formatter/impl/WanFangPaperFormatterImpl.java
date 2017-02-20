package com.imguang.demo.clean.paper.formatter.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;

import com.imguang.demo.clean.paper.TemPaper;
import com.imguang.demo.clean.paper.formatter.IPaperFormatter;
import com.imguang.demo.utils.IdGenerator;

/**
 * @author 书生
 * abandon
 */
public class WanFangPaperFormatterImpl implements IPaperFormatter {

	public final static String REGEXP_TI = "^T1 .*";
	public final static String REGEXP_AB = "^AB .*";
	public final static String REGEXP_AU = "^A1 .*";
	public final static String REGEXP_LK = "^UL .*";
	public final static String REGEXP_LA = "^LA .*";
	public final static String STORE_PATH = "F:\\gProject\\data\\formatted";

	public TemPaper saveOne(List<String> content) {
		if (content == null || content.size() == 0) {
			return null;
		}
		TemPaper temPaper = new TemPaper();
		for (String string : content) {
			if (string.matches(REGEXP_TI)) {
				temPaper.setTitle(string.substring(3).trim());
				continue;
			}
			if (string.matches(REGEXP_AB)) {
				temPaper.setAbContent(string.substring(3).trim());
				continue;
			}
			if (string.matches(REGEXP_AU)) {
				temPaper.setAuthor(string.substring(3).trim());
				continue;
			}
			if (string.matches(REGEXP_LK)) {
				temPaper.setLink_path(string.substring(3).trim());
				continue;
			}
			if (string.matches(REGEXP_LA)) {
				temPaper.setLan_type(string.substring(3).trim());
				continue;
			}
		}
		temPaper.setId(IdGenerator.getUUID());
		temPaper.setLocal_path(STORE_PATH);
		// System.out.println(temPaper);
		// @TODO 存储
		content.clear();
		return temPaper;
	}

	public List<TemPaper> doFormat(String urlPath) throws IOException {
		List<TemPaper> temPapers = new ArrayList<TemPaper>();
		List<String> lines = FileUtils.readLines(new File(urlPath), "UTF-8");
		List<String> temLines = new ArrayList<String>();
		int cnt = 0;
		for (String string : lines) {
			if (string.length() < 2) {
				continue;
			}
			if (string.substring(0, 2).equals("RT")) {
				TemPaper temPaper = saveOne(temLines);
				if (temPaper != null) {
					cnt++;
					temPapers.add(temPaper);
				}
			}
			temLines.add(string);
		}
		TemPaper temPaper = saveOne(temLines);
		if (temPaper != null) {
			cnt++;
			temPapers.add(temPaper);
		}
		System.out.println("共解析了" + cnt + "条数据！");
		return temPapers;
	}

}
