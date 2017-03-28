package com.example.card.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.example.card.entity.Admin;
import com.example.card.result.JSONResult;
import org.springframework.boot.autoconfigure.web.WebMvcProperties;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author caichunyi
 * @since 2017-03-13
 */
@Controller
@RequestMapping("/admin")
public class AdminController {


    @RequestMapping(value = "login.do", method = RequestMethod.POST)
    public ModelAndView login(@NotNull @Size(min = 6, max = 20, message = "6-20") @RequestParam String loginName, @NotNull @RequestParam String password, HttpSession httpSession) {
        JSONResult<Boolean> result = new JSONResult<>();

        Admin admin = new Admin();
        admin.setLoginName(loginName);
        admin.setPassword(password);
        admin = admin.selectOne(new EntityWrapper(admin));

        if (admin != null) {
            httpSession.setAttribute("user", admin);
            return new ModelAndView(new RedirectView("/card.html"));

        } else {
            return new ModelAndView(new RedirectView("/admin.html"));
        }
    }

    @RequestMapping("/")
    public WebMvcProperties.View index() {
        return new WebMvcProperties.View();
    }

    @RequestMapping(value = "/logout.do", method = RequestMethod.GET)
    public ModelAndView logout(HttpSession session) {

        session.removeAttribute("user");

        Admin admin = (Admin) session.getAttribute("user");
        if(admin!=null){
            System.out.println(admin);
        }else {
            System.out.println("session not exist");
        }

        return new ModelAndView(new RedirectView("/admin.html"));
    }


}
