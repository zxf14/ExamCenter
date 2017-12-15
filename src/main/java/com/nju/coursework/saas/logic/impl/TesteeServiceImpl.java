package com.nju.coursework.saas.logic.impl;

import com.nju.coursework.saas.data.db.TesteeRepository;
import com.nju.coursework.saas.data.entity.Testee;
import com.nju.coursework.saas.logic.service.TesteeService;
import com.nju.coursework.saas.logic.vo.TesteeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TesteeServiceImpl implements TesteeService {
    @Autowired
    TesteeRepository testeeRepository;

    @Override
    public List<TesteeVO> getTesteeReport(int examId) {
        List<Testee> testeeList = testeeRepository.findByExamId(examId);
        return testeeList.stream().map(i->new TesteeVO(i)).collect(Collectors.toList());
    }
}
