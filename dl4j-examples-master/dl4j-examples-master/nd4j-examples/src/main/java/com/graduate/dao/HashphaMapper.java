package com.graduate.dao;

import com.graduate.entity.Hashpha;
import com.graduate.entity.HashphaExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface HashphaMapper {
    int countByExample(HashphaExample example);

    int deleteByExample(HashphaExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Hashpha record);

    int insertSelective(Hashpha record);

    List<Hashpha> selectByExampleWithBLOBs(HashphaExample example);

    List<Hashpha> selectByExample(HashphaExample example);

    Hashpha selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Hashpha record, @Param("example") HashphaExample example);

    int updateByExampleWithBLOBs(@Param("record") Hashpha record, @Param("example") HashphaExample example);

    int updateByExample(@Param("record") Hashpha record, @Param("example") HashphaExample example);

    int updateByPrimaryKeySelective(Hashpha record);

    int updateByPrimaryKeyWithBLOBs(Hashpha record);
}
