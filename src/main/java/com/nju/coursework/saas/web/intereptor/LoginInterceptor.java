package com.nju.coursework.saas.web.intereptor;

import com.nju.coursework.saas.data.db.UserRepository;
import com.nju.coursework.saas.data.entity.User;
import com.nju.coursework.saas.logic.service.StudentService;
import com.nju.coursework.saas.web.annotation.LoginRequired;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;

public class LoginInterceptor extends HandlerInterceptorAdapter {
    @Autowired
    private UserRepository userRepository;

    /**
     * 预处理回调方法，实现处理器的预处理（如登录检查）。
     * 第三个参数为响应的处理器，即controller。
     * 返回true，表示继续流程，调用下一个拦截器或者处理器。
     * 返回false，表示流程中断，通过response产生响应。
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception {

        System.out.println("-------" + request.getRequestURI());

        // 如果不是映射到方法直接通过
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();

        // 判断接口是否需要登录
        LoginRequired methodAnnotation = method.getAnnotation(LoginRequired.class);
        if (methodAnnotation != null) {
            // 验证用户是否登陆
            Object obj = request.getSession().getAttribute("id");
            if (obj == null) {
                response.setStatus(401);
                return false;
            }
        }
        return true;
    }
}
