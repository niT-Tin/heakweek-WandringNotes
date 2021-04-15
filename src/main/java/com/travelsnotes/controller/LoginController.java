package com.travelsnotes.controller;


import com.travelsnotes.pojo.Result;
import com.travelsnotes.pojo.ResultCodeEnum;
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
    public Result Login(@RequestParam(value = "userName") String userName,
                                     @RequestParam(value = "password")String password, HttpServletRequest request){
        try {
            UserInfo user = userService.queryByName(userName);
            Map<String, Object> map = new HashMap<>();
            if (user != null ){
                if (user.getPassword().equals(password)){
                String token = UUID.randomUUID().toString().replaceAll("-","").toUpperCase();
                request.getSession().setAttribute(token, user.getUserId());
                map.put("token", token);
                return Result.ok(ResultCodeEnum.SUCCESS_LOGIN).data(map);   //返回登陆成功 token
                } else {
                return  Result.error(ResultCodeEnum.ERROR_PASSWORD);    //密码错误
                }
            }else {
               return Result.error(ResultCodeEnum.ERROR_NOT_EXISTS_USER);   //用户名不存在
            }
        }catch (Exception e){
            return Result.error(ResultCodeEnum.PARAM_ERROR);    //参数不正确
        }
    }
}
