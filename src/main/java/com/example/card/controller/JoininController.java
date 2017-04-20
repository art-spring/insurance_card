package com.example.card.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.example.card.entity.Joinin;
import com.example.card.enums.BindState;
import com.example.card.params.JoininSearchParam;
import com.example.card.result.JSONResult;
import com.example.card.result.ResultCode;
import com.example.card.service.JoininService;
import com.example.card.utils.StringUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.Arrays;
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
            for (Joinin joinin :  oldJoinins){
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

    @PostMapping(value = "/insert")
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

}
