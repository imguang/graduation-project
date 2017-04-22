package com.imguang.demo.web.common;

import java.io.IOException;
import java.text.SimpleDateFormat;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.SerializerProvider;

@Component
public class MyObjectMapper extends ObjectMapper {

	private static final long serialVersionUID = 1L;

	public MyObjectMapper() {
		super();
		// 设置null不转换
		setSerializationInclusion(Include.NON_EMPTY);
		// 设置null转换
		getSerializerProvider().setNullValueSerializer(new NullSerializer());
		// 设置日期转换yyyy-MM-dd HH:mm:ss
		setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
		// 为了使JSON视觉上的可读性，增加一行如下代码，注意，在生产中不需要这样，因为这样会增大Json的内容
		configure(SerializationFeature.INDENT_OUTPUT, true);
		// 进行缩进输出
		configure(SerializationFeature.INDENT_OUTPUT, true);
	}

	// null的JSON序列
	private class NullSerializer extends JsonSerializer<Object> {

		public void serialize(Object value, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
			jgen.writeString("");
		}
	}

}
