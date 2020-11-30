package com.cn.dao;


import com.cn.entity.Video;
import com.cn.po.VideoPO;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface VideoMapper extends Mapper<Video> {


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