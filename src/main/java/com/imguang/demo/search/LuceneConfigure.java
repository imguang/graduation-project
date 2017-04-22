package com.imguang.demo.search;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apdplat.word.lucene.ChineseWordAnalyzer;

public class LuceneConfigure {
	// 创建索引库
	private Directory dir = null;
	// 创建分词器
	private Analyzer ana = null;

	public void init() {
		// 根据指定的路径创建索引库，如果路径不存在就会创建
		try {
			dir = FSDirectory.open(new File(LuceneConst.INDEX_DIR).toPath());
			ana = new ChineseWordAnalyzer();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Directory getDir() {
		return dir;
	}

	public Analyzer getAna() {
		return ana;
	}
}
