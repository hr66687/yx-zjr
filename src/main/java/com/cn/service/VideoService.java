package com.cn.service;

import com.alibaba.druid.support.json.JSONUtils;
import com.cn.entity.Video;
import com.cn.po.VideoPO;
import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.ls.LSOutput;

import javax.persistence.Id;
import java.util.HashMap;
import java.util.List;

public interface VideoService {

    /**
     * 后台查询所有视频信息
     *
     * @param page
     * @param rows
     * @return
     */
    HashMap<String, Object> queryVideo(Integer page, Integer rows);


    /**
     * 后台添加视频功能本地添加
     *
     * @param video
     * @return
     */
    String addVideo(Video video);

    void uploadvideo(MultipartFile videoPath, String id);


    /***
     * 后台添加视频功能aliyun
     * @param videoPath
     * @param id
     */
    void uploadaliyun(MultipartFile videoPath, String id);


    /**
     * 删除视频功能
     *
     * @param video
     */
    void delaliyun(Video video);


    /**
     * 修改视频
     *
     * @param video
     */
    String updatevideo(Video video);

    void updatealiyun(MultipartFile videoPath, String id);



    /**
     * 前台视屏接口测试
     * @return
     */
    List<VideoPO> queryByReleaseTime();


    /**
     * 前台搜索功能
     * @return
     */
    List<VideoPO> queryByLikeVideoName(String content);


}
