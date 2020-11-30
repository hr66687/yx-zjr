package com.cn.app;

import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.cn.common.CommonResult;
import com.cn.po.CategoryPO;
import com.cn.po.VideoPO;
import com.cn.service.CategoryService;
import com.cn.service.VideoService;
import com.cn.util.CustomizeCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

import java.util.List;

import static com.cn.util.AliyunUtils.*;


/**
 * @Author： zjr
 * @Effect:
 * @Date: 2020/11/25
 */


@RestController
@RequestMapping("app")
public class Appcontroller {


    @Resource
    VideoService videoService;


    @Resource
    CategoryService categoryService;




    private static final Logger log = LoggerFactory.getLogger(Appcontroller.class);


    /**
     * 前台测试验证码
     *
     * @param phone
     * @return
     * @throws Exception
     */

    @RequestMapping("getPhoneCode")
    public Object getPhoneCode(String phone) throws Exception {


        Integer code = CustomizeCode.generateValidateCode(6);
        // Integer 转String
        String templateParam = String.valueOf(code);
        log.info("验证码：{}", templateParam);
        String message = null;
        try {
            message = sendPhoneMsg(phone, templateParam);
            return new CommonResult().success(message, phone);
        } catch (Exception e) {
            return new CommonResult().failed(message);
        }
    }


    /**
     * 前台视屏接口测试
     *
     * @return
     */
    @RequestMapping("queryByReleaseTime")
    public CommonResult queryByReleaseTime() {

        try {
            List<VideoPO> videoPOS = videoService.queryByReleaseTime();
            return new CommonResult().success("查询成功", videoPOS);
        } catch (Exception e) {
            return new CommonResult().failed("查询失败");
        }


    }


    /**
     * 前台分类接口
     *
     * @return
     */
            @RequestMapping("queryAllCategory")
            public CommonResult queryAllCategory() {

                try {
            List<CategoryPO> categoryPOS = categoryService.queryAllCategory();
            return new CommonResult().success("查询成功", categoryPOS);
        } catch (Exception e) {
            return new CommonResult().failed("查询失败");
        }

    }


    @RequestMapping("queryByLikeVideoName")
    public CommonResult queryByLikeVideoName(String content) {

        try {
            List<VideoPO> videoPOS = videoService.queryByLikeVideoName(content);
            return new CommonResult().success("查询成功", videoPOS);
        } catch (Exception e) {
            return new CommonResult().failed("查询失败");
        }
    }
}
