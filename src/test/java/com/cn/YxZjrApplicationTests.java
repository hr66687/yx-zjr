package com.cn;

import com.cn.dao.AdiminMapper;
import com.cn.entity.Adimin;
import com.cn.entity.AdiminExample;
import com.cn.service.AdiminService;
import org.apache.ibatis.session.RowBounds;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import javax.annotation.Resources;
import javax.swing.plaf.basic.BasicEditorPaneUI;
import java.util.List;
import java.util.UUID;

@SpringBootTest
class YxZjrApplicationTests {


    /**
     * @Resource JDK 提供的    根据名称注入 注： 如果名称找不到就直接类型注入
     * @Autowired 是spring 提供的  根据类型注入
     */


    @Resource
    AdiminMapper adiminMapper;


    @Resource
    AdiminService adiminService;


    @Test
    void contextLoadsAdmin() {

       // List<Adimin> adimins = adiminMapper.selectAll();
       // adimins.forEach(admin -> System.out.println(admin));

       /* Adimin adimin = adiminMapper.selectByPrimaryKey("1");
        System.out.println(adimin);*/

        Adimin admin = adiminService.queryByName("zjr");
        System.out.println(admin);

    }

    @Test
    void queryAll () {

//        List<Adimin> adimins = adiminMapper.selectAll();    selectAll 查所有
//        adimins.forEach(o -> System.out.println(o));
//        Adimin adimin = adiminMapper.selectByPrimaryKey("1");   selectByPrimaryKey 根据主键id 查询
//        System.out.println(adimin);


        // 条件查询
        AdiminExample example = new AdiminExample();
        // 设置条件
//        example.createCriteria().andIdEqualTo("1");    // 条件查询id 为1的数据
//        example.createCriteria().andIdNotEqualTo("1");  // 条件查询id 不为1的数据
//        example.createCriteria().andIdBetween("0", "3");    //id在1到6之间的数据
//        example.createCriteria().andIdNotBetween("0", "3");
//        example.createCriteria().andIdIsNull();
//        example.createCriteria().andIdIsNotNull();   // id不为null

//        example.createCriteria().andIdLike("%2%");  // 模糊查询id为2
//        example.createCriteria().andIdNotLike("%2%");    // 模糊查询id不为2
        example.createCriteria().andIdGreaterThan("1");  //id(大于) 多少

        // 执行
        List<Adimin> adimins = adiminMapper.selectByExample(example);
        adimins.forEach(o-> System.out.println(o));


    }


    @Test
    void test1 () {

        AdiminExample example = new AdiminExample();
        example.createCriteria().andIdEqualTo("6");
        Adimin adimin = adiminMapper.selectOneByExample(example);
        System.out.println(adimin);

    }


    @Test
    void Pagequey () {

        AdiminExample example = new AdiminExample();
        RowBounds rowBounds = new RowBounds(0, 3);
        List<Adimin> adimins = adiminMapper.selectByExampleAndRowBounds(example, rowBounds);
        adimins.forEach(o -> System.out.println(o));

    }


    @Test
    void count () {

        Adimin adimin = new Adimin();
        int count = adiminMapper.selectCount(adimin);
        System.out.println(count);

    }


    @Test
    void inster () {

        Adimin adimin = new Adimin("11", null, "ll");
//        adiminMapper.insert(adimin);
//        adiminMapper.insertSelective(adimin);
    }




    @Test
    void update () {

        Adimin adimin = new Adimin();
        adimin.setId("10");
        adimin.setUsername("99999");
//        adiminMapper.updateByPrimaryKey(adimin);   仅修改要修改的字段  不修改的字段则都为空
//        adiminMapper.updateByPrimaryKeySelective(adimin);   仅修改要修改的字段     不修改的字段为原值
    }


    @Test
    void update1 () {

        // 设置修改数据
        Adimin adimin = new Adimin();
        adimin.setId("10");
        adimin.setUsername("99999");
        // 添加修改条件

        AdiminExample example = new AdiminExample();
//        example.createCriteria().andIdEqualTo("10");
        example.createCriteria().andPasswordEqualTo("56565656");
        // 执行
        adiminMapper.updateByExample(adimin, example);

        System.out.println(adimin);
    }



}
