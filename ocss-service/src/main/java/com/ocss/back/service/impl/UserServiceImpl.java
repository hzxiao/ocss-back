package com.ocss.back.service.impl;

import com.ocss.back.dao.mapper.UserDao;
import com.ocss.back.filter.util.JwtUtil;
import com.ocss.back.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by tying on 2017/12/26.
 */
@Service
public class UserServiceImpl implements UserService {
    private static Logger log = Logger.getLogger(UserServiceImpl.class);


    @Resource
    private UserDao userDao;
    @Override
    public Map<String, Object> verifyUser(Map<String, Object> data) {
        Map<String,Object> result = new HashMap<>();
        Map<String,Object> user = userDao.verifyUser(data);
        if (ObjectUtils.isEmpty(user)){
            return null;
        }
        Map<String,Object> updateParam = new HashMap<>();
        updateParam.put("userId",user.get("userId"));
        updateParam.put("bisStatus",user.get("bisStatus"));
        //改变用户状态
        userDao.updateUser(updateParam);
        //获取token
        String token = JwtUtil.generToken(user.get("userId").toString(),null,user.get("username").toString());
        user.put("token",token);
        result.put("user",user);
        return result;
    }

    @Override
    public Map<String,Object> addUser(Map<String, Object> data) {
        Map<String,Object> result = new HashMap<>();
        data.put("author",data.get("username"));
        userDao.addUser(data);
        Map<String,Object> user = getUserByUsername(data.get("username").toString());
        if (user==null){
           return null;
        }
        String token = JwtUtil.generToken(user.get("userId").toString(),null,user.get("username").toString());
        user.put("token",token);
        result.put("user",user);

        // 添加拓展信息
        userDao.addExtendUserByUid((Integer)user.get("userId"));
        return result;
    }

    @Override
    public Map<String, Object> getUserByUsername(String username) {
        return userDao.selectUserByUsername(username);
    }

    @Override
    public Map<String, Object> getUserByUid(Integer userId) {
        return userDao.selectUserByUid(userId);
    }

    @Override
    public Map<String, Object> updateUser(Map<String, Object> data) {
        Map<String,Object> result = new HashMap<>();
        userDao.updateUser(data);
        Map<String,Object> user = getUserByUid((Integer) data.get("userId"));
        if (user==null){
            return null;
        }
        if (!ObjectUtils.isEmpty(data.get("pwd"))&&!ObjectUtils.isEmpty(data.get("token"))){
            String token = JwtUtil.updateToken(data.get("token").toString());
            user.put("token",token);
        }

        result.put("user",user);

        return result;
    }

    @Override
    public Map<String, Object> updateExtendUser(Map<String, Object> data) {
        Map<String,Object> result = new HashMap<>();
        userDao.updateExtendUser(data);
        Map<String,Object> user = getUserByUid((Integer) data.get("userId"));
        if (ObjectUtils.isEmpty(user)){
            return null;
        }
        result.put("user",user);

        return result;
    }


}
