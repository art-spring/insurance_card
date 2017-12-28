package com.jcwenhua.card.interceptor;

import com.jcwenhua.card.constants.HttpConstant;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.lang.reflect.Method;

/**
 * Created by caichunyi on 2017/3/31.
 */
@Component
public class LoginInterceptor extends HandlerInterceptorAdapter {

    /**
     * 登录验证拦截，对加了auth 注解的类或方法进行验证，session 中有sessionkey 继续进入controller，否则返回未登录
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {



        if (!handler.getClass().isAssignableFrom(HandlerMethod.class)) {
            return true;
        }
        final HandlerMethod handlerMethod = (HandlerMethod) handler;
        final Method method = handlerMethod.getMethod();
        final Class<?> clazz = method.getDeclaringClass();
        if (clazz.isAnnotationPresent(Auth.class) || method.isAnnotationPresent(Auth.class)) {

            HttpSession session = request.getSession();
            Object obj = session.getAttribute(HttpConstant.SESSION_DATA_KEY);
            if(obj == null){
                response.addHeader("Access-Control-Allow-Credentials", "true");
                response.addHeader("Access-Control-Allow-Origin", "http://localhost:8080");
                response.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE");
                response.addHeader("Access-Control-Allow-Headers", "Content-Type");
                response.addHeader("Access-Control-Max-Age", "1800");//30 min
                response.setCharacterEncoding("UTF-8");
                response.setContentType("application/json; charset=utf8");
                PrintWriter writer = response.getWriter();
                writer.append("{\"code\":1000,\"message\":\"未登录\"}");
                writer.close();
                return false;
            }


            return true;
        }

        return true;


    }

}
