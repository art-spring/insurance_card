package com.example.card.service;

import com.example.card.entity.Agent;
import com.baomidou.mybatisplus.service.IService;

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
	
}
