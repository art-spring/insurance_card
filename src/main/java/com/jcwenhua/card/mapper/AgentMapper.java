package com.jcwenhua.card.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.jcwenhua.card.entity.Agent;
import com.jcwenhua.card.params.AgentSearchParam;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author caichunyi
 * @since 2017-03-13
 */
public interface AgentMapper extends BaseMapper<Agent> {
    List<Agent> search(Page<Agent> page, AgentSearchParam param);
}