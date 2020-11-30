package com.cn;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.Bucket;
import com.aliyun.oss.model.CreateBucketRequest;
import com.aliyun.oss.model.PutObjectRequest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.*;
import java.util.List;

/**
 * @Author： zjr
 * @Effect:
 * @Date: 2020/11/23
 */

@SpringBootTest
public class AliyunOOS {



    // 创建Bucket
    @Test
    void Testchuangjian() {

        // Endpoint以杭州为例，其它Region请按实际情况填写。
        String endpoint = "https://oss-cn-beijing.aliyuncs.com";
        // 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录RAM控制台创建RAM账号。
        String accessKeyId = "LTAI4G7Cz6W3qdhmJYRCAwL9";
        String accessKeySecret = "wlrtRAgvFyVFm3OzsNBBQkTxw00jql";
        String bucketName = "hr-999";

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        // 创建存储空间。
        ossClient.createBucket(bucketName);

        // 关闭OSSClient。
        ossClient.shutdown();

    }



    // 查询所有Bucket
    @Test
    void queryAllBucket(){
        // Endpoint以杭州为例，其它Region请按实际情况填写。
        String endpoint = "http://oss-cn-beijing.aliyuncs.com";
        // 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录RAM控制台创建RAM账号。
        String accessKeyId = "LTAI4G7Cz6W3qdhmJYRCAwL9";
        String accessKeySecret = "wlrtRAgvFyVFm3OzsNBBQkTxw00jql";


        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        // 列举存储空间。
        List<Bucket> buckets = ossClient.listBuckets();

        for (Bucket bucket : buckets) {
            System.out.println(" - " + bucket.getName());
        }

        // 关闭OSSClient。
        ossClient.shutdown();
    }



    // 删除Bucket
    @Test
    void del() {
        // Endpoint以杭州为例，其它Region请按实际情况填写。
        String endpoint = "http://oss-cn-hangzhou.aliyuncs.com";
        // 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建RAM账号。
        String accessKeyId = "LTAI4G7Cz6W3qdhmJYRCAwL9";
        String accessKeySecret = "wlrtRAgvFyVFm3OzsNBBQkTxw00jql";

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        // 删除存储空间。
        ossClient.deleteBucket("hr-2000");

        // 关闭OSSClient。
        ossClient.shutdown();

    }



    // 上传文件
    @Test
    void addFile(){
        // Endpoint以杭州为例，其它Region请按实际情况填写。
        String endpoint = "https://oss-cn-beijing.aliyuncs.com";
        // 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录RAM控制台创建RAM账号。
        String accessKeyId = "LTAI4G7Cz6W3qdhmJYRCAwL9";
        String accessKeySecret = "wlrtRAgvFyVFm3OzsNBBQkTxw00jql";
        String bucketName="hr-999";  //存储空间名  yingx-2005
        String objectName="huohua.jpg";  //保存的文件名   1.MP4  aaa.mp4
        String localFile="D:\\后期项目\\Day6 类别实现，视频设计\\后台视频\\huohua.jpg";   //本地文件位置

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        // 创建PutObjectRequest对象。
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, objectName, new File(localFile));

        // 上传文件。
        ossClient.putObject(putObjectRequest);

        // 关闭OSSClient。
        ossClient.shutdown();
    }


    // 上传视频
    @Test
    void sping() {

        // Endpoint以杭州为例，其它Region请按实际情况填写。
        String endpoint = "https://oss-cn-beijing.aliyuncs.com";
        // 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录RAM控制台创建RAM账号。
        String accessKeyId = "LTAI4G7Cz6W3qdhmJYRCAwL9";
        String accessKeySecret = "wlrtRAgvFyVFm3OzsNBBQkTxw00jql";
        String bucketName="hr-999";  //存储空间名  yingx-2005
        String objectName="动画.mp4";  //保存的文件名   1.MP4  aaa.mp4
        String localFile="D:\\后期项目\\Day6 类别实现，视频设计\\后台视频\\动画.mp4";   //本地文件位置


        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId,accessKeySecret);


        // 上传文件流。
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(localFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        ossClient.putObject(bucketName, objectName, inputStream);


        // 关闭OSSClient。
        ossClient.shutdown();

    }





}
