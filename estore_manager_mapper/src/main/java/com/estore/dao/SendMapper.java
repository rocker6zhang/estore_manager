package com.estore.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.estore.bean.Send;
import com.estore.bean.SendExample;

public interface SendMapper {
    int countByExample(SendExample example);

    int deleteByExample(SendExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Send record);

    int insertSelective(Send record);

    List<Send> selectByExampleWithBLOBs(SendExample example);

    List<Send> selectByExample(SendExample example);

    Send selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Send record, @Param("example") SendExample example);

    int updateByExampleWithBLOBs(@Param("record") Send record, @Param("example") SendExample example);

    int updateByExample(@Param("record") Send record, @Param("example") SendExample example);

    int updateByPrimaryKeySelective(Send record);

    int updateByPrimaryKeyWithBLOBs(Send record);

    int updateByPrimaryKey(Send record);
}