package com.imguang.demo.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

public class ReplaceAndAddToHugeFile {

	public static void replace(String fromFileName, String toFileName, String replacement, String target, String begin,
			String end) throws IOException {
		BufferedReader bufferedReader = new BufferedReader(
				new InputStreamReader(new FileInputStream(fromFileName), "GBK"));
		BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(toFileName));
		if (begin != null && !"".equals(begin)) {
			bufferedWriter.write(begin);
			bufferedWriter.newLine();
		}
		String tem;
		while ((tem = bufferedReader.readLine()) != null) {
			tem = tem.replace("&", "&amp;");

			bufferedWriter.write(tem);
			bufferedWriter.newLine();
		}
		if (end != null && !"".equals(end)) {
			bufferedWriter.write(end);
			bufferedWriter.newLine();
		}
		bufferedWriter.flush();
		bufferedWriter.close();
		bufferedReader.close();
	}

}
