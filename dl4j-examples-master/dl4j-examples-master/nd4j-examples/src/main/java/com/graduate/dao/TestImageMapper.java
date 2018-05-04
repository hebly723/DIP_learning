package com.graduate.dao;

import com.graduate.entity.TestImage;
import com.graduate.entity.TestImageExample;
import com.graduate.entity.TestImageWithBLOBs;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TestImageMapper {
    int countByExample(TestImageExample example);

    int deleteByExample(TestImageExample example);

    int insert(TestImageWithBLOBs record);

    int insertSelective(TestImageWithBLOBs record);

    List<TestImageWithBLOBs> selectByExampleWithBLOBs(TestImageExample example);

    List<TestImage> selectByExample(TestImageExample example);

    int updateByExampleSelective(@Param("record") TestImageWithBLOBs record, @Param("example") TestImageExample example);

    int updateByExampleWithBLOBs(@Param("record") TestImageWithBLOBs record, @Param("example") TestImageExample example);

    int updateByExample(@Param("record") TestImage record, @Param("example") TestImageExample example);
}
