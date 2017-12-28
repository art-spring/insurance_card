package com.jcwenhua.card.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.jcwenhua.card.entity.Card;
import com.jcwenhua.card.entity.CardType;
import com.jcwenhua.card.enums.CardState;
import com.jcwenhua.card.model.PolicyInfo;
import com.jcwenhua.card.result.JSONResult;
import com.jcwenhua.card.result.ResultCode;
import com.jcwenhua.card.service.*;
import com.jcwenhua.card.utils.StringUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.jeecgframework.poi.excel.ExcelExportUtil;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.server.PathParam;
import java.text.SimpleDateFormat;
import java.util.*;

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
    public JSONResult<List<Card>> upload(MultipartFile file) {
        JSONResult<List<Card>> result = new JSONResult<>();

        if (file.isEmpty()) {
            result.setResultCode(ResultCode.EMPTY_FILE);
            return result;
        }
//
//        //獲取文件名
//        String fileName = file.getOriginalFilename();
//        logger.info("upload file name is :   " + fileName);
//
//        // 获取文件后缀名
//
//        String stuffixName = fileName.substring(fileName.lastIndexOf("."));
//        logger.info("upload stuffix name is :  " + stuffixName);

        List<Card> cards = new ArrayList<>();
        List<CardType> updateCardTypes = new ArrayList<>();
        try {
            ImportParams params = new ImportParams();
            params.setSheetNum(1);
            List<Map<String, Object>> list = ExcelImportUtil.importExcel(file.getInputStream(), Map.class, params);

            if (list != null) {
                for (int i = 0; i < list.size(); i++) {
                    Map<String, Object> row = list.get(i);
                    String typeName = row.get("卡片类型").toString().trim();
                    CardType cardType = cardTypeService.getCardTypeByTypeName(typeName);

                    if (cardType==null){
                        result.setResultCode(ResultCode.CARD_DATA_ERROR);
                        return result;
                    }

                    int cardCount = Integer.parseInt(row.get("生成数量").toString().trim());

                    for (int j = 0; j < cardCount; j++) {
                        Card card = new Card();
                        card.setCardNo(cardType.getPrefix() + String.format("%06d", cardType.getSeq().intValue() + j));
                        card.setPassword(String.valueOf((int)((Math.random() * 9 + 1) * 100000)));
                        card.setType(cardType.getId().intValue());
                        card.setStatus(99);
                        card.setCreatedTime(new Date());
                        cards.add(card);
                    }

                    cardType.setSeq(cardType.getSeq() + cardCount);
                    updateCardTypes.add(cardType);

//                    StringBuilder errorTextBuilder = new StringBuilder();
//                    Map<String, Integer> agentMap = this.agentService.getNameIdMap();
//                    Map<String, Integer> customerMap = this.customerService.getNameIdMap();
//                    Map<String, Integer> cardTypeMap = this.cardTypeService.getNameIdMap();
//                    int j = 0;
//                    this.checkNumber(row[j], ++j, card, errorTextBuilder);
//                    this.checkPassword(row[j], ++j, card, errorTextBuilder);
//                    this.checkAgent(row[j], ++j, card, errorTextBuilder, agentMap);
//                    this.checkCustomer(row[j], ++j, card, errorTextBuilder, customerMap);
//                    this.checkState(row[j], ++j, card, errorTextBuilder);
//                    this.checkCardType(row[j], ++j, card, errorTextBuilder, cardTypeMap);
//                    this.checkActiveDate(row[j], ++j, card, errorTextBuilder);
//                    this.checkUseDate(row[j], ++j, card, errorTextBuilder);

//                    if (errorTextBuilder.length() > 0) {
//                        totalBuilder.append(String.format("第%d行数据错误：%s\n", i + 1, errorTextBuilder.toString()));
//                    } else {
//                        cards.add(card);
//                    }

                }
//                if (totalBuilder.length() > 0) {
//                    result.setResultCode(ResultCode.CARD_DATA_ERROR);
//                    result.setData(totalBuilder.toString());
//                } else {
//                    this.cardService.insertBatch(cards);
//                }
                cardService.insertBatch(cards);
                cardTypeService.updateBatchById(updateCardTypes);
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.setResultCode(ResultCode.EXCEPTION);
            result.setMessage(e.getMessage());
            result.setData(null);
        }
        result.setResultCode(ResultCode.SUCCESS);
        result.setData(cards);
        return result;
    }

    // 下载execl文档
    @RequestMapping(value = "policy/download")
    public void downloadPolicyData(HttpServletRequest request, HttpServletResponse response, @PathParam("keys") String keys) throws Exception {
        if (!StringUtil.isEmpty(keys)) {
            List<String> idList = Arrays.asList(keys.split(","));
            // 告诉浏览器用什么软件可以打开此文件
            response.setHeader("content-Type", "application/vnd.ms-excel");
            // 下载文件的默认名称
            // 初始化时设置 日期和时间模式
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String fileName = "%e6%8a%95%e4%bf%9d%e4%bf%a1%e6%81%af " + sdf.format(new Date()) + ".xls";
            response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
            List<PolicyInfo> list = policyService.searchByIds(idList);
            if (list != null) {
                policyService.setRecordExport(idList);
                Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams(), PolicyInfo.class, list);
                workbook.write(response.getOutputStream());
            }
        }
    }

    // 下载execl文档
    @RequestMapping(value = "card/download")
    public void downloadCardData(HttpServletRequest request, HttpServletResponse response, @PathParam("keys") String keys) throws Exception {
        if (!StringUtil.isEmpty(keys)) {
            List<String> idList = Arrays.asList(keys.split(","));
            // 告诉浏览器用什么软件可以打开此文件
            response.setHeader("content-Type", "application/vnd.ms-excel");
            // 下载文件的默认名称
            // 初始化时设置 日期和时间模式
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String fileName = "%e5%8d%a1%e7%89%87%e4%bf%a1%e6%81%af " + sdf.format(new Date()) + ".xls";
            response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
            List<Card> list = cardService.selectBatchIds(idList);
            if (list != null) {
                Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams(), Card.class, list);
                workbook.write(response.getOutputStream());
            }
        }
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
