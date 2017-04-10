package com.imguang.demo.spider.pipeline;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.imguang.demo.dao.XywyDiseaseUrlMapper;
import com.imguang.demo.model.XywyDiseaseUrl;

import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

/**
 * @author 书生
 * 疾病链接处理
 */
@Repository
public class XywyDiseaseListPipeline implements Pipeline {
	
	Logger log = LoggerFactory.getLogger(XywyDiseaseListPipeline.class);

	@Autowired
	XywyDiseaseUrlMapper xywyDiseaseUrlMapper;
	
	public void process(ResultItems resultItems, Task task) {
		List<String> urList = resultItems.get("links");
		List<XywyDiseaseUrl> xywyDiseaseUrls = new ArrayList<XywyDiseaseUrl>();
		for (String string : urList) {
			XywyDiseaseUrl xywyDiseaseUrl = new XywyDiseaseUrl();
			xywyDiseaseUrl.setUrl(string.trim());
			xywyDiseaseUrls.add(xywyDiseaseUrl);
		}
		xywyDiseaseUrlMapper.insertByBatch(xywyDiseaseUrls);
		log.info("插入" + xywyDiseaseUrls.size() + "条数据到数据库中");
	}

}
