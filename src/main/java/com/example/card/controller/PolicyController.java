package com.example.card.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.example.card.entity.Policy;
import com.example.card.params.PolicySearchParam;
import com.example.card.result.JSONResult;
import com.example.card.result.ResultCode;
import com.example.card.service.PolicyService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author caichunyi
 * @since 2017-03-13
 */
@RestController
@RequestMapping("/policy")
public class PolicyController {

    @Autowired
    private PolicyService policyService;

    @PostMapping(value = "/update")
    public JSONResult<Policy> update(@RequestParam("keys") String keys, @RequestBody Policy updateInfo) {
        JSONResult<Policy> result = new JSONResult<>();
        String[] ids = keys.split(",");
        List<Policy> oldPolicies = this.policyService.selectBatchIds(Arrays.asList(ids));

        boolean updateStatus = updateInfo.getExportStatus() != null;

        if (oldPolicies != null && oldPolicies.size() > 0) {
            for (Policy policy :  oldPolicies){
                if (updateStatus)
                    policy.setExportStatus(updateInfo.getExportStatus().intValue());
            }
            this.policyService.insertOrUpdateBatch(oldPolicies);
        } else {
            result.setResultCode(ResultCode.FAILD);
        }
        return result;
    }

    @PostMapping("/select")
    public JSONResult<Page<Policy>> search(@NotNull @RequestBody PolicySearchParam param) {
        JSONResult<Page<Policy>> result = new JSONResult<>();
        result.setData(this.policyService.search(param));
        return result;
    }

    @PostMapping(value = "/insert")
    public JSONResult<Policy> insert(@NotNull @RequestBody Policy policy) {
        JSONResult<Policy> result = new JSONResult<>();

        if (policy.getCardId() == null) {
            result.setResultCode(ResultCode.PARAMS_IS_NULL);
            return result;
        }
        if (StringUtils.isEmpty(policy.getHolder())) {
            result.setResultCode(ResultCode.PARAMS_IS_NULL);
            return result;
        }
        if (StringUtils.isEmpty(policy.getRecognizee())) {
            result.setResultCode(ResultCode.PARAMS_IS_NULL);
            return result;
        }

        boolean addResult = this.policyService.createByManager(policy);
        if (!addResult) {
            result.setResultCode(ResultCode.FAILD);
            result.setData(null);
        }
        result.setData(policy);
        return result;

    }
}
