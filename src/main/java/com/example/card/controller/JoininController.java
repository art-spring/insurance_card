package com.example.card.controller;

import com.example.card.entity.Joinin;
import com.example.card.enums.BindState;
import com.example.card.result.JSONResult;
import com.example.card.result.ResultCode;
import com.example.card.service.JoininService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author caichunyi
 * @since 2017-03-13
 */
@RestController
@RequestMapping("/joinin")
public class JoininController {


    @Autowired
    private JoininService joininService;

    @PostMapping("/remove")
    public JSONResult<Boolean> remove(int joinId) {
        JSONResult<Boolean> result = new JSONResult<>();

        this.joininService.deleteById(joinId);


        return result;
    }

    @PostMapping("/accept")
    public JSONResult<Boolean> accept(int joinId) {
        JSONResult<Boolean> result = new JSONResult<>();

        Joinin joinin = new Joinin();
        joinin.setId(joinId);
        joinin.setState(BindState.BIND.getKey());
        joinin.updateById();

        return result;
    }

    @PostMapping("/getDetail")
    public JSONResult<Joinin> get(int joinId) {
        JSONResult<Joinin> result = new JSONResult<>();
        result.setData(this.joininService.selectById(joinId));
        return result;
    }

    @PostMapping("/add")
    public JSONResult<String> add(Joinin join) {
        JSONResult<String> result = new JSONResult<>();
        try {

            join.insert();
        } catch (Exception ex) {
            ex.printStackTrace();
            result.setResultCode(ResultCode.EXCEPTION);
            result.setData(ex.getMessage());
        }

        return result;
    }

}
