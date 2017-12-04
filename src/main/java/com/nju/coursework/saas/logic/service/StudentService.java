package com.nju.coursework.saas.logic.service;

import com.nju.coursework.saas.logic.vo.StudentVO;
import com.nju.coursework.saas.web.response.GeneralResponse;

/**
 * Created by zhouxiaofan on 2017/11/8.
 */
public interface StudentService {

    /**
     * 学生注册
     *
     * @param studentVO 学生信息
     * @return
     */
    GeneralResponse register(StudentVO studentVO);

    /**
     * 登陆
     *
     * @param studentNo 学号
     * @param password  密码
     * @return
     */
    GeneralResponse login(String studentNo, String password);

    /**
     * 获取验证码
     *
     * @param mail 邮箱地址
     * @return 验证码 6 位数字
     */
    String getVerifyCode(String mail);

}
