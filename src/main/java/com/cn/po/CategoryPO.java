package com.cn.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author： zjr
 * @Effect:
 * @Date: 2020/11/25
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryPO {


    private String id;
    private String cateName;
    private String levels;
    private String parentId;

    private List<CategoryPO> categoryList;


}
