package com.example.card.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.example.card.entity.Agent;
import com.example.card.enums.AccountCreateType;
import com.example.card.enums.ApplyType;
import com.example.card.enums.BindState;
import com.example.card.mapper.AgentMapper;
import com.example.card.service.AgentService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author caichunyi
 * @since 2017-03-13
 */
@Service
public class AgentServiceImpl extends ServiceImpl<AgentMapper, Agent> implements AgentService {

    @Autowired
    private AgentMapper agentMapper;

    @Override
    public Map<String, Integer> getNameIdMap() {
        List<Agent> agents = this.selectList(new EntityWrapper<>(null));
        Map<String, Integer> agentMap = new HashMap<>();
        for (Agent agent : agents) {
            agentMap.put(agent.getName(), agent.getId());
        }
        return agentMap;
    }

    @Override
    public boolean createByManager(Agent agent) {

        Agent param = new Agent();
        param.setPhoneNumber(agent.getPhoneNumber());
        Agent searchEntity = this.agentMapper.selectOne(param);
        if (searchEntity == null) {
            agent.setApplyType(ApplyType.DEFAULT.getKey());
            agent.setState(BindState.APPLY.getKey());
            agent.setCreateType(AccountCreateType.MAMANGER_ADD.getKey());
            agent.insert();
            return true;
        } else {
            return false;
        }


    }


}
