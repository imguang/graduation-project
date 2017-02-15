package com.imguang.demo.train.prepare.split;

import java.io.File;

import org.apdplat.word.WordSegmenter;

/**
 * @author 书生
 * word分词框架进行分词
 */
public class WordDoSplitImpl implements IDoSplit{

	public void spiltFile(String inputFile, String outputFile) throws Exception {
		WordSegmenter.seg(new File(inputFile), new File(outputFile));
	}
	
}
