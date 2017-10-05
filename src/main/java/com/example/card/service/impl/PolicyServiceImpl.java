package com.example.card.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.example.card.entity.Policy;
import com.example.card.mapper.PolicyMapper;
import com.example.card.model.PolicyInfo;
import com.example.card.params.PolicySearchParam;
import com.example.card.service.PolicyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author caichunyi
 * @since 2017-03-13
 */
@Service
public class PolicyServiceImpl extends ServiceImpl<PolicyMapper, Policy> implements PolicyService {

    @Autowired
    private PolicyMapper policyMapper;

    @Override
    public boolean createByManager(Policy policy) {
        policy.setCreateTime(new Date());
        policy.setExportStatus(0);
        return policy.insert();
    }

    @Override
    public Page<PolicyInfo> search(PolicySearchParam param) {
        Page<PolicyInfo> page = new Page<>(param.getPage(), param.getPageSize());
        page.setRecords(this.policyMapper.query(page,param));
        return page;
    }

    @Override
    public List<PolicyInfo> searchByIds(List<String> ids) {
        return policyMapper.queryByIds(ids);
    }

    @Override
    public boolean setRecordExport(List<String> ids) {
        if (policyMapper.setRecordExport(ids) > 0){
            return true;
        }
        return false;
    }
}
