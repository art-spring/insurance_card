package com.example.card.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.example.card.entity.Agent;
import com.example.card.entity.Joinin;
import com.example.card.enums.AccountCreateType;
import com.example.card.enums.ApplyType;
import com.example.card.enums.BindState;
import com.example.card.mapper.AgentMapper;
import com.example.card.mapper.JoininMapper;
import com.example.card.params.JoininSearchParam;
import com.example.card.service.JoininService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author caichunyi
 * @since 2017-03-13
 */
@Service
public class JoininServiceImpl extends ServiceImpl<JoininMapper, Joinin> implements JoininService {

    @Autowired
    private JoininMapper joininMapper;

    @Override
    public boolean createByManager(Joinin joinin) {
        Joinin param = new Joinin();
        param.setPhoneNumber(joinin.getPhoneNumber());
        Joinin searchEntity = this.joininMapper.selectOne(param);
        if (searchEntity == null) {
            joinin.setApplyType(ApplyType.DEFAULT.getKey());
            joinin.setStatus(BindState.APPLY.getKey());
            joinin.setCreateType(AccountCreateType.MAMANGER_ADD.getKey());
            joinin.setCreateTime(new Date());
            joinin.insert();
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Page<Joinin> search(JoininSearchParam param) {
        Page<Joinin> page = new Page<>(param.getPage(), param.getPageSize());
        page.setRecords(this.joininMapper.search(param));
        return page;
    }
}
