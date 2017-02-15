package com.imguang.demo.utils;

import java.util.UUID;

public class IdGenerator {

	public static String getUUID(){
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
	
	
}
