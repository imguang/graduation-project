package com.imguang.demo.train.prepare.main;


import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.ansj.domain.Result;
import org.ansj.splitWord.analysis.ToAnalysis;
import org.apdplat.word.dictionary.DictionaryFactory;
import org.apdplat.word.util.WordConfTools;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;

import com.imguang.demo.train.prepare.handler.NewsTensiteHandler;
import com.imguang.demo.train.prepare.split.ANSJDoSplitImpl;
import com.imguang.demo.train.prepare.split.IDoSplit;
import com.imguang.demo.train.prepare.split.WordDoSplitImpl;
import com.imguang.demo.utils.ReplaceAndAddToHugeFile;
import com.sun.org.apache.xml.internal.security.Init;

public class main {

	public static final String BASE_PATH = "F:\\gProject\\corpus\\";
	
	public static Set<String> urlSet;
	
	public static IDoSplit dosplitImpl;

	/*
	 * 预处理
	 */
	public static void preDeal() throws IOException{
		ReplaceAndAddToHugeFile.replace(BASE_PATH + "news_tensite_xml.dat", BASE_PATH + "03.dat", "&", "&amp;",
				"<i>", "</i>");
		System.out.println("读写完毕");
	}
	
	/*
	 * xml解析
	 */
	public static void parseXml() throws Exception{
		SAXReader saxReader = new SAXReader();
		saxReader.setDefaultHandler(new NewsTensiteHandler(BASE_PATH+"result01.txt"));
		saxReader.setEncoding("UTF-8");
		saxReader.read(new File(BASE_PATH + "03.dat"));
	}
	
	/**
	 * 初始化
	 */
	public static void init(){
		urlSet = new HashSet<String>();
//		dosplitImpl = new WordDoSplitImpl();
		dosplitImpl = new ANSJDoSplitImpl();
	}
	
	public static void splitWord() throws Exception{
		dosplitImpl.spiltFile(BASE_PATH + "result01.txt", BASE_PATH + "train_set_tem.txt");
	}
	
	public static void main(String[] args) throws Exception {
		init();
		splitWord();
	}

}
