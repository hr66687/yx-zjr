package com.cn.controller;

import com.cn.entity.Video;
import com.cn.service.VideoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * @Author： zjr
 * @Effect:
 * @Date: 2020/11/23
 */

@Controller
@RequestMapping("/video")
public class VideoController {


    private static final Logger log = LoggerFactory.getLogger(VideoController.class);
    @Autowired
    private VideoService videoService;


    @ResponseBody
    @RequestMapping("queryVideo")
    public Map<String, Object> queryVideo(Integer page, Integer rows) {

        return videoService.queryVideo(page, rows);
    }


    @ResponseBody
    @RequestMapping("edit")
    public String edit(Video video, String oper) {
        log.info("获取到的对象：{}", video);

        String result = null;

        if (oper.equals("add")) {
            result = videoService.addVideo(video);
        }
        if (oper.equals("edit")) {
            result = videoService.updatevideo(video);
        }

        if (oper.equals("del")) {
            videoService.delaliyun(video);
        }

        return result;

    }


    @ResponseBody
    @RequestMapping("uploadvideo")
    public void uploadvideo(MultipartFile videoPath, String id) {
        System.out.println(videoPath.getOriginalFilename());
        System.out.println(id);
        videoService.uploadaliyun(videoPath, id);
    }




    @ResponseBody
    @RequestMapping("updatealiyun")

    public void updatealiyun(MultipartFile videoPath, String id) {
        videoService.updatealiyun(videoPath, id);
    }
}
