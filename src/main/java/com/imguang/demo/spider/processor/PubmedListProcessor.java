package com.imguang.demo.spider.processor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.imguang.demo.spider.Procceed;

import org.slf4j.Logger;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.utils.HttpConstant;

@Component
public class PubmedListProcessor implements PageProcessor {

	private static final String PAGENUMS_NAME = "EntrezSystem2.PEntrez.PubMed.Pubmed_ResultsPanel.Entrez_Pager.cPage";
	private static final String KEY_NAME = "EntrezSystem2.PEntrez.DbConnector.LastQueryKey";
	private static final String GOAL_URL = "https://www.ncbi.nlm.nih.gov/pubmed";

	public static final int FIRST_FLAG = 1;
	public static final int REAL_FLAG = 2;

	private Logger logger = LoggerFactory.getLogger("running-log");
	private Site site = Site.me().setTimeOut(20000)
			.setUserAgent(
					"Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.143 Safari/537.36")
			.setRetryTimes(3).setSleepTime(5000).setCycleRetryTimes(3);

	public Site getSite() {
		return site;
	}

	@SuppressWarnings("unchecked")
	public Request requestFactory(int nextPageNum, String lastQueryKey) {
		Request temRequest = new Request(GOAL_URL);
		temRequest.setMethod(HttpConstant.Method.POST);
		NameValuePair[] values = new NameValuePair[3];
		values[0] = new BasicNameValuePair("EntrezSystem2.PEntrez.PubMed.Pubmed_ResultsPanel.Entrez_Pager.CurrPage",
				String.valueOf(nextPageNum));
		values[1] = new BasicNameValuePair("EntrezSystem2.PEntrez.DbConnector.LastQueryKey", lastQueryKey);
		values[2] = new BasicNameValuePair("EntrezSystem2.PEntrez.DbConnector.Cmd", "PageChanged");
		Map nameValuePair = new HashMap();
		nameValuePair.put("nameValuePair", values);
		temRequest.setExtras(nameValuePair);
		temRequest.putExtra("flag", REAL_FLAG);
		return temRequest;
	}

	public void process(Page page) {
		/*
		 * method 1 int pageNum = (Integer)request.getExtra("pageNum"); String
		 * urlTemp = (String) request.getExtra("urlTemp"); logger.info("正在爬取第" +
		 * pageNum + "页内容\n"); List<String> results =
		 * page.getHtml().xpath("//input[@name='uid']/@value").all(); int reNum
		 * = results.size(); logger.info("爬取结果为("+ reNum +"):");
		 * logger.info(results.toString()+"\n");
		 * Procceed.pubidList.addAll(results); if(reNum >= 20){//满足爬取下一页条件
		 * pageNum ++; Request nextRequest = new Request();
		 * nextRequest.putExtra("pageNum", pageNum);
		 * nextRequest.putExtra("urlTemp", urlTemp);
		 * nextRequest.setUrl(urlTemp+pageNum);
		 * page.addTargetRequest(nextRequest); }
		 */
		// method2
		Html html = page.getHtml();
		String totPage = html.xpath("//input[@name='" + PAGENUMS_NAME + "']/@last").toString();
		String nowPage = html.xpath("//input[@name='" + PAGENUMS_NAME + "']/@value").toString();
		String lastQueryKey = html.xpath("//input[@name='" + KEY_NAME + "']/@value").toString();
		List<String> goalId = html.xpath("//dl[@class='rprtid']/dd/text()").all();
		int size = goalId.size();
		logger.info("totPage:" + totPage);
		logger.info("nowPage:" + nowPage);
		logger.info("lastQueryKey:" + lastQueryKey);
		logger.info("gain size:" + size);
		logger.info(goalId.toString());
		Procceed.pubidList.addAll(goalId);

		if (Integer.valueOf(nowPage) < Integer.valueOf(totPage)) {
			Request nextRequest = requestFactory(Integer.valueOf(nowPage) + 1, lastQueryKey);
			page.addTargetRequest(nextRequest);
			logger.info("add next request:"+nextRequest.toString());
		}

		page.setSkip(true);
	}

}
