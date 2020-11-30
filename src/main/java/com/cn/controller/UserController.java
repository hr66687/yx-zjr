package com.cn.controller;

import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.cn.entity.User;
import com.cn.entity.Video;
import com.cn.po.CityPO;
import com.cn.po.SexPO;
import com.cn.service.UserService;
import com.cn.util.CustomizeCode;
import com.cn.util.ImageCodeUtil;
import com.cn.util.MonthUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.util.*;


import static com.cn.util.AliyunUtils.sendPhoneMsg;


/**
 * @Author： zjr
 * @Effect:
 * @Date: 2020/11/19
 */

@Controller
@RequestMapping("/user")
public class UserController {


    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserService userService;


    @ResponseBody
    @RequestMapping("queryUsers")
    public Map<String, Object> queryUsers(Integer page, Integer rows) {

        HashMap<String, Object> result = new HashMap<>();


        List<User> users = userService.queryAll(page, rows);   // 查询结果

        Integer count = userService.count();   // 查询总条数

        Integer totol = count % rows == 0 ? count / rows : (count / rows) + 1;   // 计算页数

        result.put("page", page);   // 当前页
        result.put("rows", users);  // 每页显示记录数
        result.put("total", totol); // 总页数
        result.put("records", count); // 总条数

        return result;
    }


    @ResponseBody
    @RequestMapping("status")
    public Map<String, String> updateStatus(User user) {

        log.info("用户数据:{}", user);

        HashMap<String, String> result = new HashMap<>();

        try {
            userService.updateStates(user);

            result.put("message", "修改成功");
            result.put("status", "200");

        } catch (Exception e) {
            e.printStackTrace();
            result.put("message", "修改失败");
            result.put("status", "201");
        }
        return result;
    }


    @RequestMapping("aliyun")
    @ResponseBody
    public void ali(String phoneNumbers) throws Exception {

        Integer code = CustomizeCode.generateValidateCode(6);
        // Integer 转String
        String templateParam = String.valueOf(code);

        String s = sendPhoneMsg(phoneNumbers, templateParam);
        System.out.println("======"+s);

    }



    @ResponseBody
    @RequestMapping("edit")
    public String edit(User user, String oper) {
        log.info("获取到的对象：{}", user);

        String result = null;

        if (oper.equals("add")) {
            result = userService.addUser(user);
        }
        if (oper.equals("edit")) {

        }

        if (oper.equals("del")) {
            userService.delUser(user);

        }

        return result;
    }


    @RequestMapping("adduploadUser")
    @ResponseBody
    public void adduploadUser(MultipartFile head, String id) {

        userService.adduploadUser(head, id);

    }







    @ResponseBody
    @RequestMapping("easyPoi")
    public Map<String, Object> easyPoi() {

        Map<String, Object> map = null;
        try {
            map = userService.easyPoi();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return map;
    }


    /**
     * 用户分布
     * @return
     */
    @ResponseBody
    @RequestMapping("querySex")

    public List<SexPO> querySex() {


        ArrayList<SexPO> sexPOS = new ArrayList<>();

        List<CityPO> cityBoy = userService.querySex("男");
        sexPOS.add(new SexPO("小男孩", cityBoy));

        List<CityPO> cityGitl = userService.querySex("女");
        sexPOS.add(new SexPO("小女孩", cityGitl));

        return sexPOS;
    }




    @RequestMapping("queryByMonth")
    @ResponseBody
    public Map<String,Object> queryByMonth () {

        HashMap<String, Object> map = new HashMap<>();

        List<Integer> list = MonthUtil.queryMonths();

        ArrayList<Integer> boy = new ArrayList<>();    // 小男孩
        ArrayList<Integer> girl = new ArrayList<>();  // 小女孩
        ArrayList<String> months = new ArrayList<>(); // 月

        String date = new SimpleDateFormat("MM").format(new Date());
        Integer month = Integer.valueOf(date);

        for (Integer integer : list) {

            boy.add(userService.queryByMonth("男", integer));
            girl.add(userService.queryByMonth("女", integer));
            months.add(integer + "月");
        }

        map.put("boy", boy);
        map.put("girl", girl);
        map.put("months", months);


        return map;
    }



}
