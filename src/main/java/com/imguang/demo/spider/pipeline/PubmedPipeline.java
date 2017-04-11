package com.imguang.demo.spider.pipeline;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.imguang.demo.mysql.dao.PubmedMapper;
import com.imguang.demo.mysql.model.Pubmed;

import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

/**
 * @author imguang
 * 用于爬取后数据处理
 * 1.将基本数据存储到mysql
 * 2.将内容保存到文件中
 *
 */
@Component
public class PubmedPipeline implements Pipeline {

	public static String file_path = "C:\\Users\\书生\\Desktop\\毕设\\pubmed\\data\\";
	private Logger logger = LoggerFactory.getLogger("running-log");
	
	@Resource
	PubmedMapper pubmedMapper;
	
	public void process(ResultItems resultItems, Task task) {
		String context = resultItems.get("context");
		//logger.info(context);
		String pubId = resultItems.get("pubId");
		String file_name = file_path +"download_"+pubId+".txt";
		File file = new File(file_name);
		if(!file.exists()){
			try {
				file.createNewFile();
			} catch (IOException e) {
				logger.error(e.getMessage());
			}
		}
		try {
			FileWriter fileWriter = null;
			fileWriter = new FileWriter(file);
			if(fileWriter != null){
				BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
				bufferedWriter.write(context,0,context.length()-1);
				bufferedWriter.close();
			}
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
		logger.info("处理"+"pubId:"+pubId);
		logger.info("pubId:"+pubId+"写入文件成功！");
		Pubmed pubed = new Pubmed();
		pubed.setAddr(file_name);
		pubed.setPubmedId(pubId);
		pubmedMapper.insert(pubed);
		logger.info("pubId:"+pubId+"记录数据库成功！");
	}


}
