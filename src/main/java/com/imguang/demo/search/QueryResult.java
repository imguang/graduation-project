package com.imguang.demo.search;

import java.io.File;
import java.io.IOException;

import org.ansj.lucene5.AnsjAnalyzer;
import org.ansj.lucene5.AnsjAnalyzer.TYPE;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apdplat.word.lucene.ChineseWordAnalyzer;

public class QueryResult {

	public static final String CONTENT = "content";
	public static final String TITLE = "TI";
	public static final String ABSTRACT = "AB";
	public static final String PATH = "path";

	private static Analyzer analyzer = null;
	private static Directory directory = null;
	private static QueryParser queryParser = null;
	private static IndexSearcher indexSearcher = null;

	public QueryResult() throws IOException {
		analyzer = new ChineseWordAnalyzer();
		directory = FSDirectory.open(new File(LuceneConst.INDEX_DIR).toPath());
	}

	public void searchByPropertiesNameAndValue(String properties, String term) {
		queryParser = new QueryParser(properties, analyzer);
		DirectoryReader directoryReader = null;
		try {
			directoryReader = DirectoryReader.open(directory);
			indexSearcher = new IndexSearcher(directoryReader);
			Query query = queryParser.parse(term);
			ScoreDoc[] hits = indexSearcher.search(query, 10).scoreDocs;
			for (ScoreDoc scoreDoc : hits) {
				Document document = indexSearcher.doc(scoreDoc.doc);
				System.out.println(document.get("id") + "得分：" + scoreDoc.score + ",flag:" + document.get("flag")
						+ ",name:" + document.get("name"));
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} finally {
			try {
				if (directory != null) {
					directory.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void search(String term) {
		queryParser = new QueryParser(CONTENT, analyzer);
		DirectoryReader directoryReader = null;
		try {
			directoryReader = DirectoryReader.open(directory);
			indexSearcher = new IndexSearcher(directoryReader);
			Query query = queryParser.parse(term);
			ScoreDoc[] hits = indexSearcher.search(query, 10).scoreDocs;
			for (ScoreDoc scoreDoc : hits) {
				Document document = indexSearcher.doc(scoreDoc.doc);
				System.out.println(document.get(PATH) + "得分" + scoreDoc.score);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} finally {
			try {
				if (directory != null) {
					directory.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) throws IOException {
		QueryResult queryResult = new QueryResult();
		queryResult.searchByPropertiesNameAndValue("name", "胎盘");
	}

}
