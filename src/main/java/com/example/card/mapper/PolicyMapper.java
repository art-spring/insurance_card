package com.example.card.mapper;

import com.baomidou.mybatisplus.plugins.Page;
import com.example.card.entity.Policy;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.example.card.model.PolicyInfo;
import com.example.card.params.PolicySearchParam;

import java.util.List;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author caichunyi
 * @since 2017-03-13
 */
public interface PolicyMapper extends BaseMapper<Policy> {
    List<Policy> search(Page<Policy> page, PolicySearchParam param);

    List<PolicyInfo> query(Page<PolicyInfo> page, PolicySearchParam param);

    List<PolicyInfo> queryByIds(List<String> ids);

    int setRecordExport(List<String> ids);
}