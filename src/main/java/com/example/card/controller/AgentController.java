package com.example.card.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.example.card.entity.Agent;
import com.example.card.enums.BindState;
import com.example.card.interceptor.Auth;
import com.example.card.params.AgentSearchParam;
import com.example.card.result.JSONResult;
import com.example.card.result.ResultCode;
import com.example.card.service.AgentService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author caichunyi
 * @since 2017-03-13
 */
@RestController
@RequestMapping("/agent")
public class AgentController {

    @Autowired
    private AgentService agentService;

    @PostMapping(value = "/add")
    @Auth
    public JSONResult<String> add(@NotNull @RequestBody Agent agent) {
        JSONResult<String> result = new JSONResult<>();

        if (StringUtils.isEmpty(agent.getName())) {
            result.setResultCode(ResultCode.PARAMS_IS_NULL);
            return result;
        }
        if (StringUtils.isEmpty(agent.getPhoneNumber())) {
            result.setResultCode(ResultCode.PARAMS_IS_NULL);
            return result;
        }


        boolean addResult = this.agentService.createByManager(agent);
        if (!addResult) {
            result.setResultCode(ResultCode.FAILD);
            result.setData("该用户已存在无需再创建");
        }

        return result;

    }


    @PostMapping("/search")
    @Auth
    public JSONResult<List<Agent>> search(@NotNull @RequestBody AgentSearchParam param) {
        JSONResult<List<Agent>> result = new JSONResult<>();


        return result;
    }

    @PostMapping("/accept")
    @Auth
    public JSONResult<String> accept(@RequestBody int id) {
        JSONResult<String> result = new JSONResult<>();
        Agent agent = new Agent();
        agent.setId(id);
        agent.setState(BindState.BIND.getKey());
        agent.setBindTime(new Date());
        agent.updateById();
        return result;

    }

    @PostMapping("/remove")
    @Auth
    public JSONResult<String> remove(@RequestBody int id) {

        JSONResult<String> result = new JSONResult<>();
        Agent agent = new Agent();
        agent.setId(id);
        agent.setState(BindState.UN_BIND.getKey());
        agent.setUnbindTime(new Date());
        agent.updateById();
        return result;


    }


}
