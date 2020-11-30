package com.cn;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.cn.dao.UserMapper;
import com.cn.entity.User;
import com.cn.service.UserService;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * @Author： zjr
 * @Effect:
 * @Date: 2020/11/19
 */

@SpringBootTest(classes = YxZjrApplication.class)
@RunWith(SpringRunner.class)
public class testUser {


    @Autowired
    private UserService userService;


    @Autowired
    private UserMapper userMapper;

    @Test
    public void test() {
        List<User> users = userService.queryAll(1, 2);
        users.forEach(user -> System.out.println(user));

    }


    @Test
    public void test0() {
        List<User> users = userMapper.queryAll(0, 2);
        users.forEach(user -> System.out.println(user));

    }



    @Test
    public void t0(){
        List<User> users = userMapper.selectAll();
        System.out.println(users);
        System.out.println("GitHub");
        System.out.println("本地推送到GitHub");
        
        
    }





    @Test
    public void easyPOI() {

        List<User> users = userMapper.selectAll();
        //导出设置的参数  参数:大标题,工作表名
        ExportParams exportParams = new ExportParams("用户数据", "用户");
        //导出工具   参数:导出的参数,对应的实体类,导出的集合
        for (User user : users) {
            System.out.println("原路径：" + user.getHead());
            String picName = user.getHead().split("img/")[1];
            user.setHead("src/main/webapp/bootstrap/upload/" + picName);
            // log.debug("c: {}", user.getPicImg());
            System.out.println("新路径" + user.getHead());
        }
        Workbook workbook = ExcelExportUtil.exportExcel(exportParams, User.class, users);
        try {
            workbook.write(new FileOutputStream(new File("E:\\用户信息6.xls")));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
