package com.cn.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;



@Data
@AllArgsConstructor
@NoArgsConstructor

@Table(name = "yx_log")
public class Log implements Serializable {

    @Id
    private String id;

    private String name;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date times;

    private String options;

    private String status;

}