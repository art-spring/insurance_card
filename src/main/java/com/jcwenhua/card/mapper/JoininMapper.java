package com.jcwenhua.card.mapper;

import com.baomidou.mybatisplus.plugins.Page;
import com.jcwenhua.card.entity.Joinin;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.jcwenhua.card.params.JoininSearchParam;

import java.util.List;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author caichunyi
 * @since 2017-03-13
 */
public interface JoininMapper extends BaseMapper<Joinin> {
    List<Joinin> search(Page<Joinin> page, JoininSearchParam param);
}