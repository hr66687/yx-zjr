package com.cn.service;


import com.cn.entity.Log;
import com.cn.entity.User;

import java.util.HashMap;
import java.util.List;

/**
 * @Author： zjr
 * @Effect:
 * @Date: 2020/11/24
 */

public interface LogService {

    /**
     * 后台查询所有视频信息
     *
     * @param page
     * @param rows
     * @return
     */
    HashMap<String, Object> queryAlllog(Integer page, Integer rows);



}
