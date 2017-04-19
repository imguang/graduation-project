package com.imguang.demo.mysql.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.imguang.demo.mysql.model.XywyDiseaseUrl;
import com.imguang.demo.mysql.model.XywyDiseaseUrlExample;

public interface XywyDiseaseUrlMapper {
    int countByExample(XywyDiseaseUrlExample example);

    int deleteByExample(XywyDiseaseUrlExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(XywyDiseaseUrl record);

    int insertSelective(XywyDiseaseUrl record);

    List<XywyDiseaseUrl> selectByExample(XywyDiseaseUrlExample example);
    
    List<XywyDiseaseUrl> selectAll();
    
    List<XywyDiseaseUrl> selectByLimit(Map<String, Integer> map);

    XywyDiseaseUrl selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") XywyDiseaseUrl record, @Param("example") XywyDiseaseUrlExample example);

    int updateByExample(@Param("record") XywyDiseaseUrl record, @Param("example") XywyDiseaseUrlExample example);

    int updateByPrimaryKeySelective(XywyDiseaseUrl record);

    int updateByPrimaryKey(XywyDiseaseUrl record);
    
    void insertByBatch(List<XywyDiseaseUrl> records);
}