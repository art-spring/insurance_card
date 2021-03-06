package com.jcwenhua.card.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.jcwenhua.card.config.SmsConfig;
import com.jcwenhua.card.entity.Agent;
import com.jcwenhua.card.enums.BindState;
import com.jcwenhua.card.enums.SmsCodeCheckResult;
import com.jcwenhua.card.params.AgentSearchParam;
import com.jcwenhua.card.result.JSONResult;
import com.jcwenhua.card.result.ResultCode;
import com.jcwenhua.card.service.AgentService;
import com.jcwenhua.card.utils.SmsUtil;
import com.jcwenhua.card.utils.StringUtil;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.*;

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

    @PostMapping("options")
    public JSONResult<Map<String, List<Map<String, String>>>> getStatusOptions(@RequestBody String[] columnNames) {
        JSONResult<Map<String, List<Map<String, String>>>> result = new JSONResult<>();
        Map<String, List<Map<String, String>>> output = new HashMap<>();
        List<Map<String, String>> options = new ArrayList<>();
        List<Agent> allAgents = agentService.selectByMap(null);
        if (allAgents != null && allAgents.size() > 0) {
            for (Agent agent : allAgents) {
                Map<String, String> option = new HashMap<>();
                option.put("text", agent.getName());
                option.put("value", agent.getId().toString());
                options.add(option);
            }
        }
        output.put("test", options);
        result.setData(output);
        return result;
    }

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

    @GetMapping("/delete")
    public JSONResult<String> delete(@RequestParam("keys") String keys) {
        JSONResult<String> result = new JSONResult<>();
        String[] ids = keys.split(",");
        if (agentService.deleteBatchIds(Arrays.asList(ids))){
            result.setMessage("删除成功");
        }else{
            result.setResultCode(ResultCode.FAILD);
            result.setMessage("删除失败");
        }
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
        boolean bindSuccess = false;

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
            Map<String, Object> map = new HashedMap();
            map.put("phone_number", phoneNumber);
            List<Agent> oldAgents = agentService.selectByMap(map);
            if (oldAgents == null || oldAgents.size() == 0) {
                Agent agent = new Agent();
                agent.setName(applicant);
                agent.setPhoneNumber(phoneNumber);
                agent.setWxId(openId);
                agent.setStatus(BindState.APPLY.getKey());
                bindSuccess = agent.insert();
            } else {
                Agent oldAgent = oldAgents.get(0);
                if (StringUtil.isEmpty(oldAgent.getWxId())) {
                    oldAgent.setWxId(openId);
                    oldAgent.setStatus(BindState.APPLY.getKey());
                    bindSuccess = oldAgent.updateById();
                }else{
                    result.setResultCode(ResultCode.FAILD);
                    result.setMessage("状态出错");
                    return result;
                }
            }
            if (!bindSuccess) {
                result.setResultCode(ResultCode.FAILD);
                result.setMessage("申请失败");
            } else {
                result.setMessage("申请成功");
                result.setData(null);
            }
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
            if (tmp != null && tmp.size() == 1) {
                Agent agent = tmp.get(0);
                if (!agent.getPhoneNumber().equals(phoneNumber)) {
                    result.setResultCode(ResultCode.FAILD);
                    result.setMessage("手机号不匹配，请使用" + agent.getPhoneNumber().substring(0, 2) + "****" + agent.getPhoneNumber().substring(8, 10));
                    return result;
                }
                agent.setWxId(null);
                agent.setStatus(BindState.UN_BIND.getKey());
                agent.setUnbindTime(new Date());
                if (!agent.updateById()) {
                    result.setResultCode(ResultCode.FAILD);
                }
            } else {
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
    public JSONResult<Integer> wechatCheckOpenId(@PathVariable("openId") String openId) {
        JSONResult<Integer> result = new JSONResult<>();
        if (StringUtils.isEmpty(openId)) {
            result.setResultCode(ResultCode.PARAMS_IS_NULL);
            return result;
        }
        Map<String, Object> map = new HashedMap();
        map.put("wx_id", openId);
        List<Agent> tmp = agentService.selectByMap(map);
        if (tmp.size() == 1) {
            result.setData(tmp.get(0).getStatus().intValue());
        } else {
            result.setData(-1);
        }
        return result;
    }

}
