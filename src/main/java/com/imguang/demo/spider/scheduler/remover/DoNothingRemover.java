package com.imguang.demo.spider.scheduler.remover;

import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.scheduler.component.DuplicateRemover;

/**
 * @author imguang
 * 不进行去重
 *
 */
public class DoNothingRemover implements DuplicateRemover {

	public boolean isDuplicate(Request request, Task task) {
		return false;
	}

	public void resetDuplicateCheck(Task task) {

	}

	public int getTotalRequestsCount(Task task) {
		return 0;
	}

}
