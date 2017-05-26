package com.imguang.demo.web.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.imguang.demo.utils.word2vec.Word2VEC;
import com.imguang.demo.utils.word2vec.domain.WordEntry;


public class Word2VecService {

	public static final String BASE_PATH = "F:\\gProject\\corpus\\";
	public Word2VEC word2vec;
	
	public Word2VecService() throws IOException {
		word2vec = new Word2VEC();
		word2vec.loadJavaModel(BASE_PATH + "wor2vec_trained.txt");
	}
	
	
	/**
	 * 使用word2vec发现新词
	 * @param word
	 * @return
	 */
	public List<String> findWords(String word){
		List<String> words = new ArrayList<>();
		Set<WordEntry> wordEntries = word2vec.distance(word);
		for (WordEntry wordEntry : wordEntries) {
			words.add(wordEntry.name);
		}
		return words;
	}
	
	/**
	 * 使用word2vec发现新词
	 * @param words
	 * @return
	 */
	public List<String> findWords(Set<String> words){
		List<String> re = new ArrayList<>();
		for (String string : words) {
			re.addAll(findWords(string));
		}
		return re;
	}
	
}
