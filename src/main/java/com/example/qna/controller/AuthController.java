package com.example.qna.controller;

import com.example.qna.entity.User;
import com.example.qna.service.UserService;
import com.example.qna.util.JwtUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping({"/", "/index"})
    public String index() {
        return "index"; // 会重定向到 /login
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String doLogin(HttpServletRequest request,
                          HttpServletResponse response,
                          @RequestParam String username,
                          @RequestParam String password,
                          @RequestParam String captcha,
                          Model model) {

        // 验证验证码（保存在 session）
        String sessionCaptcha = (String) request.getSession().getAttribute("CAPTCHA_CODE");
        request.getSession().removeAttribute("CAPTCHA_CODE"); // 一次性验证码
        if (sessionCaptcha == null || captcha == null || !sessionCaptcha.equalsIgnoreCase(captcha.trim())) {
            model.addAttribute("error", "验证码错误");
            return "login";
        }

        User user = userService.findByUsername(username);
        if (user == null || !user.getPassword().equals(password)) {
            model.addAttribute("error", "用户名或密码错误");
            return "login";
        }

        String token = jwtUtil.generateToken(user.getUsername());
        System.out.println(">>>> GENERATED TOKEN: " + token);
        Cookie cookie = new Cookie("token", token);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
        System.out.println(">>>> ADDED COOKIE with Path=" + cookie.getPath());

        return "redirect:/discussions";
    }

    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }

    @PostMapping("/register")
    public String doRegister(@RequestParam String username,
                             @RequestParam String password,
                             Model model) {
        if (username == null || username.trim().isEmpty() || password == null || password.trim().isEmpty()) {
            model.addAttribute("error", "用户名或密码不能为空");
            return "register";
        }
        if (userService.findByUsername(username) != null) {
            model.addAttribute("error", "用户名已存在");
            return "register";
        }
        User u = new User();
        u.setUsername(username);
        u.setPassword(password);
        userService.register(u);
        return "redirect:/login";
    }

    @GetMapping("/logout")
    public String logout(HttpServletResponse response) {
        Cookie cookie = new Cookie("token", null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);
        return "redirect:/login";
    }
}
