package com.imguang.demo.mysql.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;

import com.imguang.demo.mysql.model.Pubmed;
import com.imguang.demo.mysql.model.PubmedExample;

public interface PubmedMapper {
    int countByExample(PubmedExample example);

    int deleteByExample(PubmedExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Pubmed record);

    int insertSelective(Pubmed record);

    List<Pubmed> selectByExample(PubmedExample example);

    Pubmed selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Pubmed record, @Param("example") PubmedExample example);

    int updateByExample(@Param("record") Pubmed record, @Param("example") PubmedExample example);

    int updateByPrimaryKeySelective(Pubmed record);

    int updateByPrimaryKey(Pubmed record);
}