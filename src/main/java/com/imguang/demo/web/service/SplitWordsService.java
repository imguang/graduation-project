package com.imguang.demo.web.service;

import java.util.ArrayList;
import java.util.List;

import org.apdplat.word.WordSegmenter;
import org.apdplat.word.segmentation.SegmentationAlgorithm;
import org.apdplat.word.segmentation.Word;
import org.springframework.stereotype.Service;

@Service
public class SplitWordsService {

	public List<String> splitResult(String item){
		List<String> words = new ArrayList<>();
		List<Word> list = WordSegmenter.segWithStopWords(item, SegmentationAlgorithm.BidirectionalMinimumMatching);
		for (Word word : list) {
			words.add(word.getText());
		}
		return words;
	}
	
}
