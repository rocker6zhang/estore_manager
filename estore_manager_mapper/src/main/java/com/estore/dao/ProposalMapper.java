package com.estore.dao;

import com.estore.bean.Proposal;
import com.estore.bean.ProposalExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ProposalMapper {
    int countByExample(ProposalExample example);

    int deleteByExample(ProposalExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Proposal record);

    int insertSelective(Proposal record);

    List<Proposal> selectByExample(ProposalExample example);

    Proposal selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Proposal record, @Param("example") ProposalExample example);

    int updateByExample(@Param("record") Proposal record, @Param("example") ProposalExample example);

    int updateByPrimaryKeySelective(Proposal record);

    int updateByPrimaryKey(Proposal record);
}