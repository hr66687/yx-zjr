package com.cn.service;


import com.cn.entity.Category;
import com.cn.po.CategoryPO;

import java.util.List;

public interface CategoryService {

    /**
     * 查询一级类别
     *
     * @return
     */
    Integer countOne();

    List<Category> queryOne(Integer page, Integer rows);


    /**
     * 查询二级类别
     *
     * @return
     */
    Integer counttwo(String parentid);

    List<Category> querytwo(Integer page, Integer rwos, String parentid);


    /**
     * 后台一级二级类别的添加   修改  删除
     * @param category
     */
    void exitCategory(Category category,String oper);


    /**
     * 前台分类展示
     * @return
     */
    List<CategoryPO> queryAllCategory();



}