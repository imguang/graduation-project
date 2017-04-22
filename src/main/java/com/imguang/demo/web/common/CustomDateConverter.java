package com.imguang.demo.web.common;

import org.springframework.core.convert.converter.Converter;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by yl1390 on 2017/3/15.
 */

    public class CustomDateConverter implements Converter<String, Date>{

        @Override
        public Date convert(String source) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                return format.parse(source);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                return null;
            }

        }

    }

