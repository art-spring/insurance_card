package com.example.card.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.example.card.entity.Policy;
import com.example.card.model.PolicyInfo;
import com.example.card.params.PolicySearchParam;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author caichunyi
 * @since 2017-03-13
 */
public interface PolicyService extends IService<Policy> {
    /**
     * 添加代理商对象，如果phone存在则返回false
     * @param policy
     * @return
     */
    boolean createByManager(Policy policy);

    Page<PolicyInfo> search(PolicySearchParam param);

    List<PolicyInfo> searchByIds(List<String> ids);

    boolean setRecordExport(List<String> ids);
}
