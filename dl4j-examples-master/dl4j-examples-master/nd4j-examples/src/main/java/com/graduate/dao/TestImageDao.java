package com.graduate.dao;

import com.graduate.entity.TestImage;
import com.graduate.entity.TestImageExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TestImageDao {
    int countByExample(TestImageExample example);

    int deleteByExample(TestImageExample example);

    int insert(TestImage record);

    int insertSelective(TestImage record);

    List<TestImage> selectByExample(TestImageExample example);

    int updateByExampleSelective(@Param("record") TestImage record, @Param("example") TestImageExample example);

    int updateByExample(@Param("record") TestImage record, @Param("example") TestImageExample example);
}
