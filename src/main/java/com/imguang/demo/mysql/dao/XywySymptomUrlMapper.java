package com.imguang.demo.mysql.dao;

import java.util.List;
import java.util.Map;

import com.imguang.demo.mysql.model.XywySymptomUrl;

public interface XywySymptomUrlMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(XywySymptomUrl record);

    int insertSelective(XywySymptomUrl record);

    XywySymptomUrl selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(XywySymptomUrl record);

    int updateByPrimaryKey(XywySymptomUrl record);
    
    void insertByBatch(List<XywySymptomUrl> XywySymptomUrls);
    
    List<XywySymptomUrl> selectByLimit(Map<String, Integer> map);
}