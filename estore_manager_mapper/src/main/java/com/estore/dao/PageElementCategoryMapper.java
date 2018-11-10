package com.estore.dao;

import com.estore.bean.PageElementCategory;
import com.estore.bean.PageElementCategoryExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PageElementCategoryMapper {
    int countByExample(PageElementCategoryExample example);

    int deleteByExample(PageElementCategoryExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(PageElementCategory record);

    int insertSelective(PageElementCategory record);

    List<PageElementCategory> selectByExample(PageElementCategoryExample example);

    PageElementCategory selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") PageElementCategory record, @Param("example") PageElementCategoryExample example);

    int updateByExample(@Param("record") PageElementCategory record, @Param("example") PageElementCategoryExample example);

    int updateByPrimaryKeySelective(PageElementCategory record);

    int updateByPrimaryKey(PageElementCategory record);
}