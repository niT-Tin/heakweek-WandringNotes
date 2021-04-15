package com.travelsnotes.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfo {
    public UserInfo(String userName, String userPassword, String phoneNumber) {
        this.userName = userName;
        this.userPassword = userPassword;
        this.phoneNumber = phoneNumber;
    }
    int userId;
    String userName;
    String userPassword;
    String phoneNumber;
    String avatarUrl;
}
