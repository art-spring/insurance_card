package com.example.card.service;

import com.example.card.entity.Customer;
import com.baomidou.mybatisplus.service.IService;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author caichunyi
 * @since 2017-03-13
 */
public interface CustomerService extends IService<Customer> {
    Map<String, Integer> getNameIdMap();

    boolean checkOpenId(String openId);

    boolean bindWechat(Customer customer);

    boolean unbindWechat(Customer customer);

    Customer findCustomerByOpenId(String openId);
}
