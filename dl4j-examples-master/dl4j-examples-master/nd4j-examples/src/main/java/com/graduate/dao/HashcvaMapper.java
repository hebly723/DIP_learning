package com.graduate.dao;

import com.graduate.entity.Hashcva;
import com.graduate.entity.HashcvaExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface HashcvaMapper {
    int countByExample(HashcvaExample example);

    int deleteByExample(HashcvaExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Hashcva record);

    int insertSelective(Hashcva record);

    List<Hashcva> selectByExampleWithBLOBs(HashcvaExample example);

    List<Hashcva> selectByExample(HashcvaExample example);

    Hashcva selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Hashcva record, @Param("example") HashcvaExample example);

    int updateByExampleWithBLOBs(@Param("record") Hashcva record, @Param("example") HashcvaExample example);

    int updateByExample(@Param("record") Hashcva record, @Param("example") HashcvaExample example);

    int updateByPrimaryKeySelective(Hashcva record);

    int updateByPrimaryKeyWithBLOBs(Hashcva record);
}
