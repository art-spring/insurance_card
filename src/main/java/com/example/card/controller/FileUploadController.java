package com.example.card.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.example.card.entity.Card;
import com.example.card.entity.Policy;
import com.example.card.enums.CardState;
import com.example.card.result.JSONResult;
import com.example.card.result.ResultCode;
import com.example.card.service.*;
import com.example.card.utils.ExcelUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.jeecgframework.poi.excel.ExcelExportUtil;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by caichunyi on 2017/3/13.
 */
@RestController
@RequestMapping("/file")
public class FileUploadController {

    private static final Logger logger = LoggerFactory.getLogger(FileUploadController.class);
    @Autowired
    private AgentService agentService;

    @Autowired
    private CardService cardService;

    @Autowired
    private CardTypeService cardTypeService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private PolicyService policyService;

    @RequestMapping(value = "upload", method = RequestMethod.POST)
    public JSONResult<String> upload(MultipartFile file) {
        JSONResult<String> result = new JSONResult<>();

        if (file.isEmpty()) {
            result.setResultCode(ResultCode.EMPTY_FILE);
            return result;

        }

        //獲取文件名
        String fileName = file.getOriginalFilename();
        logger.info("upload file name is :   " + fileName);

        // 获取文件后缀名

        String stuffixName = fileName.substring(fileName.lastIndexOf("."));
        logger.info("upload stuffix name is :  " + stuffixName);

        try {
            Object[][] data = ExcelUtil.read(fileName);
            if (data != null) {

                Object[] row = null;


                StringBuilder totalBuilder = new StringBuilder();
                List<Card> cards = new ArrayList<>();
                for (int i = 0; i < data.length; i++) {
                    row = data[i];

                    Card card = new Card();
                    StringBuilder errorTextBuilder = new StringBuilder();
                    Map<String, Integer> agentMap = this.agentService.getNameIdMap();
                    Map<String, Integer> customerMap = this.customerService.getNameIdMap();
                    Map<String, Integer> cardTypeMap = this.cardTypeService.getNameIdMap();
                    int j = 0;
                    this.checkNumber(row[j], ++j, card, errorTextBuilder);
                    this.checkPassword(row[j], ++j, card, errorTextBuilder);
                    this.checkAgent(row[j], ++j, card, errorTextBuilder, agentMap);
                    this.checkCustomer(row[j], ++j, card, errorTextBuilder, customerMap);
                    this.checkState(row[j], ++j, card, errorTextBuilder);
                    this.checkCardType(row[j], ++j, card, errorTextBuilder, cardTypeMap);
                    this.checkActiveDate(row[j], ++j, card, errorTextBuilder);
                    this.checkUseDate(row[j], ++j, card, errorTextBuilder);

                    if (errorTextBuilder.length() > 0) {
                        totalBuilder.append(String.format("第%d行数据错误：%s\n", i + 1, errorTextBuilder.toString()));
                    } else {
                        cards.add(card);
                    }

                }
                if (totalBuilder.length() > 0) {
                    result.setResultCode(ResultCode.CARD_DATA_ERROR);
                    result.setData(totalBuilder.toString());
                } else {
                    this.cardService.insertBatch(cards);
                }


            }
        } catch (Exception e) {
            e.printStackTrace();
            result.setResultCode(ResultCode.EXCEPTION);
            result.setData(e.getMessage());
        }
        return result;
    }

    // 下载execl文档
    @RequestMapping(value = "policy/download")
    public void download(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 告诉浏览器用什么软件可以打开此文件
        response.setHeader("content-Type", "application/vnd.ms-excel");
        // 下载文件的默认名称
        // 初始化时设置 日期和时间模式
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String fileName = "%e6%8a%95%e4%bf%9d%e4%bf%a1%e6%81%af " + sdf.format(new Date()) + ".xls";
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
        List<Policy> list = new ArrayList<>();
        list.add(policyService.selectById(1));
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams(), Policy.class, list);
        workbook.write(response.getOutputStream());
    }

    private void checkState(Object stateName, int colIndex, Card card, StringBuilder errorTextBuilder) {
        CardState cardState = null;
        if (stateName != null && "" != stateName.toString()) {


            cardState = CardState.getByValue(stateName.toString());
            if (cardState != null) {
                card.setStatus(cardState.getKey());
            } else {
                errorTextBuilder.append(generateColErrorInfo(colIndex, "卡片状态错误！"));
            }
        } else {
            card.setStatus(CardState.UN_USE.getKey());
        }


    }

    private void checkAgent(Object agentName, int colIndex, Card card, StringBuilder errorTextBuilder, Map<String, Integer> agentMap) {
        if (agentName != null && "" != agentName.toString()) {
            if (agentMap.containsKey(agentName)) {
                card.setAgentId(agentMap.get(agentName));
            } else {
                errorTextBuilder.append(generateColErrorInfo(colIndex, "代理商名称不存在！"));
            }
        }
    }

    private void checkCustomer(Object customerName, int colIndex, Card card, StringBuilder errorTextBuilder, Map<String, Integer> customerMap) {
        if (customerName != null && "" != customerName.toString()) {
            if (customerMap.containsKey(customerName)) {
                card.setCustomerId(customerMap.get(customerName));
            } else {
                errorTextBuilder.append(generateColErrorInfo(colIndex, "客户名称不存在！"));
            }
        }
    }

    private void checkCardType(Object cardTypeName, int colIndex, Card card, StringBuilder errorTextBuilder, Map<String, Integer> cardTypeMap) {
        if (cardTypeName != null && cardTypeMap.containsKey(cardTypeName)) {
            card.setType(cardTypeMap.get(cardTypeName));
        } else {
            errorTextBuilder.append(generateColErrorInfo(colIndex, "卡片类型不存在！"));
        }
    }

    private String generateColErrorInfo(int colNum, String errorText) {

        return String.format("第%d列问题：%s;", colNum, errorText);
    }

    private void checkNumber(Object value, int colIndex, Card card, StringBuilder errorTextBuilder) {

        if (value instanceof String) {
            card.setCardNo(value.toString());
            int count = this.cardService.selectCount(new EntityWrapper<>(card));
            if (count > 0) {
                errorTextBuilder.append(generateColErrorInfo(colIndex, "该卡号已存在！"));
            }

        } else {
            errorTextBuilder.append(generateColErrorInfo(colIndex, "卡号数据为空或不是数字！"));
        }


    }

    private void checkPassword(Object value, int colIndex, Card card, StringBuilder errorTextBuilder) {


        if (value != null && StringUtils.isNotEmpty(value.toString())) {
            card.setPassword(value.toString());

        } else {
            errorTextBuilder.append(generateColErrorInfo(colIndex, "密码不能为空！"));
        }

    }

    private void checkActiveDate(Object value, int colIndex, Card card, StringBuilder errorTextBuilder) {
        if (value != null) {
            if (value instanceof Date) {
                card.setActiveTime((Date) value);
            } else {
                errorTextBuilder.append(generateColErrorInfo(colIndex, "激活时间类型错误！"));
            }
        }
    }

    private void checkUseDate(Object value, int colIndex, Card card, StringBuilder errorTextBuilder) {
        if (value != null) {
            if (value instanceof Date) {
                card.setUsedTime((Date) value);
            } else {
                errorTextBuilder.append(generateColErrorInfo(colIndex, "使用时间类型错误！"));
            }
        }
    }
}
