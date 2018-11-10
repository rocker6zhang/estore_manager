package com.estore.dao;

import com.estore.bean.PageElement;
import com.estore.bean.PageElementExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PageElementMapper {
    int countByExample(PageElementExample example);

    int deleteByExample(PageElementExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(PageElement record);

    int insertSelective(PageElement record);

    List<PageElement> selectByExampleWithBLOBs(PageElementExample example);

    List<PageElement> selectByExample(PageElementExample example);

    PageElement selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") PageElement record, @Param("example") PageElementExample example);

    int updateByExampleWithBLOBs(@Param("record") PageElement record, @Param("example") PageElementExample example);

    int updateByExample(@Param("record") PageElement record, @Param("example") PageElementExample example);

    int updateByPrimaryKeySelective(PageElement record);

    int updateByPrimaryKeyWithBLOBs(PageElement record);

    int updateByPrimaryKey(PageElement record);
}