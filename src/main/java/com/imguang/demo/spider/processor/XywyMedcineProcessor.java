package com.imguang.demo.spider.processor;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.imguang.demo.neo4j.entity.Medicine;
import com.imguang.demo.spider.common.SpiderConstant;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Selectable;

/**
 * @author 书生
 * 爬取药物
 */
public class XywyMedcineProcessor implements PageProcessor {
	
	private static Logger log = LoggerFactory.getLogger(XywyDiseaseListProcessor.class);

	@Override
	public void process(Page page) {
		Medicine medicine = (Medicine) page.getRequest().getExtra("medicine");
		//图片
		medicine.setImgUrl(page.getHtml().xpath("//li[@class='cur']/img").toString());
		//价格
		medicine.setPrice(page.getHtml().xpath("//font[@class='font2 fHei']/text()").toString().trim());
		List<Selectable> nodes = page.getHtml().xpath("//div[@id='smsList']/ul").nodes();
		medicine.setName(nodes.get(0).xpath("//li[2]/text()").toString().trim());
		nodes.remove(0);
		for (Selectable selectable : nodes) {//各种信息
			log.info(selectable.toString());
			String className = selectable.xpath("//li[1]/a/@name").toString();
			String value = selectable.xpath("//li[2]/text()").toString().trim();
			switch (className) {
			case "zhuzhi":
				medicine.setFunction(value);
				break;
			case "jiliang":
				medicine.setUsageDosage(value);
				break;
			case "fanying":
				medicine.setUntowardEffect(value);
				break;
			case "jinji":
				medicine.setContraindication(value);
				break;
			case "zhuyi":
				medicine.setMattersNeedAttention(value);
				break;
			case "chengfen":
				medicine.setConponent(value);
				break;
			case "zuoyong":
				medicine.setInteraction(value);
				break;
			default:
				break;
			}
		}
	}

	@Override
	public Site getSite() {
		return SpiderConstant.SITE;
	}

	public static void main(String[] args) {
		String beginUrl = "http://yao.xywy.com/goods/4977.htm";
		Medicine medicine = new Medicine();
		medicine.setId(beginUrl.substring(26, beginUrl.length()-4));
		Request request = new Request(beginUrl);
		request.putExtra("medicine", medicine);
		Spider spider = Spider.create(new XywyMedcineProcessor()).addRequest(request).thread(1);
		spider.run();
		spider.close();
		log.info(medicine.toString());
	}
	
}
