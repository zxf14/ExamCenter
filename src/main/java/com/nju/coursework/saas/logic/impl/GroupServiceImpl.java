package com.nju.coursework.saas.logic.impl;

import com.nju.coursework.saas.data.db.GroupRepository;
import com.nju.coursework.saas.data.db.UserRepository;
import com.nju.coursework.saas.data.entity.Groups;
import com.nju.coursework.saas.data.entity.User;
import com.nju.coursework.saas.logic.service.GroupService;
import com.nju.coursework.saas.logic.vo.GroupsVO;
import com.nju.coursework.saas.util.ExcelConverter;
import com.nju.coursework.saas.web.response.GeneralResponse;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GroupServiceImpl implements GroupService {
    @Autowired
    GroupRepository groupRepository;
    @Autowired
    UserRepository userRepository;

    @Override
    public GeneralResponse createGroup(int userId, InputStream excel, String groupName) {
        try {
            XSSFSheet xssfSheet = new XSSFWorkbook(excel).getSheetAt(0);

            List<String> list = new ArrayList<>();
            for (int rowNum = 1; rowNum <= xssfSheet.getLastRowNum(); rowNum++) {
                XSSFRow xssfRow = xssfSheet.getRow(rowNum);
                String info = ExcelConverter.getCellValue(xssfRow.getCell(0)) + " " + ExcelConverter.getCellValue(xssfRow.getCell(1));
                list.add(info);
            }
            try {
                saveGroup(list, groupName, userId);
            } catch (Exception error) {
                return new GeneralResponse(false, "excel格式错误");
            }

            return new GeneralResponse(true, "");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new GeneralResponse(false, "");
    }

    private void saveGroup(List<String> list, String groupName, int userId) {
        Groups group = new Groups();
        group.setName(groupName);
        group.setStudents(String.join(";", list));
        group.setUserByUserId(userRepository.findOne(userId));
        groupRepository.saveAndFlush(group);
    }

    @Override
    public List<GroupsVO> getGroups(int userId) {
        List<Groups> groups = groupRepository.findByTeacher(userId);
        List<GroupsVO> result = groups.stream().map(item -> new GroupsVO(item)).collect(Collectors.toList());
        return result;
    }
}
