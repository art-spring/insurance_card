package com.jcwenhua.card.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.jcwenhua.card.entity.Agent;
import com.jcwenhua.card.enums.AccountCreateType;
import com.jcwenhua.card.enums.ApplyType;
import com.jcwenhua.card.enums.BindState;
import com.jcwenhua.card.mapper.AgentMapper;
import com.jcwenhua.card.params.AgentSearchParam;
import com.jcwenhua.card.service.AgentService;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
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
            agent.setStatus(BindState.APPLY.getKey());
            agent.setCreateType(AccountCreateType.MAMANGER_ADD.getKey());
            agent.setCreateTime(new Date());
            agent.insert();
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Page<Agent> search(AgentSearchParam param) {
        Page<Agent> page = new Page<>(param.getPage(), param.getPageSize());
        page.setRecords(this.agentMapper.search(page, param));
        return page;
    }

    @Override
    public boolean checkOpenId(String openId) {
        Map<String, Object> map = new HashedMap();
        map.put("wx_id", openId);
        List<Agent> tmp = agentMapper.selectByMap(map);
        if (tmp.size() == 1) {
            return true;
        }
        return false;
    }


}
