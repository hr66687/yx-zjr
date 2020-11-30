package com.cn.controller;

import com.cn.entity.Category;
import com.cn.service.CategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author： zjr
 * @Effect:
 * @Date: 2020/11/20
 */

@Controller
@RequestMapping("category")
public class CategoryController {

    private static final Logger log = LoggerFactory.getLogger(CategoryController.class);
    @Autowired
    private CategoryService categoryService;

    // 查询一级类别
    @RequestMapping("queryOne")
    @ResponseBody
    public Map<String, Object> queryOne(Integer page, Integer rows) {

        HashMap<String, Object> map = new HashMap<>();


        List<Category> categories = categoryService.queryOne(page, rows);
        log.info("一级查询到的数据： {}", categories);

        Integer count = categoryService.countOne();
        log.info("一级查询到的总条数： {}", count);

        Integer total = count % rows == 0 ? count / rows : count / rows + 1;   // 计算页数

        map.put("page", page);  // 当前页
        map.put("rows", categories);  // 每页显示记录数
        map.put("total", total);   // 总页数
        map.put("records", count);  //  总条数

        return map;

    }


    // 展示二级类别
    @RequestMapping("queryTwo")
    @ResponseBody
    public Map<String, Object> queryTwo(Integer page, Integer rows, String parentid) {

        HashMap<String, Object> map = new HashMap<>();

        List<Category> querytwo = categoryService.querytwo(page, rows, parentid);
        log.info("二级类别的数据");
        Integer counttwo = categoryService.counttwo(parentid);
        log.info("二级类别总条数");

        Integer total = counttwo % rows == 0 ? counttwo / rows : counttwo / rows + 1;

        map.put("page", page);
        map.put("rows", querytwo);
        map.put("total", total);
        map.put("records", querytwo);

        return map;
    }


    /***
     * 后台增删改
     * @param category
     * @param oper
     * @return
     */
    @RequestMapping("exit")
    @ResponseBody
    public Map<String, Object> exit(Category category, String oper) {

        HashMap<String, Object> map = new HashMap<>();

        try {
            categoryService.exitCategory(category, oper);
            map.put("status", "ok");
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", e.getMessage());
        }
        return map;
    }







}
