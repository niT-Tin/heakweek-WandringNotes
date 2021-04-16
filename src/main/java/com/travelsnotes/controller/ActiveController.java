package com.travelsnotes.controller;


import com.travelsnotes.dao.HKMapper;
import com.travelsnotes.pojo.Result;
import com.travelsnotes.pojo.ResultCodeEnum;
import com.travelsnotes.pojo.UserActive;
import com.travelsnotes.pojo.UserInfo;
import com.travelsnotes.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class ActiveController {

    @Autowired
    private HKMapper userActiveProperties;

    private UserActive userActive;

    @Autowired
    UserService userService;
    @CrossOrigin
    @GetMapping("/getUserActive")
    public UserActive getActive(@RequestParam(value = "token")String token, HttpServletRequest request) {
        Object attribute = request.getSession().getAttribute(token);
        if (attribute == null) {
            return null;
        }
        int tempId=(int)attribute;
        userActive = new UserActive();
        //前端获得token 获得userId 返回对应user信息
        try {
            userActive.setActiveDays(userActiveProperties.getActiveDays(tempId));
            userActive.setUserName(userActiveProperties.getUsername(tempId));
            userActive.setTxtNum(userActiveProperties.getTxtNum(tempId));
        } catch (Exception e) {
            return null;
        }
        return userActive;
    }

}
