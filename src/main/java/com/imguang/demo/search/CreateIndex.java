package com.imguang.demo.search;

import java.io.File;
import java.io.IOException;
import java.sql.Date;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.AndFileFilter;
import org.apache.commons.io.filefilter.PrefixFileFilter;
import org.apache.commons.io.filefilter.SuffixFileFilter;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.TextField;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

/**
 * @author 书生 Lucene 生成索引
 */
public class CreateIndex {
	private static String INDEX_DIR = "C:\\Users\\书生\\Desktop\\毕设\\index";
	private static Analyzer analyzer = null;
	private static IndexWriterConfig indexWriterConfig = null;
	private static Directory directory = null;
	private static IndexWriter indexWriter = null;

	public CreateIndex() throws IOException {
		File indexFile = new File(INDEX_DIR);
		if (!indexFile.exists()) {
			indexFile.mkdirs();
		}
		directory = FSDirectory.open(indexFile.toPath());
		analyzer = new StandardAnalyzer();
		indexWriterConfig = new IndexWriterConfig(analyzer);
	}

	public static void addToIndexByUrl(String url) {
		try {
			indexWriter = new IndexWriter(directory, indexWriterConfig);
			File file = new File(url);
			String[] files = file
					.list(new AndFileFilter(new PrefixFileFilter("download"), new SuffixFileFilter(".txt")));
			for (String string : files) {
				String temContent = FileUtils.readFileToString(new File(url + "//" + string));
				Document document = new Document();
				document.add(new TextField("content", temContent, Store.YES));
				document.add(new TextField("path", url + "//" + string, Store.YES));
				indexWriter.addDocument(document);
			}
			indexWriter.commit();
			System.out.println("写入成功!共写入"+files.length+"条记录！");
		} catch (IOException e) {
			e.printStackTrace();// @TODO change to logger
		} finally {
			if (indexWriter != null) {
				try {
					indexWriter.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}

	
	
	
	public static String getINDEX_DIR() {
		return INDEX_DIR;
	}

	public static void setINDEX_DIR(String iNDEX_DIR) {
		INDEX_DIR = iNDEX_DIR;
	}

	public static Analyzer getAnalyzer() {
		return analyzer;
	}

	public static void setAnalyzer(Analyzer analyzer) {
		CreateIndex.analyzer = analyzer;
	}

	public static IndexWriterConfig getIndexWriterConfig() {
		return indexWriterConfig;
	}

	public static void setIndexWriterConfig(IndexWriterConfig indexWriterConfig) {
		CreateIndex.indexWriterConfig = indexWriterConfig;
	}

	public static Directory getDirectory() {
		return directory;
	}

	public static void setDirectory(Directory directory) {
		CreateIndex.directory = directory;
	}

	public static IndexWriter getIndexWriter() {
		return indexWriter;
	}

	public static void setIndexWriter(IndexWriter indexWriter) {
		CreateIndex.indexWriter = indexWriter;
	}

	public static void main(String[] args) throws IOException {
		CreateIndex createIndex = new CreateIndex();
		createIndex.addToIndexByUrl("C:\\Users\\书生\\Desktop\\毕设\\pubmed\\data");
	}

}
