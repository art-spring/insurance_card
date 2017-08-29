package com.example.card.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.example.card.config.SmsConfig;
import com.example.card.entity.Joinin;
import com.example.card.enums.BindState;
import com.example.card.enums.SmsCodeCheckResult;
import com.example.card.params.JoininSearchParam;
import com.example.card.result.JSONResult;
import com.example.card.result.ResultCode;
import com.example.card.service.JoininService;
import com.example.card.utils.SmsUtil;
import com.example.card.utils.StringUtil;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import javax.websocket.server.PathParam;
import java.util.Arrays;
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
@RequestMapping("/joinin")
public class JoininController {

    @GetMapping("/options/{optionsColumnName}")
    public JSONResult<String> getStatusOptions(@PathParam("optionsColumnName") String optionsColumnName) {
        JSONResult<String> result = new JSONResult<>();
        String data = "[{'key':'1','value':'test1'},{'key':'2','value':'test2'}]";
        result.setData(data);
        return result;
    }


    @Autowired
    private JoininService joininService;

    @Autowired
    private SmsConfig smsConfig;

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
        joinin.setStatus(BindState.BIND.getKey());
        joinin.updateById();

        return result;
    }

    @PostMapping("/getDetail")
    public JSONResult<Joinin> get(int joinId) {
        JSONResult<Joinin> result = new JSONResult<>();
        result.setData(this.joininService.selectById(joinId));
        return result;
    }

    @PostMapping(value = "/update")
    public JSONResult<Joinin> update(@RequestParam("keys") String keys, @RequestBody Joinin updateInfo) {
        JSONResult<Joinin> result = new JSONResult<>();
        String[] ids = keys.split(",");
        List<Joinin> oldJoinins = this.joininService.selectBatchIds(Arrays.asList(ids));

        boolean updateName = !StringUtil.isEmpty(updateInfo.getName());
        boolean updatePhone = !StringUtil.isEmpty(updateInfo.getPhoneNumber());
        boolean updateStatus = updateInfo.getStatus() != null;

        if (oldJoinins != null && oldJoinins.size() > 0) {
            for (Joinin joinin : oldJoinins) {
                if (updateName)
                    joinin.setName(updateInfo.getName());
                if (updatePhone)
                    joinin.setPhoneNumber(updateInfo.getPhoneNumber());
                if (updateStatus)
                    joinin.setStatus(updateInfo.getStatus().intValue());
            }
            this.joininService.insertOrUpdateBatch(oldJoinins);
        } else {
            result.setResultCode(ResultCode.FAILD);
        }
        return result;
    }

    @PostMapping("/select")
    public JSONResult<Page<Joinin>> search(@NotNull @RequestBody JoininSearchParam param) {
        JSONResult<Page<Joinin>> result = new JSONResult<>();
        result.setData(this.joininService.search(param));
        return result;
    }

    @PostMapping(value = "/ ")
    public JSONResult<Joinin> insert(@NotNull @RequestBody Joinin joinin) {
        JSONResult<Joinin> result = new JSONResult<>();

        if (StringUtils.isEmpty(joinin.getName())) {
            result.setResultCode(ResultCode.PARAMS_IS_NULL);
            return result;
        }
        if (StringUtils.isEmpty(joinin.getPhoneNumber())) {
            result.setResultCode(ResultCode.PARAMS_IS_NULL);
            return result;
        }


        boolean addResult = this.joininService.createByManager(joinin);
        if (!addResult) {
            result.setResultCode(ResultCode.FAILD);
            result.setData(null);
        }
        result.setData(joinin);
        return result;

    }


    //微信端
    @PostMapping(value = "wechat/apply")
    public JSONResult<Joinin> wechatApply(@RequestBody Map<String, String> params) {

//        openId: openId,
//        smsCode: this.state.smsCode,
//        applicant: this.state.applicant,
//        phoneNumber: this.state.phoneNumber


        JSONResult<Joinin> result = new JSONResult<>();
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
            Joinin joinin = new Joinin();
            joinin.setName(applicant);
            joinin.setPhoneNumber(applicant);
            joinin.setWxId(openId);
            boolean addResult = this.joininService.createByManager(joinin);
            if (!addResult) {
                result.setResultCode(ResultCode.FAILD);
                result.setData(null);
            }
            result.setData(joinin);
        } else {
            result.setResultCode(ResultCode.FAILD);
            result.setMessage("短信验证码验证失败");
        }

        return result;
    }

    @PostMapping(value = "wechat/checkApply/{openId}")
    public JSONResult<Boolean> wechatCheckApply(@PathVariable("openId") String openId) {
        JSONResult<Boolean> result = new JSONResult<>();
        if (StringUtils.isEmpty(openId)) {
            result.setResultCode(ResultCode.PARAMS_IS_NULL);
            return result;
        }
        Map<String, Object> map = new HashedMap();
        map.put("wx_id", openId);
        List<Joinin> tmp = joininService.selectByMap(map);
        if (tmp != null && tmp.size() > 0) {
            result.setData(true);
        }else{
            result.setData(false);
        }
        return result;
    }

}
