package com.travelsnotes.dao;

import com.travelsnotes.pojo.UserInfo;
import com.travelsnotes.pojo.UserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface UserMapper {
   public UserInfo queryById(int id);
   public UserInfo queryByName(String name);
   public UserInfo queryByTele(String usertele);
   public int registerUser(UserInfo userInfo);
   public int updateUser(UserInfo user);
   public int deleteUser(String name);
}
