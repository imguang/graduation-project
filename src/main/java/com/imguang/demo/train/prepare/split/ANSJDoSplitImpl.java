package com.imguang.demo.train.prepare.split;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;

import org.ansj.domain.Result;
import org.ansj.splitWord.analysis.ToAnalysis;

public class ANSJDoSplitImpl implements IDoSplit {

	public void spiltFile(String inputFile, String outputFile) throws Exception {
		BufferedReader bufferedReader = new BufferedReader(new FileReader(inputFile));
		BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(outputFile));
		String tem;
		Result lists;
		while((tem = bufferedReader.readLine()) != null){
			lists = ToAnalysis.parse(tem);
			bufferedWriter.write(lists.toStringWithOutNature(" "));
			bufferedWriter.newLine();
		}
		bufferedReader.close();
		bufferedWriter.flush();
		bufferedWriter.close();
		
	}

}
