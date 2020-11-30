package com.cn.service.Impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.cn.annotation.AddCache;
import com.cn.annotation.AddLog;
import com.cn.annotation.DelCache;
import com.cn.dao.VideoMapper;
import com.cn.entity.Video;
import com.cn.entity.VideoExample;
import com.cn.po.VideoPO;
import com.cn.service.VideoService;

import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;


import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/**
 * @Author： zjr
 * @Effect:
 * @Date: 2020/11/23
 */
@Service
@Transactional(propagation = Propagation.REQUIRED)
public class VideoServiceImpl implements VideoService {

    private static final Logger log = LoggerFactory.getLogger(VideoServiceImpl.class);
    @Autowired
    private VideoMapper videoMapper;


    @Autowired
    private HttpServletRequest request;

    @AddCache
    @Override
    public HashMap<String, Object> queryVideo(Integer page, Integer rows) {

        HashMap<String, Object> map = new HashMap<>();

        map.put("page", page);

        // 条件对象
        VideoExample example = new VideoExample();
        // 分页参数
        RowBounds rowBounds = new RowBounds((page - 1) * rows, rows);
        // 查询分页数据
        List<Video> videos = videoMapper.selectByExampleAndRowBounds(example, rowBounds);

        map.put("rows", videos);


        //查询总条数
        int count = videoMapper.selectCountByExample(example);

        map.put("records", count);


        // 设置总页数

        Integer total = count % rows == 0 ? count / rows : count / rows + 1;

        map.put("total", total);

        return map;

    }


    @DelCache
    @AddLog(value = "添加视频")
    @Override
    public String addVideo(Video video) {

        String uuid = UUID.randomUUID().toString();
        video.setId(uuid);
        video.setUploadTime(new Date());

        videoMapper.insertSelective(video);

        return uuid;
    }

    @Override
    public void uploadvideo(MultipartFile videoPath, String id) {

        String realPath = request.getSession().getServletContext().getRealPath("/files");
        log.debug("地址：{}", realPath);

        //判断上传文件夹是否存在
        File file = new File(realPath);
        if (!file.exists()) file.mkdirs();  //创建文件夹

        // 获取文件名
        String filename = videoPath.getOriginalFilename();
        //给文件拼接时间戳
        String newName = new Date().getTime() + "-" + filename;

        try {
            videoPath.transferTo(new File(realPath, newName));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 修改数据
        VideoExample example = new VideoExample();
        example.createCriteria().andIdEqualTo(id);

        Video video = new Video();
        video.setVideoPath(newName);

        videoMapper.updateByExampleSelective(video, example);

    }


    /**
     * aliyun上传视频
     *
     * @param videoPath
     * @param id
     */



    @Override
    public void uploadaliyun(MultipartFile videoPath, String id) {
        byte[] bytes = null;
        try {
            bytes = videoPath.getBytes();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 文件名
        String filename = videoPath.getOriginalFilename();
        String newName = new Date().getTime() + "-" + filename;

        // Endpoint以杭州为例，其它Region请按实际情况填写。
        String endpoint = "https://oss-cn-beijing.aliyuncs.com";
        // 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录RAM控制台创建RAM账号。
        String accessKeyId = "LTAI4G7Cz6W3qdhmJYRCAwL9";
        String accessKeySecret = "wlrtRAgvFyVFm3OzsNBBQkTxw00jql";
        String bucketName = "hr-999";  //存储空间名  yingx-2005
        String objectName = "zjr/" + newName;  //保存的文件名   1.MP4  aaa.mp4
        //String localFile="D:\\后期项目\\Day6 类别实现，视频设计\\后台视频\\huohua.jpg";   //本地文件位置


        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        // 上传Byte数组。
        ossClient.putObject(bucketName, objectName, new ByteArrayInputStream(bytes));


        // 关闭OSSClient。
        ossClient.shutdown();

        // 修改图片的路径
        Video video = new Video();

        video.setId(id);
        String videoPath1 = "https://hr-999.oss-cn-beijing.aliyuncs.com/" + objectName;
        // 截取视频第一帧  jqGrid前台 自定义函数二次渲染 获取value值
        video.setCoverPath(videoPath1 + "?x-oss-process=video/snapshot,t_0,f_jpg,w_0,h_0,m_fast,ar_auto");
        video.setVideoPath(videoPath1);

        videoMapper.updateByPrimaryKeySelective(video);
    }


    /**
     * 删除视频功能
     *
     * @param video
     */
    @DelCache
    @AddLog(value = "删除视频")
    @Override
    public void delaliyun(Video video) {

        // Endpoint以杭州为例，其它Region请按实际情况填写。
        String endpoint = "https://oss-cn-beijing.aliyuncs.com";
        // 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录RAM控制台创建RAM账号。
        String accessKeyId = "LTAI4G7Cz6W3qdhmJYRCAwL9";
        String accessKeySecret = "wlrtRAgvFyVFm3OzsNBBQkTxw00jql";
        String bucketName = "hr-999";  //存储空间名  yingx-2005
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);


        Video video1 = videoMapper.selectOne(video);
        System.out.println("原文件路径：" + video1.getVideoPath());
        String objectName = "zjr/" + video1.getVideoPath().split("zjr/")[1];
        //log.info("videl路径：{}",objectName);
        System.out.println("文件路径为: " + objectName);

        // 删除文件。如需删除文件夹，请将ObjectName设置为对应的文件夹名称。如果文件夹非空，则需要将文件夹下的所有object删除后才能删除该文件夹。
        ossClient.deleteObject(bucketName, objectName);

        // 关闭OSSClient。
        ossClient.shutdown();
        videoMapper.delete(video);
    }


    @DelCache
    @AddLog(value = "修改视频")
    @Override
    public String updatevideo(Video video) {


        video.setVideoPath(null);
        String id = video.getId();
        videoMapper.updateByPrimaryKeySelective(video);

        return id;

    }


    /**
     * 修改视频
     *
     * @param videoPath
     * @param id
     */

    @Override
    public void updatealiyun(MultipartFile videoPath, String id) {
        System.out.println(videoPath.isEmpty());


        // 查询一条原有数据
        VideoExample example = new VideoExample();
        example.createCriteria().andIdEqualTo(id);
        Video video = videoMapper.selectOneByExample(example);
        System.out.println("查询一个数据：" + video);

        // 判断
        if (videoPath.isEmpty() == false && videoPath != null && videoPath.getOriginalFilename() != "") {
            // 删除阿里云视频

            // Endpoint以杭州为例，其它Region请按实际情况填写。
            String endpoint = "https://oss-cn-beijing.aliyuncs.com";
            // 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录RAM控制台创建RAM账号。
            String accessKeyId = "LTAI4G7Cz6W3qdhmJYRCAwL9";
            String accessKeySecret = "wlrtRAgvFyVFm3OzsNBBQkTxw00jql";
            String bucketName = "hr-999";  //存储空间名  yingx-2005
            // 创建OSSClient实例。
            OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);


            System.out.println("原文件路径：" + video.getVideoPath());
            String objectName = "zjr/" + video.getVideoPath().split("zjr/")[1];


            //log.info("videl路径：{}",objectName);
            System.out.println("文件路径为: " + objectName);

            // 删除文件。如需删除文件夹，请将ObjectName设置为对应的文件夹名称。如果文件夹非空，则需要将文件夹下的所有object删除后才能删除该文件夹。
            ossClient.deleteObject(bucketName, objectName);

            // 重新上传

            String filename1 = videoPath.getOriginalFilename();
            System.out.println("旧文件名：" + filename1);
            String newName = new Date().getTime() + "-" + filename1;

            // 保存文件名
            String objectName1 = "zjr/" + newName;

            byte[] bytes = null;
            try {
                bytes = videoPath.getBytes();
            } catch (IOException e) {
                e.printStackTrace();
            }


            // 上传Byte数组。
            ossClient.putObject(bucketName, objectName1, new ByteArrayInputStream(bytes));


            // 关闭OSSClient。
            ossClient.shutdown();

            // 修改图片的路径

            String videoPath1 = "https://hr-999.oss-cn-beijing.aliyuncs.com/" + objectName1;
            video.setId(id);
            // 截取视频第一帧  jqGrid前台 自定义函数二次渲染 获取value值
            video.setCoverPath(videoPath1 + "?x-oss-process=video/snapshot,t_0,f_jpg,w_0,h_0,m_fast,ar_auto");
            video.setVideoPath(videoPath1);

            videoMapper.updateByPrimaryKeySelective(video);
        }

    }


    /**
     * 前台视屏接口测试
     * @return
     */
    @Override
    public List<VideoPO> queryByReleaseTime() {

        List<VideoPO> videos = videoMapper.queryByReleaseTime();
        for (VideoPO VideoPO : videos) {

            // 获取视频id
            String id = VideoPO.getId();

            // 根据视频id  redis   查询点赞数
            VideoPO.setLikeCount(888);

        }
        return videos;
    }


    /**
     * 前台搜索功能
     *
     * @param content
     * @return
     */
    @Override
    public List<VideoPO> queryByLikeVideoName(String content) {


        // 如果搜索条件为空则不执行查询
        if(content==null || content=="") return null;

        List<VideoPO> videoPOS = videoMapper.queryByLikeVideoName("%"+content+"%");

        for (VideoPO videoPO : videoPOS) {
            // 获取视频id
            String id = videoPO.getId();

            // 根据视频id  redis   查询点赞数
            videoPO.setLikeCount(888);

        }
        return videoPOS;
    }


}
