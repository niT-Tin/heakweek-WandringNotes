package com.travelsnotes.controller;


import com.travelsnotes.pojo.UserInfo;
import com.travelsnotes.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/user")        //测试  用户登录
public class LoginController {
    @Autowired
    UserServiceImpl userService;
    @RequestMapping("/toLogin")
    public ModelAndView toLogin(){
        return new ModelAndView("login");
    }

    // 登录
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public Map<String, String> Login(@RequestParam(value = "userName") String userName,
                                     @RequestParam(value = "userPassword")String password, HttpServletRequest request){
        try {
            UserInfo user = userService.queryByName(userName);
            Map<String, String> map = new HashMap<>();
            if (user != null && user.getUserPassword().equals(password)) {
                String token = UUID.randomUUID().toString();
                request.getSession().setAttribute(token, user.getUserId());
                map.put("token", token);
                map.put("status", "200");
                return map;
            } else {
                map.put("status", "400");
                return map;
            }
        }catch (Exception e){
            Map<String, String> map = new HashMap<>();
            map.put("status", "400");
            return map;
        }
    }
}
