package com.imguang.demo.dao;

import com.imguang.demo.model.Pubmed;
import com.imguang.demo.model.PubmedExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

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