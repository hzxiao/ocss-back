package com.ocss.back.service;

import java.util.Map;

/**
 * Created by tying on 2017/12/26.
 */
public interface UserService {
    Map<String,Object> verifyUser(Map<String,Object> data);
    Map<String,Object> addUser(Map<String,Object> data);
    Map<String, Object> getUserByUsername(String username);
    Map<String, Object> getUserByUid(Integer userId);
    Map<String,Object> updateUser(Map<String,Object> data);
    Map<String,Object> updateExtendUser(Map<String,Object> data);
}
