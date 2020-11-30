package com.cn.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author： zjr
 * @Effect:
 * @Date: 2020/11/27
 */



@Data
@AllArgsConstructor
@NoArgsConstructor
public class SexPO {

    private String sex;
    private List<CityPO> cityPOS;



}
