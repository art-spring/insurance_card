package com.example.card.mapper;

import com.baomidou.mybatisplus.plugins.Page;
import com.example.card.entity.Joinin;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.example.card.params.JoininSearchParam;

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