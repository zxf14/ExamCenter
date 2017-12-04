package com.nju.coursework.saas.util;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;

import java.math.BigDecimal;

/**
 * Created by guhan on 17/11/8.
 */
public class ExcelConverter {
    public static String getCellValue(XSSFCell cell) {
        if(cell!= null){
            if (cell.getCellTypeEnum() == CellType.BOOLEAN) {
                return String.valueOf(cell.getBooleanCellValue());
            } else if (cell.getCellTypeEnum() == CellType.NUMERIC) {
                return new BigDecimal(cell.getNumericCellValue()).toPlainString();
            } else {
                return String.valueOf(cell.getStringCellValue());
            }
        }else{
            return "";
        }
    }
}
