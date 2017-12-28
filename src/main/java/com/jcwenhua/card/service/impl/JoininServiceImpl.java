package com.jcwenhua.card.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.jcwenhua.card.entity.Joinin;
import com.jcwenhua.card.enums.AccountCreateType;
import com.jcwenhua.card.enums.ApplyType;
import com.jcwenhua.card.enums.BindState;
import com.jcwenhua.card.mapper.JoininMapper;
import com.jcwenhua.card.params.JoininSearchParam;
import com.jcwenhua.card.service.JoininService;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

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
        page.setRecords(this.joininMapper.search(page,param));
        return page;
    }

    @Override
    public boolean checkOpenId(String openId) {
        Map<String, Object> map = new HashedMap();
        map.put("wx_id", openId);
        List<Joinin> tmp = joininMapper.selectByMap(map);
        if (tmp.size() == 1) {
            return true;
        }
        return false;
    }
}
