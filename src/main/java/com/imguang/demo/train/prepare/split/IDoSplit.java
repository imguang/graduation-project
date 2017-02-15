package com.imguang.demo.train.prepare.split;

/**
 * @author 书生
 * 分词类的接口
 */
public interface IDoSplit {

	/**
	 * 对文件进行分词
	 * @param inputFile
	 * @param outputFile
	 */
	public void spiltFile(String inputFile,String outputFile) throws Exception;
	
}
