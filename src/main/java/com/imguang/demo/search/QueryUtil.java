package com.imguang.demo.search;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.lucene.analysis.Analyzer;
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

import com.imguang.demo.search.entity.HitsEntity;

public class QueryUtil {

	public static final String CONTENT = "content";
	public static final String TITLE = "TI";
	public static final String ABSTRACT = "AB";
	public static final String PATH = "path";

	private static Analyzer analyzer = null;
	private static Directory directory = null;
	private static IndexSearcher indexSearcher = null;
	private static DirectoryReader directoryReader = null;
	private static Map<String, QueryParser> queryParserMap ;
	private static final String[] properties = { "name","abstract_content", "etiology", "treatment_detail", "prevent",
			"nursing", "function", "conponent", "cause" };


	public void init() {
		analyzer = new ChineseWordAnalyzer();
		try {
			directory = FSDirectory.open(new File(LuceneConst.INDEX_DIR).toPath());
			directoryReader = DirectoryReader.open(directory);
		} catch (IOException e) {
			e.printStackTrace();
		}
		indexSearcher = new IndexSearcher(directoryReader);
		queryParserMap = new HashMap<>();
		for (String string : properties) {
			queryParserMap.put(string, new QueryParser(string, analyzer));
		}
	}

	public void destroy() {
		if (analyzer != null) {
			analyzer.close();
		}
		if (directory != null) {
			try {
				directory.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public List<HitsEntity> searchByPropertiesNameAndValue(String properties, String term)
			throws IOException, ParseException {
		QueryParser queryParser = queryParserMap.get(properties);
		Query query = queryParser.parse(term);
		ScoreDoc[] hits = indexSearcher.search(query, 10).scoreDocs;
		List<HitsEntity> hitsEntities = new ArrayList<>();
		for (ScoreDoc scoreDoc : hits) {
			HitsEntity hitsEntity = new HitsEntity(indexSearcher.doc(scoreDoc.doc), scoreDoc.score);
			hitsEntities.add(hitsEntity);
		}
		return hitsEntities;
	}

	public void search(String term) {
		QueryParser queryParser = new QueryParser(CONTENT, analyzer);
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

	public static void main(String[] args) throws IOException, ParseException {
		QueryUtil queryResult = new QueryUtil();
		queryResult.init();
		System.out.println(queryResult.searchByPropertiesNameAndValue("name", "感冒"));
//		queryResult.searchByPropertiesNameAndValue("abstract_content", "胎盘");
		queryResult.destroy();
	}

}
