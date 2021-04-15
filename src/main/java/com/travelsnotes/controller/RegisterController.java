package com.travelsnotes.controller;


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
    public Map<String, Integer> isHas(@RequestParam(value = "userName") String username){
        Map<String,Integer> map=new HashMap<>();
        try {
            UserInfo user = userService.queryByName(username);
            if (user != null) {
                map.put("status",404);
                return map;
            } else {
                map.put("status",200);
                return map;
            }
        }catch (Exception e){
            map.put("status",404);
            return map;
        }
    }
    @RequestMapping(value = "/account",method = RequestMethod.POST)
    public Map<String, Integer> register(String userName,String password,String phoneNumber){
        Map<String, Integer> map=new HashMap<>();
        int result=0;
        try{
            UserInfo userInfo=new UserInfo(userName,password,phoneNumber);
            result=userService.registerUser(userInfo);
        }catch (Exception e){
            result=-1;
        }
        if (result==1)
        map.put("status",200);
        if (result==-1)
            map.put("status",400);
        return map;
    }
}
