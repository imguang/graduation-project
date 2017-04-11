package com.imguang.demo.spider.pipeline;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.imguang.demo.mysql.dao.XywySymptomUrlMapper;
import com.imguang.demo.mysql.model.XywySymptomUrl;

import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

/**
 * @author 书生
 * 疾病链接处理
 */
@Component
public class XywySymptomListPipeline implements Pipeline {
	
	Logger log = LoggerFactory.getLogger(XywySymptomListPipeline.class);

	@Autowired
	XywySymptomUrlMapper xywySymptomUrlMapper;
	
	public void process(ResultItems resultItems, Task task) {
		List<String> urList = resultItems.get("links");
		List<XywySymptomUrl> XywySymptomUrls = new ArrayList<XywySymptomUrl>();
		for (String string : urList) {
			XywySymptomUrl XywySymptomUrl = new XywySymptomUrl();
			XywySymptomUrl.setUrl(string.trim());
			XywySymptomUrls.add(XywySymptomUrl);
		}
		xywySymptomUrlMapper.insertByBatch(XywySymptomUrls);
		log.info("插入" + XywySymptomUrls.size() + "条数据到数据库中");
	}

}
