package com.travelsnotes.controller;


import com.travelsnotes.pojo.Result;
import com.travelsnotes.pojo.ResultCodeEnum;
import com.travelsnotes.pojo.UserInfo;
import com.travelsnotes.pojo.UserInfo;
import com.travelsnotes.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/register")
public class RegisterController {
    @Autowired
    UserService userService;
    @RequestMapping(value = "/isHas",method = RequestMethod.GET)
    public Result isHas(@RequestParam(value = "userName") String username){
        try {
            UserInfo user = userService.queryByName(username);
            if (user != null) {
               return Result.error(ResultCodeEnum.ERROR_NOT_EXISTS_USER);   //返回用户已存在
            } else {
                return Result.ok(ResultCodeEnum.SUCCESS_USABLE); //返回可以使用该用户名
            }
        }catch (Exception e){
            return Result.error(ResultCodeEnum.PARAM_ERROR);
        }
    }
    @RequestMapping(value = "/account",method = RequestMethod.POST)
    public Result register(String userName,String password,String phoneNumber){
        Map<String, Integer> map=new HashMap<>();
        int result=0;
        try{
            UserInfo userInfo=new UserInfo(userName,password,phoneNumber);
            result=userService.registerUser(userInfo);
            return Result.ok(ResultCodeEnum.SUCCESS_REGISTER); //注册成功
        }catch (Exception e){
            return    Result.error(ResultCodeEnum.FAIL_REGISTER);  //注册失败
        }
    }
}
