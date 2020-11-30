package com.cn.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author： zjr
 * @Effect:
 * @Date: 2020/11/25
 */

@Data
@AllArgsConstructor
@NoArgsConstructor

public class VideoPO {

    private String id;
    private String videoTitle;
    private String cover;
    private String path;
    private String uploadTime;
    private String description;



    private Integer likeCount;    // 点赞
    private String cateName;   // 类别
    private String userPhoto;  // 用户头像



//     "categoryId": "16",
//             "userId": "1",
//             "userName": "xiaohei"


    // 前台搜索需要的属性
    private String categoryId;   //所属类别id
    private String userId;      // 所属用户id
    private String userName;  //所属用户名


}
