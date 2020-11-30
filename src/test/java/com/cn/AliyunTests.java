package com.cn;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.GetObjectRequest;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.cn.dao.UserMapper;
import com.cn.entity.User;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@SpringBootTest
public class AliyunTests {


    @Resource
    UserMapper userMapper;


    //说明 有备注无需修改的位置请勿改动
    public static SendSmsResponse queryPhone() throws Exception {

        //设置超时时间-可自行调整
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");


        //初始化ascClient需要的几个参数
        final String product = "Dysmsapi";//短信API产品名称（短信产品名固定，无需修改）
        final String domain = "dysmsapi.aliyuncs.com";//短信API产品域名（接口地址固定，无需修改）

        //替换成你的AK
        final String accessKeyId = "LTAI4G7GPEdeRf1tuXmeFD8m";//你的accessKeyId,参考本文档步骤2
        final String accessKeySecret = "IzG0uQl3prdBjuY4yrhqSrcVl1mAfM";//你的accessKeySecret，参考本文档步骤2


        //初始化ascClient,暂时不支持多region（请勿修改）
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
        DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
        IAcsClient acsClient = new DefaultAcsClient(profile);

        //组装请求对象
        SendSmsRequest request = new SendSmsRequest();
        //使用post提交
        request.setMethod(MethodType.POST);
        //必填:待发送手机号。支持以逗号分隔的形式进行批量调用，批量上限为1000个手机号码,批量调用相对于单条调用及时性稍有延迟,验证码类型的短信推荐使用单条调用的方式；发送国际/港澳台消息时，接收号码格式为国际区号+号码，如“85200000000”

        request.setPhoneNumbers("18734382439,18335004427");


        //必填:短信签名-可在短信控制台中找到
        request.setSignName("小蛋黄");
        //必填:短信模板-可在短信控制台中找到，发送国际/港澳台消息时，请使用国际/港澳台短信模版
        request.setTemplateCode("SMS_205605542");
        //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
        //友情提示:如果JSON中需要带换行符,请参照标准的JSON协议对换行符的要求,比如短信内容中包含\r\n的情况在JSON中需要表示成\\r\\n,否则会导致JSON在服务端解析失败
        //参考：request.setTemplateParam("{\"变量1\":\"值1\",\"变量2\":\"值2\",\"变量3\":\"值3\"}")
        request.setTemplateParam("{\"code\":\"383838\"}");
        //可选-上行短信扩展码(扩展码字段控制在7位或以下，无特殊需求用户请忽略此字段)
        //request.setSmsUpExtendCode("90997");

        //可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
        request.setOutId("yourOutId");

        //请求失败这里会抛ClientException异常
        SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
        if (sendSmsResponse.getCode() != null && sendSmsResponse.getCode().equals("OK")) {
            //请求成功
        }
        return sendSmsResponse;
    }

    @Test
    public void testqueryPhone() {
        try {
            SendSmsResponse response = queryPhone();

            String message = response.getCode();


            System.out.println(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     *
     */

    @Test
    public void testDownLoad() {

        List<User> users = userMapper.queryAllExport();


        //导出设置的参数  参数:大标题,工作表名
        ExportParams exportParams = new ExportParams("用户数据", "用户");

        // Endpoint以杭州为例，其它Region请按实际情况填写。
        String endpoint = "http://oss-cn-beijing.aliyuncs.com";
        // 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建RAM账号。
        String accessKeyId = "LTAI4G7Cz6W3qdhmJYRCAwL9";
        String accessKeySecret = "wlrtRAgvFyVFm3OzsNBBQkTxw00jql";
        String bucketName = "hr-999";  //存储空间名


        // String objectName = null;  //文件名


        for (User user : users) {
            String picName = user.getHead().split("img")[1];
            user.setHead("src/main/webapp/bootstrap/upload" + picName);
            System.out.println("新路径：" + user.getHead());
        }


        Workbook workbook = ExcelExportUtil.exportExcel(exportParams, User.class, users);


        //  String localFile="/src/main/webapp/bootstrap/upload"+objectName;  //下载本地地址  地址加保存名字

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        // 下载OSS文件到本地文件。如果指定的本地文件存在会覆盖，不存在则新建。
        // ossClient.getObject(new GetObjectRequest(bucketName, objectName), new File(localFile));

        // 关闭OSSClient。
        ossClient.shutdown();
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
            workbook.write(new FileOutputStream(new File("E:\\用户信息2.xls")));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void t0(){
        List<User> users = userMapper.selectAll();
        System.out.println(users);
    }



}
