package com.travelsnotes.controller;


import com.travelsnotes.dao.HKMapper;
import com.travelsnotes.pojo.OSS;
import com.travelsnotes.pojo.UserInfo;
import com.travelsnotes.service.OSSutil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
public class UserInfoController {

    @Autowired
    private HKMapper userProperties;


    @Autowired
    private OSSutil ossProperties;


    //设置用户信息
    @PostMapping("/updateInfo")
    public Map<String, String> setUserInfo(@RequestParam(value = "token") String token,
                              @RequestParam(value = "userName")String userName,
                              @RequestParam(value = "phoneNumber",required = false) String phoneNumber,
                              @RequestParam(value = "avatar",required = false)String avatarUrl,
                                HttpServletRequest request){
        Object attribute = request.getSession().getAttribute(token);
        Map<String, String> map = new HashMap<>();
        if (attribute == null) {
            return null;
        }
        int tempId = (int) attribute;
        try{
            userProperties.setUsername(phoneNumber, tempId);
            userProperties.setPhoneNumber(userName, tempId);
//            ossProperties.setAvatar(avatarUrl, tempId);
        }catch(Exception e){
            map.put("status", "400");
            return map;
        }
        map.put("status", "200");
        return map;
    }

    //设置用户头像URL
    @PostMapping("/uploadAvatar")
    public String setUserAvatar(UserInfo userInfo){
        int tempId = 1;
        try{
            if(ossProperties.setAvatar(userInfo.getAvatarUrl(), tempId)){
                return "Result! Get";
            }else{
                return "Failed!";
            }
        }catch(Exception e){
            return "Catch Exception";
        }
    }

    //返回用户头像URL
    @GetMapping("/user/getUserAvatar")
    public String getUserInfo(Integer id){
        int tempId = 1;
        return ossProperties.getAvatar(tempId);
    }

}
