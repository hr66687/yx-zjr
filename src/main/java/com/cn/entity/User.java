package com.cn.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.gson.internal.$Gson$Types;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;



@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "yx_user")


public class User implements Serializable {

    @Id
    @Excel(name = "ID",width = 45)
    private String id;

    @Excel(name = "头像",type = 2,width = 30,height = 20)
    private String head;

    @Excel(name = "名字")
    private String username;

    @Excel(name = "简介")
    private String brief;

    @Excel(name = "学分")
    private String scroe;

    @Excel(name = "密码")
    private String password;

    @Excel(name = "状态")
    private String state;

    @Excel(name = "手机号")
    private String phone;


    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "注册时间",exportFormat = "yyyy-MM-dd")
    private Date date;



    @Excel(name = "城市")
    private String city;

    @Excel(name = "性别")
    private String sex;


}