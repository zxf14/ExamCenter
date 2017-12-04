package com.nju.coursework.saas.logic.impl;

import com.nju.coursework.saas.data.db.CourseRepository;
import com.nju.coursework.saas.data.db.OptionRepository;
import com.nju.coursework.saas.data.db.QuestionRepository;
import com.nju.coursework.saas.data.entity.Course;
import com.nju.coursework.saas.data.entity.Aoption;
import com.nju.coursework.saas.data.entity.Question;
import com.nju.coursework.saas.logic.service.QuestionService;
import com.nju.coursework.saas.util.ExcelConverter;
import com.nju.coursework.saas.web.response.GeneralResponse;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;

import static java.lang.Integer.parseInt;

/**
 * Created by zhouxiaofan on 2017/12/3.
 */
@Service
public class QuestionServiceImpl implements QuestionService {
    @Autowired
    QuestionRepository questionRepository;

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    OptionRepository optionRepository;

    @Override
    public GeneralResponse importQuestion(InputStream excel, int courseId) {
        GeneralResponse generalResponse = null;
        Course course = courseRepository.findOne(courseId);
        try {
            XSSFSheet xssfSheet = new XSSFWorkbook(excel).getSheetAt(0);
            //第一行为标题
            for (int rowNum = 1; rowNum <= xssfSheet.getLastRowNum(); rowNum++) {
                XSSFRow xssfRow = xssfSheet.getRow(rowNum);

                try {
                    saveQuestions(xssfRow, course);
                } catch (Exception e) {
                    e.printStackTrace();
                    generalResponse = new GeneralResponse(false, "excel格式错误");
                }
            }
            return generalResponse == null ? new GeneralResponse(true, "") : generalResponse;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private void saveQuestions(XSSFRow xssfRow, Course course) {
        Question question = new Question();
        question.setCourseByCourseId(course);
        question.setContent(ExcelConverter.getCellValue(xssfRow.getCell(0)));
        question.setType((int) xssfRow.getCell(5).getNumericCellValue());
        questionRepository.saveAndFlush(question);

        String[] answers = ExcelConverter.getCellValue(xssfRow.getCell(5)).split(" ");
        for (int i = 0; i < 4; i++) {
            Aoption option = new Aoption();
            option.setQuestionByQuestionId(question);
            option.setContent(ExcelConverter.getCellValue(xssfRow.getCell(i + 1)));
            option.setIsRight(false);
            for (String answer : answers) {
                if (parseInt(answer) == i + 1) {
                    option.setIsRight(true);
                    break;
                }
            }
            optionRepository.saveAndFlush(option);
        }

    }


}
