package com.cn.dao;

import com.cn.entity.Adimin;

import tk.mybatis.mapper.common.Mapper;

public interface AdiminMapper extends Mapper<Adimin> {

    //管理员登陆

    Adimin queryByName(String username);



}