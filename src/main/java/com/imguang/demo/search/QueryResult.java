package com.imguang.demo.search;

import java.io.File;
import java.io.IOException;

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

public class QueryResult {

	public static final String CONTENT = "content";
	public static final String TITLE = "TI";
	public static final String ABSTRACT = "AB";
	public static final String PATH = "path";

	private static String INDEX_DIR = "C:\\Users\\书生\\Desktop\\毕设\\index";
	private static Analyzer analyzer = null;
	private static Directory directory = null;
	private static QueryParser queryParser = null;
	private static IndexSearcher indexSearcher = null;

	public QueryResult() throws IOException {
		analyzer = new StandardAnalyzer();
		directory = FSDirectory.open(new File(INDEX_DIR).toPath());
		queryParser = new QueryParser(CONTENT, analyzer);
	}

	public void search(String term) {
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
		queryResult.search("ischemic");
	}

}
