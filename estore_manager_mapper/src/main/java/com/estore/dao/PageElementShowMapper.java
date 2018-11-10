package com.estore.dao;

import com.estore.bean.PageElementShow;
import com.estore.bean.PageElementShowExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PageElementShowMapper {
    int countByExample(PageElementShowExample example);

    int deleteByExample(PageElementShowExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(PageElementShow record);

    int insertSelective(PageElementShow record);

    List<PageElementShow> selectByExampleWithBLOBs(PageElementShowExample example);

    List<PageElementShow> selectByExample(PageElementShowExample example);

    PageElementShow selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") PageElementShow record, @Param("example") PageElementShowExample example);

    int updateByExampleWithBLOBs(@Param("record") PageElementShow record, @Param("example") PageElementShowExample example);

    int updateByExample(@Param("record") PageElementShow record, @Param("example") PageElementShowExample example);

    int updateByPrimaryKeySelective(PageElementShow record);

    int updateByPrimaryKeyWithBLOBs(PageElementShow record);

    int updateByPrimaryKey(PageElementShow record);
}