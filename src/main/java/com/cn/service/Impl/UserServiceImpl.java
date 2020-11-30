package com.cn.service.Impl;

//import com.cn.entity.User;


import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.GetObjectRequest;
import com.cn.annotation.AddCache;
import com.cn.annotation.AddLog;
import com.cn.annotation.DelCache;
import com.cn.dao.UserMapper;
import com.cn.entity.User;
import com.cn.entity.Video;
import com.cn.po.CityPO;
import com.cn.service.UserService;
import org.apache.poi.ss.usermodel.Workbook;
import org.mybatis.logging.Logger;
import org.mybatis.logging.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

/**
 * @Author： zjr
 * @Effect:
 * @Date: 2020/11/19
 */

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class UserServiceImpl implements UserService {


    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
    @Autowired
    private UserMapper userMapper;



    @AddCache
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<User> queryAll(Integer page, Integer rows) {
        Integer begin = (page - 1) * rows;
        Integer end = page * rows;

        return userMapper.queryAll(begin, end);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Integer count() {
        return userMapper.count();
    }





    @DelCache
   @AddLog(value = "修改用户状态")
   @Override
    public void updateStates(User user) {
//        log.info("用户数据：{}",user);

           if (user.getState().equals("正常")) user.setState("冻结");
           else user.setState("正常");
       System.out.println("***********************");

       System.out.println("///////////////////////////////");
        userMapper.updateStates(user);
    }




    @DelCache
    @AddLog(value = "添加用户")
    @Override
    public String addUser(User user) {

        String uuid = UUID.randomUUID().toString();
        user.setId(uuid);
        user.setDate(new Date());
        user.setState("正常");
        userMapper.insertSelective(user);

        return uuid;
    }



    @Override
    public void adduploadUser(MultipartFile head, String id) {
        byte[] bytes = null;
        try {
            bytes = head.getBytes();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 文件名
        String filename = head.getOriginalFilename();
        String newName = new Date().getTime() + "-" + filename;

        // Endpoint以杭州为例，其它Region请按实际情况填写。
        String endpoint = "https://oss-cn-beijing.aliyuncs.com";
        // 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录RAM控制台创建RAM账号。
        String accessKeyId = "LTAI4G7Cz6W3qdhmJYRCAwL9";
        String accessKeySecret = "wlrtRAgvFyVFm3OzsNBBQkTxw00jql";
        String bucketName = "hr-999";  //存储空间名  yingx-2005
        String objectName = "img/" + newName;  //保存的文件名   1.MP4  aaa.mp4
        //String localFile="D:\\后期项目\\Day6 类别实现，视频设计\\后台视频\\huohua.jpg";   //本地文件位置


        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        // 上传Byte数组。
        ossClient.putObject(bucketName, objectName, new ByteArrayInputStream(bytes));



        // 修改图片的路径

        User user = new User();

        user.setId(id);
        user.setHead(objectName);
        String  poiName=  user.getHead().split("img/")[1];
        System.out.println("下载文件名字："+poiName);
        ossClient.getObject(new GetObjectRequest(bucketName, objectName),new File("src/main/webapp/img/"+poiName));
        // 关闭OSSClient。
        ossClient.shutdown();

        userMapper.updateByPrimaryKeySelective(user);



    }



    @DelCache
    @AddLog(value = "删除用户")
    @Override
    public void delUser(User user) {
        // Endpoint以杭州为例，其它Region请按实际情况填写。
        String endpoint = "https://oss-cn-beijing.aliyuncs.com";
        // 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录RAM控制台创建RAM账号。
        String accessKeyId = "LTAI4G7Cz6W3qdhmJYRCAwL9";
        String accessKeySecret = "wlrtRAgvFyVFm3OzsNBBQkTxw00jql";
        String bucketName = "hr-999";  //存储空间名  yingx-2005
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);


        User user1 = userMapper.selectOne(user);
        System.out.println("原文件路径：" + user1.getHead());
        String objectName = "img/" + user1.getHead().split("img/")[1];
        //log.info("videl路径：{}",objectName);
        System.out.println("文件路径为: " + objectName);

        // 删除文件。如需删除文件夹，请将ObjectName设置为对应的文件夹名称。如果文件夹非空，则需要将文件夹下的所有object删除后才能删除该文件夹。
        ossClient.deleteObject(bucketName, objectName);

        // 关闭OSSClient。
        ossClient.shutdown();
        userMapper.delete(user);

    }





    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<CityPO> querySex(String sex) {
        return userMapper.querySex(sex);
    }









    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Map<String, Object> easyPoi() {

        HashMap<String, Object> map = new HashMap<>();

        List<User> users = userMapper.selectAll();
        //导出设置的参数  参数:大标题,工作表名
        ExportParams exportParams = new ExportParams("用户数据", "用户");
        //导出工具   参数:导出的参数,对应的实体类,导出的集合


        for (User user : users) {
            System.out.println("原路径："+user.getHead());
            String picName = user.getHead().split("img/")[1];
            user.setHead("src/main/webapp/img/" + picName);
            System.out.println("新路径："+user.getHead());
        }


        Workbook workbook = ExcelExportUtil.exportExcel(exportParams, User.class, users);
        try {
            workbook.write(new FileOutputStream(new File("E:\\用户信息2.xls")));
            map.put("message", "导出成功");
            map.put("status", "200");
        } catch (IOException e) {
            e.printStackTrace();
            map.put("message", "导出失败");
            map.put("status", "201");
        }
        return map;
    }


    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Integer queryByMonth(String sex, Integer month) {
            return userMapper.queryByMonth(sex, month);
    }


}
