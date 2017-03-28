package com.example.card.utils;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

/**
 * Created by caichunyi on 2017/3/16.
 */
public class ExcelUtil {


    /**
     * 没有数据返回null
     *
     * @param filePath
     * @return
     * @throws IOException
     */
    public static Object[][] read(String filePath) throws IOException {
        if (filePath == null) {
            return null;
        }

        InputStream stream = new FileInputStream(filePath);
        Workbook workbook = null;

        if (filePath.endsWith(".xls")) {

            workbook = new HSSFWorkbook(stream);
        } else if (filePath.endsWith(".xlsx")) {
            workbook = new XSSFWorkbook(stream);
        } else {
            return null;
        }

        Sheet sheet = workbook.getSheetAt(0);

        int rowNum = sheet.getLastRowNum();
        Row row = sheet.getRow(0);
        int colNum = row.getPhysicalNumberOfCells();
        Object[][] result = new Object[rowNum][colNum];
        for (int i = 1; i <= rowNum; i++) {
            row = sheet.getRow(i);
            for (int j = 0; j < colNum && j < row.getLastCellNum(); j++) {

                result[i - 1][j] = getCellFormatValue(row.getCell(j));
                System.out.println("i:" + i + " j:" + j + "value : " + result[i - 1][j]);
            }


        }
        return result;
    }


    private static Object getCellFormatValue(Cell cell) {
        Object cellvalue = "";


        if (cell != null) {
            // 判断当前Cell的Type
            switch (cell.getCellType()) {
                case Cell.CELL_TYPE_NUMERIC:// 如果当前Cell的Type为NUMERIC
                case Cell.CELL_TYPE_FORMULA: {
                    // 判断当前的cell是否为Date
                    if (DateUtil.isCellDateFormatted(cell)) {
                        // 如果是Date类型则，转化为Data格式
                        // data格式是带时分秒的：2013-7-10 0:00:00
                        // cellvalue = cell.getDateCellValue().toLocaleString();
                        // data格式是不带带时分秒的：2013-7-10
                        Date date = cell.getDateCellValue();
                        cellvalue = date;
                    } else {// 如果是纯数字

                        // 取得当前Cell的数值
                        Integer intValue = Math.toIntExact(Math.round(cell.getNumericCellValue()));
                        if (cell.getNumericCellValue() == intValue) {
                            cellvalue = intValue;
                        } else {
                            cellvalue = cell.getNumericCellValue();
                        }
                    }
                    break;
                }
                case Cell.CELL_TYPE_STRING:// 如果当前Cell的Type为STRING
                    // 取得当前的Cell字符串
                    cellvalue = cell.getRichStringCellValue().getString();
                    break;
                default:// 默认的Cell值
                    cellvalue = "";
            }
        } else {
            cellvalue = "";
        }
        return cellvalue;
    }

    public static void main(String[] args) throws IOException {
        Object[][] strings = read("d:/template.xlsx");
        System.out.println(strings);
    }

}
