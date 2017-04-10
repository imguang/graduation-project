package com.imguang.demo.train.prepare.handler;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import org.dom4j.Element;
import org.dom4j.ElementHandler;
import org.dom4j.ElementPath;

import com.imguang.demo.train.prepare.main.main;
import com.sun.org.apache.bcel.internal.generic.NEW;

/**
 * @author 书生 处理news_tensite_xml.dat 文件的handler
 */
public class NewsTensiteHandler implements ElementHandler {

	public static int totCnt = 0;
	public static final String HEAL_REGEXP = "^.*(http://www.xinhuanet.com/health/|http://health.china.com/|http://sina.kangq.com/|http://163.39.net/|http://health.sohu.com/).*$";
	public static BufferedWriter bWriter;

	public NewsTensiteHandler(String outputFileName) throws IOException {
		bWriter = new BufferedWriter(new FileWriter(outputFileName));
	}

	public void onEnd(ElementPath arg0) {//标签结束时处理
		Element node = arg0.getCurrent();
		if (node.getName().equals("doc")) {//如果为doc标签内容
			String url = node.elementText("url");
			//main.urlSet.add(url);
			totCnt++;
			try {
				bWriter.write(node.elementText("contenttitle") + " " + node.elementText("content"));
				bWriter.newLine();
				bWriter.flush();
			} catch (IOException e) {
				System.out.println(e);
			}
			System.out.println("检测到第" + totCnt + "个！");
			node.detach();
		}
	}

	public void onStart(ElementPath arg0) {
	}

}
