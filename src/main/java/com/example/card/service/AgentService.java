package com.example.card.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.example.card.entity.Agent;
import com.example.card.params.AgentSearchParam;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author caichunyi
 * @since 2017-03-13
 */
public interface AgentService extends IService<Agent> {
    Map<String, Integer> getNameIdMap();

    /**
     * 添加代理商对象，如果phone存在则返回false
     * @param agent
     * @return
     */
    boolean createByManager(Agent agent);

    Page<Agent> search(AgentSearchParam param);


}
