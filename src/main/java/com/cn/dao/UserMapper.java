package com.cn.dao;

import com.cn.entity.User;
import com.cn.entity.UserExample;
import java.util.List;

import com.cn.po.CityPO;
import org.apache.ibatis.annotations.Param;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.common.Mapper;


public interface UserMapper extends Mapper<User> {

    /**
     * 后台用户信息展示
     */

    List<User> queryAll(@Param("begin")Integer begin, @Param("end")Integer end);

    Integer count();



    /***
     * 查看用户状态
     */
    void updateStates(User user);


    /**
     * 导出用户查所有
     * @return
     */
    List<User> queryAllExport();


    /**
     * 用户分布
     * @param sex
     * @return
     */
    List<CityPO> querySex(String sex);


    /**
     * 用户统计
     * @param sex
     * @param month
     * @return
     */
    Integer queryByMonth(@Param("sex") String sex, @Param("month") Integer month);



}
