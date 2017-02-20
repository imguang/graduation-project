package com.imguang.demo.clean.paper;

import java.io.IOException;
import java.util.List;

import com.imguang.demo.clean.paper.formatter.IPaperFormatter;
import com.imguang.demo.clean.paper.formatter.impl.CnkiPaperFormatterImpl;
import com.imguang.demo.clean.paper.formatter.impl.WOSPaperFormatterImpl;

public class Test {
	
	public static IPaperFormatter paperFormatter ;
	
	public static void init(){
//		paperFormatter = new CnkiPaperFormatterImpl();
		paperFormatter = new WOSPaperFormatterImpl();
	}

	public static void main(String[] args) throws IOException {
		/*
		 * IPaperFormatter pubmedPaperFormatter = new
		 * PubmedPaperFormatterImpl(); String urlPath =
		 * "C:\\Users\\书生\\Desktop\\毕设\\pubmed\\data\\download_7345221.txt";
		 * String tem = FileUtils.readFileToString(new File(urlPath), "utf-8");
		 * pubmedPaperFormatter.doFormat(tem);
		 */
	/*	String urlPath = "F:\\gProject\\data\\wanfang\\wanfang_02.txt";
		System.out.println(wanFangPaperFormatter.doFormat(urlPath));*/
		init();
//		String urlPath = "F:\\gProject\\data\\cnki\\CNKI_01.xml";
		String urlPath = "C:\\Users\\书生\\Desktop\\毕设\\WOS\\data\\download_1.txt";
		List<TemPaper> temPapers = paperFormatter.doFormat(urlPath);
		int cnt = 0;
		for (TemPaper temPaper : temPapers) {
			List<String> DOIs = temPaper.getCitedReferenceIds();
			if(DOIs == null || DOIs.size() <= 0)
				continue;
			for (String string : DOIs) {
				for (TemPaper tem: temPapers) {
					if(string.equals(tem.getDOI())){
						System.out.println(temPaper.getDOI()+"-->"+string);
						cnt ++;
						break;
					}
				}
			}
		}
		System.out.println(cnt);
	}

}
