package com.imguang.demo.clean.paper.formatter;

import java.io.IOException;
import java.util.List;

import com.imguang.demo.clean.paper.TemPaper;

public interface IPaperFormatter {

	public  List<TemPaper>  doFormat(String urlPath) throws IOException;
	
}
