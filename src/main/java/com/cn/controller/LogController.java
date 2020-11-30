package com.cn.controller;

import com.cn.entity.Video;
import com.cn.service.LogService;
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
@RequestMapping("/log")
public class LogController {


    private static final Logger log = LoggerFactory.getLogger(LogController.class);
    @Autowired
    private LogService logService;



    @ResponseBody
    @RequestMapping("queryAlllog")
    public Map<String, Object> queryAlllog(Integer page, Integer rows) {

        return logService.queryAlllog(page, rows);
    }


}
