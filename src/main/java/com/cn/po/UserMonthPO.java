package com.cn.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author： zjr
 * @Effect:
 * @Date: 2020/11/27
 */


@Data
@AllArgsConstructor
@NoArgsConstructor

public class UserMonthPO {

    private Integer month;
    private Integer count;


}
