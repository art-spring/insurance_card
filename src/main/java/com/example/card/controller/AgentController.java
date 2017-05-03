package com.example.card.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.example.card.config.SmsConfig;
import com.example.card.entity.Agent;
import com.example.card.enums.BindState;
import com.example.card.enums.SmsCodeCheckResult;
import com.example.card.params.AgentSearchParam;
import com.example.card.result.JSONResult;
import com.example.card.result.ResultCode;
import com.example.card.service.AgentService;
import com.example.card.utils.SmsUtil;
import com.example.card.utils.StringUtil;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

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

    @Autowired
    private SmsConfig smsConfig;

    @PostMapping(value = "/insert")
    public JSONResult<Agent> insert(@NotNull @RequestBody Agent agent) {
        JSONResult<Agent> result = new JSONResult<>();

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
            result.setData(null);
        }
        result.setData(agent);
        return result;

    }


    @PostMapping("/select")
    public JSONResult<Page<Agent>> search(@NotNull @RequestBody AgentSearchParam param) {
        JSONResult<Page<Agent>> result = new JSONResult<>();
        result.setData(this.agentService.search(param));
        return result;
    }


    @PostMapping("/accept")
    public JSONResult<String> accept(@RequestBody int id) {
        JSONResult<String> result = new JSONResult<>();
        Agent agent = new Agent();
        agent.setId(id);
        agent.setStatus(BindState.BIND.getKey());
        agent.setBindTime(new Date());
        agent.updateById();
        return result;

    }

    @PostMapping("/remove")
    public JSONResult<String> remove(@RequestBody int id) {

        JSONResult<String> result = new JSONResult<>();
        Agent agent = new Agent();
        agent.setId(id);
        agent.setStatus(BindState.UN_BIND.getKey());
        agent.setUnbindTime(new Date());
        agent.updateById();
        return result;
    }

    @PostMapping(value = "/update")
    public JSONResult<Agent> update(@RequestParam("keys") String keys, @RequestBody Agent updateInfo) {
        JSONResult<Agent> result = new JSONResult<>();
        String[] ids = keys.split(",");
        List<Agent> oldAgents = this.agentService.selectBatchIds(Arrays.asList(ids));

        boolean updateAgentName = !StringUtil.isEmpty(updateInfo.getName());
        boolean updateAgentPhone = !StringUtil.isEmpty(updateInfo.getPhoneNumber());
        boolean updateStatus = updateInfo.getStatus() != null;

        if (oldAgents != null && oldAgents.size() > 0) {
            for (Agent agent : oldAgents) {
                if (updateAgentName)
                    agent.setName(updateInfo.getName());
                if (updateAgentPhone)
                    agent.setPhoneNumber(updateInfo.getPhoneNumber());
                if (updateStatus)
                    agent.setStatus(updateInfo.getStatus().intValue());
            }
            this.agentService.insertOrUpdateBatch(oldAgents);
        } else {
            result.setResultCode(ResultCode.FAILD);
        }
        return result;
    }

    @PostMapping(value = "wechat/apply")
    public JSONResult<Agent> wechatApply(@RequestBody Map<String, String> params) {

//        openId: openId,
//        smsCode: this.state.smsCode,
//        applicant: this.state.applicant,
//        phoneNumber: this.state.phoneNumber


        JSONResult<Agent> result = new JSONResult<>();
        String openId = params.get("openId");
        String smsCode = params.get("smsCode");
        String applicant = params.get("applicant");
        String phoneNumber = params.get("phoneNumber");
        if (StringUtils.isEmpty(openId) || StringUtils.isEmpty(smsCode)
                || StringUtils.isEmpty(applicant) || StringUtils.isEmpty(phoneNumber)) {
            result.setResultCode(ResultCode.PARAMS_IS_NULL);
            return result;
        }

        if (SmsUtil.checkCode(phoneNumber, smsCode, Long.parseLong(smsConfig.getOverTime())) == SmsCodeCheckResult.MATCH) {
            Agent agent = new Agent();
            agent.setName(applicant);
            agent.setPhoneNumber(applicant);
            agent.setWxId(openId);
            boolean addResult = this.agentService.createByManager(agent);
            if (!addResult) {
                result.setResultCode(ResultCode.FAILD);
                result.setData(null);
            }
            result.setData(agent);
        } else {
            result.setResultCode(ResultCode.FAILD);
            result.setMessage("短信验证码验证失败");
        }

        return result;
    }

    @PostMapping(value = "wechat/unbind")
    public JSONResult<Boolean> wechatUnbind(@RequestBody Map<String, String> params) {

//        openId: openId,
//        smsCode: this.state.smsCode,
//        phoneNumber: this.state.phoneNumber

        JSONResult<Boolean> result = new JSONResult<>();
        String openId = params.get("openId");
        String smsCode = params.get("smsCode");
        String phoneNumber = params.get("phoneNumber");

        if (StringUtils.isEmpty(openId) || StringUtils.isEmpty(smsCode)
                || StringUtils.isEmpty(phoneNumber)) {
            result.setResultCode(ResultCode.PARAMS_IS_NULL);
            return result;
        }

        if (SmsUtil.checkCode(phoneNumber, smsCode, Long.parseLong(smsConfig.getOverTime())) == SmsCodeCheckResult.MATCH) {
            Map<String, Object> map = new HashedMap();
            map.put("wx_id", openId);
            List<Agent> tmp = agentService.selectByMap(map);
            if (tmp!=null&&tmp.size()==1){
                if (!tmp.get(0).setWxId(null).updateById()){
                    result.setResultCode(ResultCode.FAILD);
                }
            }else{
                result.setResultCode(ResultCode.FAILD);
                result.setMessage("该微信尚未绑定");
            }
        } else {
            result.setResultCode(ResultCode.FAILD);
            result.setMessage("短信验证码验证失败");
        }
        return result;
    }

    @PostMapping(value = "wechat/checkOpenId/{openId}")
    public JSONResult<Boolean> wechatCheckApply(@PathVariable("openId") String openId) {
        JSONResult<Boolean> result = new JSONResult<>();
        if (StringUtils.isEmpty(openId)) {
            result.setResultCode(ResultCode.PARAMS_IS_NULL);
            return result;
        }
        Map<String, Object> map = new HashedMap();
        map.put("wx_id", openId);
        List<Agent> tmp = agentService.selectByMap(map);
        if (tmp != null && tmp.size() > 0) {
            result.setData(true);
        } else {
            result.setData(false);
        }
        return result;
    }

}
