package com.nju.coursework.saas.logic.impl;

import com.nju.coursework.saas.data.db.AnswerRepository;
import com.nju.coursework.saas.data.db.OptionRepository;
import com.nju.coursework.saas.data.db.QuizRepository;
import com.nju.coursework.saas.data.db.TesteeRepository;
import com.nju.coursework.saas.data.entity.*;
import com.nju.coursework.saas.logic.service.SubmitExamService;
import com.nju.coursework.saas.logic.vo.QuizVO;
import com.nju.coursework.saas.web.response.GeneralResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SubmitExamServiceImpl implements SubmitExamService {

    @Autowired
    QuizRepository quizRepository;
    @Autowired
    OptionRepository optionRepository;
    @Autowired
    AnswerRepository answerRepository;
    @Autowired
    TesteeRepository testeeRepository;

    @Override
    public GeneralResponse submitExam(int testeeId, List<QuizVO> quizVO) {

        List<Answer> answerList = new ArrayList<>();
        quizVO.stream().forEach(
                q -> {
                    Quiz quiz = quizRepository.findOne(q.getId());
                    Question question = quiz.getQuestionByQuestionId();
                    List<Aoption> options = q.getOptionId().stream().map(
                            i -> {
                                Aoption option = optionRepository.findOne(Integer.parseInt(i));
                                return option;
                            }
                    ).collect(Collectors.toList());
                    Answer answer = new Answer();
                    answer.setQuizByQuizId(quiz);
                    answer.setContent(String.join(";", q.getOptionId()));
                    if (question.getType() == 0) {
                        handleSingle(quiz, options, answer);
                    } else {
                        handleMulti(question, quiz, options, answer);
                    }
                    answerList.add(answer);

                }
        );
        try {
            Testee testee = testeeRepository.findOne(testeeId);
            testee.setScore(answerList.stream().mapToInt(Answer::getScore).sum());
            answerList.forEach(a -> {
                a.setStudentByStudentId(testee.getStudentByStudentId());
                answerRepository.saveAndFlush(a);
            });
            testeeRepository.saveAndFlush(testee);
        } catch (Exception e) {
            return new GeneralResponse(false, e.getMessage());
        }

        return new GeneralResponse(true, "试卷提交成功");
    }

    private void handleSingle(Quiz quiz, List<Aoption> aoption, Answer answer) {
        if (aoption.size() == 1 && aoption.get(0).getIsRight()) {
            answer.setScore(quiz.getValue());
        } else {
            answer.setScore(0);
        }
    }

    private void handleMulti(Question question, Quiz quiz, List<Aoption> aoption, Answer answer) {
        List<Aoption> rightAoption = question.getAoptionsById().stream().filter(Aoption::getIsRight)
                .collect(Collectors.toList());
        for (Aoption option : aoption) {
            if (!option.getIsRight()) {
                answer.setScore(0);
                return;
            }
        }
        List<Aoption> filterAoption = aoption.stream().filter(Aoption::getIsRight).collect(Collectors.toList());
        if (filterAoption.size() == rightAoption.size()) {
            answer.setScore(quiz.getValue());
        } else {
            answer.setScore(quiz.getValue() / 2);
        }
    }
}
