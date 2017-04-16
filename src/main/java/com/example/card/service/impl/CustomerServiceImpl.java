package com.example.card.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.example.card.entity.Customer;
import com.example.card.mapper.CustomerMapper;
import com.example.card.service.CustomerService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
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
    public boolean bindWechat(Customer customer) {
        Customer oldCustomer = customerMapper.selectById(customer.getId());
        if (oldCustomer == null) {
            return false;
        } else {
            oldCustomer.setWxId(customer.getWxId());
            oldCustomer.setBindTime(new Date());
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

}
