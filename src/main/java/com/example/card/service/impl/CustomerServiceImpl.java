package com.example.card.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.example.card.entity.Customer;
import com.example.card.mapper.CustomerMapper;
import com.example.card.service.CustomerService;
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
public class CustomerServiceImpl extends ServiceImpl<CustomerMapper, Customer> implements CustomerService {
    @Autowired
    private CustomerMapper customerMapper;

    @Override
    public Map<String, Integer> getNameIdMap() {
        List<Customer> customers = this.selectList(new EntityWrapper<>(null));
        Map<String, Integer> customerMap = new HashMap<>();
        for (Customer customer : customers) {
            customerMap.put(customer.getName(), customer.getId());
        }

        return customerMap;
    }

    @Override
    public boolean checkOpenId(String openId) {
        Map<String, Object> map = new HashedMap();
        map.put("wx_id", openId);
        List<Customer> tmp = customerMapper.selectByMap(map);
        if (tmp.size() == 1) {
            return true;
        }
        return false;
    }

    @Override
    public boolean bindWechat(Customer customer) {
        Map<String, Object> map = new HashedMap();
        map.put("phone_number", customer.getPhoneNumber());
        List<Customer> oldCustomers = customerMapper.selectByMap(map);
        if (oldCustomers == null || oldCustomers.size() == 0) {
            customer.setBindTime(new Date());
            customer.setBindState(true);
            return customer.insert();
        } else {
            Customer oldCustomer = oldCustomers.get(0);
            oldCustomer.setWxId(customer.getWxId());
            oldCustomer.setBindTime(new Date());
            oldCustomer.setBindState(true);
            if (!oldCustomer.updateById()) {
                return false;
            } else {
                return true;
            }
        }
    }

    @Override
    public boolean unbindWechat(Customer customer) {
        return false;
    }

    @Override
    public Customer findCustomerByOpenId(String openId) {
        Map<String, Object> map = new HashedMap();
        map.put("wx_id", openId);
        List<Customer> tmp = customerMapper.selectByMap(map);
        if (tmp != null && tmp.size() == 1) {
            return tmp.get(0);
        }
        return null;
    }

}
