package com.imguang.demo.clean.paper.formatter.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.imguang.demo.clean.paper.TemPaper;
import com.imguang.demo.clean.paper.formatter.IPaperFormatter;
import com.imguang.demo.utils.IdGenerator;

/**
 * @author 书生
 * cnki 论文格式解析
 * xml格式
 */
public class CnkiPaperFormatterImpl implements IPaperFormatter {

	public final static String STORE_PATH = "F:\\gProject\\data\\formatted";
	public final static String CNKI_PAPERURL = "http://www.cnki.net/KCMS/detail/detail.aspx?filename={1}&dbname={2}";
	
	//public final static String REGEXP_URL = "FileName=.*&DbName=.*";
//	http://epub.cnki.net/kns/detail/detail.aspx?FileName=ZGYL200708001017&DbName=CPFD2008
	private static Logger logger = LoggerFactory.getLogger("Formatter");

	private static SAXReader saxReader;

	public CnkiPaperFormatterImpl() {
		saxReader = new SAXReader();
	}

	/**
	 * 构造适用的url
	 * @param url
	 * @return
	 */
	public static String parseLinkPath(String url){
		int begin = url.indexOf("FileName=") + "FileName=".length();
		int end = url.indexOf("&DbName=");
		String fileName = url.substring(begin, end);
		String DbName = url.substring(end + "&DbName=".length(), url.length());
		return CNKI_PAPERURL.replace("{1}", fileName).replace("{2}",DbName);
	}
	
	
	/**
	 * 对单条数据做具体的解析
	 * @param element
	 * @return
	 */
	public static TemPaper splitOne(Element element){
		TemPaper temPaper = new TemPaper();
		temPaper.setId(IdGenerator.getUUID());
		temPaper.setTitle(element.elementText("Title"));
		temPaper.setAuthor(element.elementText("Author"));
		temPaper.setAbContent(element.elementText("Summary"));
		temPaper.setKey_word(element.elementText("Keyword"));
		temPaper.setLan_type("chi");
		temPaper.setLocal_path(STORE_PATH);
		temPaper.setLink_path(parseLinkPath(element.elementText("Link")));
		return temPaper;
	}
	
	/* 
	 * 将目标文件路径的文件，转化为特定格式 
	 */
	public List<TemPaper> doFormat(String urlPath) throws IOException {
		try {
			List<TemPaper> temPapers = new ArrayList<TemPaper>();
			Document document = saxReader.read(new File(urlPath));
			Element rootElement = document.getRootElement();
			List<Element> dataNodes = rootElement.elements("DATA");
			for (Element element : dataNodes) {
				temPapers.add(splitOne(element));
			}
			System.out.println(dataNodes.size());
		} catch (DocumentException e) {
			logger.debug(e.getMessage());
		}
		return null;
	}

}
