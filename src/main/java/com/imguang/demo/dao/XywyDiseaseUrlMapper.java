package com.imguang.demo.dao;

import com.imguang.demo.model.XywyDiseaseUrl;
import com.imguang.demo.model.XywyDiseaseUrlExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface XywyDiseaseUrlMapper {
    int countByExample(XywyDiseaseUrlExample example);

    int deleteByExample(XywyDiseaseUrlExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(XywyDiseaseUrl record);

    int insertSelective(XywyDiseaseUrl record);

    List<XywyDiseaseUrl> selectByExample(XywyDiseaseUrlExample example);
    
    List<XywyDiseaseUrl> selectAll();

    XywyDiseaseUrl selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") XywyDiseaseUrl record, @Param("example") XywyDiseaseUrlExample example);

    int updateByExample(@Param("record") XywyDiseaseUrl record, @Param("example") XywyDiseaseUrlExample example);

    int updateByPrimaryKeySelective(XywyDiseaseUrl record);

    int updateByPrimaryKey(XywyDiseaseUrl record);
    
    void insertByBatch(List<XywyDiseaseUrl> records);
}