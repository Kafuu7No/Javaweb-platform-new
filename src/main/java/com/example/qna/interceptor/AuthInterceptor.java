package com.example.qna.interceptor;

import com.example.qna.entity.User;
import com.example.qna.service.UserService;
import com.example.qna.util.JwtUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class AuthInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String uri = request.getRequestURI();

        // 放行静态资源与登录、注册、验证码
        if (uri.contains("/login")
                || uri.contains("/register")
                || uri.contains("/captcha")) {
            return true;
        }


        String token = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie c : cookies) {
                if ("token".equals(c.getName())) {
                    token = c.getValue();
                    break;
                }
            }
        }

        if (token != null && jwtUtil.validateToken(token)) {
            String username = jwtUtil.getUsernameFromToken(token);
            User user = userService.findByUsername(username);
            if (user != null) {
                // 把当前用户存放在 request，用于 controller 和视图读取
                request.setAttribute("currentUser", user);
                return true;
            }
        }

        // 未登录，重定向到登录
        response.sendRedirect(request.getContextPath() + "/login");
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response,
                           Object handler, ModelAndView modelAndView) throws Exception {
        if (modelAndView != null) {
            Object cu = request.getAttribute("currentUser");
            modelAndView.addObject("currentUser", cu);
        }
    }
}
