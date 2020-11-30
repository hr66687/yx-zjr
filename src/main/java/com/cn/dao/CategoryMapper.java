package com.cn.dao;


import com.cn.entity.Category;
import com.cn.po.CategoryPO;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface CategoryMapper extends Mapper<Category> {


    List<CategoryPO> queryAllCategory();




}