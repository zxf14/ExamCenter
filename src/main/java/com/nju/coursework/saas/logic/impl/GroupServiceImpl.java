package com.nju.coursework.saas.logic.impl;

import com.nju.coursework.saas.data.db.GroupRepository;
import com.nju.coursework.saas.data.db.StudentRepository;
import com.nju.coursework.saas.data.db.UserRepository;
import com.nju.coursework.saas.data.entity.Group;
import com.nju.coursework.saas.logic.service.GroupService;
import com.nju.coursework.saas.web.response.GeneralResponse;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

@Service
public class GroupServiceImpl implements GroupService {
    @Autowired
    GroupRepository groupRepository;
    @Autowired
    UserRepository userRepository;

    @Override
    public GeneralResponse createGroup(int userId, File excel, String groupName) {

        try {
            InputStream is = new FileInputStream("C:\\Users\\I345754\\Downloads\\cloud\\TestCenter\\src\\main\\resources\\static\\studentList.xlsx");

            XSSFSheet xssfSheet = new XSSFWorkbook(is).getSheetAt(0);

            List<String> list = new ArrayList<>();
            // 循环行Row,第一行是列名，所以跳过
            for (int rowNum = 0; rowNum <= xssfSheet.getLastRowNum(); rowNum++) {
                XSSFRow xssfRow = xssfSheet.getRow(rowNum);
//                list.add(getCellValue(xssfRow.getCell(0)));
                list.add(getCellValue(xssfRow.getCell(0)));
            }

            saveGroup(list);

            GeneralResponse response = new GeneralResponse(true,"");

            System.out.println(list);
            return response;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private void saveGroup(List<String> list) {
        Group group = new Group();
        group.setStudents(String.join(" ", list));
        groupRepository.saveAndFlush(group);
    }

    private String getCellValue(XSSFCell cell) {
        if(cell!= null){
            if (cell.getCellTypeEnum() == CellType.BOOLEAN) {
                return String.valueOf(cell.getBooleanCellValue());
            } else if (cell.getCellTypeEnum() == CellType.NUMERIC) {
                return String.valueOf(cell.getNumericCellValue());
            } else {
                return String.valueOf(cell.getStringCellValue());
            }
        }else{
            return "";
        }
    }

    @Override
    public List<String> getGroups(int userId) {
        return null;
    }
}
