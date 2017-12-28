package com.jcwenhua.card.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.jcwenhua.card.entity.Joinin;
import com.jcwenhua.card.params.JoininSearchParam;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author caichunyi
 * @since 2017-03-13
 */
public interface JoininService extends IService<Joinin> {
    /**
     * 添加代理商对象，如果phone存在则返回false
     * @param joinin
     * @return
     */
    boolean createByManager(Joinin joinin);

    Page<Joinin> search(JoininSearchParam param);

    boolean checkOpenId(String openId);
}
