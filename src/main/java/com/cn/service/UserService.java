package com.cn.service;

import com.cn.entity.User;
import com.cn.entity.Video;
import com.cn.po.CityPO;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface UserService {

        List<User> queryAll(Integer page, Integer rows);

        Integer count();

        void updateStates(User user);


    /**
     * 后台添加用户功能
     * @param user
     * @return
     */

    String addUser(User user);

    void adduploadUser(MultipartFile head, String id);


    /**
     * 删除图片
     * @param user
     */
    void delUser(User user);




    /**
     * 用户分布
     * @param sex
     * @return
     */
    List<CityPO> querySex(String sex);


    /**
     * 导出用户查所有
     * @return
     */
    Map<String, Object> easyPoi();



    /**
     * 用户统计
     * @param sex
     * @param month
     * @return
     */
    Integer queryByMonth(String sex,Integer month);




}
