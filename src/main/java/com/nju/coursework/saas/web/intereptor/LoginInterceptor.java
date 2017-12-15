package com.nju.coursework.saas.web.intereptor;

import com.nju.coursework.saas.data.db.UserRepository;
import com.nju.coursework.saas.data.entity.User;
import com.nju.coursework.saas.logic.service.StudentService;
import com.nju.coursework.saas.logic.service.UserService;
import com.nju.coursework.saas.web.annotation.LoginRequired;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

//todo
public class LoginInterceptor extends HandlerInterceptorAdapter {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StudentService studentService;

    /**
     *预处理回调方法，实现处理器的预处理（如登录检查）。
     *第三个参数为响应的处理器，即controller。
     *返回true，表示继续流程，调用下一个拦截器或者处理器。
     *返回false，表示流程中断，通过response产生响应。
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception {

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
            if (obj == null || !(obj instanceof String)) {
                response.sendRedirect(request.getContextPath() + "/index.html");
                return false;
            }
            User use = userRepository.findOne((int) obj);
            if (use == null) {
                return false;
            }
        }
        return true;
        // 有 @LoginRequired 注解，需要认证
//        if (methodAnnotation != null) {
//            // 执行认证
//            String token = request.getHeader("token");  // 从 http 请求头中取出 token
//            if (token == null) {
//                throw new RuntimeException("无token，请重新登录");
//            }
//            int userId;
//            try {
//                userId = Integer.parseInt(JWT.decode(token).getAudience().get(0));  // 获取 token 中的 user id
//            } catch (JWTDecodeException e) {
//                throw new RuntimeException("token无效，请重新登录");
//            }
//            User user = userService.findById(userId);
//            if (user == null) {
//                throw new RuntimeException("用户不存在，请重新登录");
//            }
//            // 验证 token
//            try {
//                JWTVerifier verifier =  JWT.require(Algorithm.HMAC256(user.getPassword())).build();
//                try {
//                    verifier.verify(token);
//                } catch (JWTVerificationException e) {
//                    throw new RuntimeException("token无效，请重新登录");
//                }
//            } catch (UnsupportedEncodingException ignore) {}
//            request.setAttribute("currentUser", user);
//            return true;
//        }
//        return true;
    }

    /**
     *当前请求进行处理之后，也就是Controller 方法调用之后执行，
     *但是它会在DispatcherServlet 进行视图返回渲染之前被调用。
     *此时我们可以通过modelAndView对模型数据进行处理或对视图进行处理。
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response,
                           Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("-------------------postHandle");
    }

    /**
     *方法将在整个请求结束之后，也就是在DispatcherServlet 渲染了对应的视图之后执行。
     *这个方法的主要作用是用于进行资源清理工作的。
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
                                Object handler, Exception ex) throws Exception {
        System.out.println("-------------------afterCompletion");
    }
}
