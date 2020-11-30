package com.cn;

import com.cn.dao.CategoryMapper;
import com.cn.entity.Category;
import com.cn.entity.CategoryExample;
import com.cn.service.CategoryService;
import org.apache.ibatis.session.RowBounds;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @Author： zjr
 * @Effect:
 * @Date: 2020/11/19
 */

@SpringBootTest(classes = YxZjrApplication.class)
@RunWith(SpringRunner.class)
public class testCategory {


    @Autowired
    private CategoryService categoryService;


    @Autowired
    private CategoryMapper categoryMapper;

    @Test
    public void test() {
        // 设置条件
        CategoryExample example = new CategoryExample();

       // HashMap<String, Object> page = categoryService.queryPage(0, 4);
        //System.out.println(page);
    }


    @Test
    public void test2() {

        Integer integer = categoryService.countOne();
        System.out.println(integer);


    }


    @Test
    public void test3() {


        Integer integer = categoryService.counttwo("1");
        System.out.println(integer);


    }


    @Test
    public void two() {

        CategoryExample example = new CategoryExample();

        example.createCriteria().andLevelsEqualTo(2).andParentIdEqualTo("6");

        RowBounds rowBounds = new RowBounds(0, 3);

        List<Category> categories = categoryMapper.selectByExampleAndRowBounds(example, rowBounds);
        categories.forEach(o -> System.out.println(o));

    }



        @Test
    public void one() {


        CategoryExample example = new CategoryExample();
        // 标准格式
        example.createCriteria().andLevelsEqualTo(1);
        // 分页查询
        RowBounds rowBounds = new RowBounds(0, 3);

        List<Category> categories = categoryMapper.selectByExampleAndRowBounds(example, rowBounds);

        categories.forEach(category -> System.out.println(category));

    }




}
