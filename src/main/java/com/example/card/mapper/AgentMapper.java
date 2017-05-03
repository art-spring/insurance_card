package com.example.card.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.example.card.entity.Agent;
import com.example.card.params.AgentSearchParam;

import java.util.List;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author caichunyi
 * @since 2017-03-13
 */
public interface AgentMapper extends BaseMapper<Agent> {
    List<Agent> search(AgentSearchParam param);
}