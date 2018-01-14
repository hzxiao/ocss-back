package com.ocss.back.dao.mapper;


import java.util.Map;

/**
 * Created by tying on 2017/12/26.
 */
public interface UserDao {
    Map<String,Object> verifyUser(Map<String,Object> data);
    Map<String,Object> verifyUserByUsername(String username);


    void updateUser(Map<String,Object> data);
    void addUser(Map<String, Object> data);
    Map<String,Object> selectUserByUsername(String username);
    Map<String,Object> selectUserByUid(Integer userId);
    void addExtendUserByUid(Integer userId);
    void updateExtendUser(Map<String,Object> data);


}
