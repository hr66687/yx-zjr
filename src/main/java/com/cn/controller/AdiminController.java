package com.cn.controller;

import com.cn.entity.Adimin;
import com.cn.service.AdiminService;
import com.cn.util.ImageCodeUtil;
import com.sun.org.apache.bcel.internal.classfile.Code;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author： zjr
 * @Effect:
 * @Date: 2020/11/18
 */

@Controller
@RequestMapping("/admin")
public class AdiminController {


    @Autowired
    private AdiminService adiminService;

    private static final Logger log = LoggerFactory.getLogger(AdiminController.class);



    @RequestMapping("login")
    @ResponseBody
    public Map<String,String> login(String username, String password, HttpSession session, String code) {
        Adimin login = adiminService.queryByName(username);

        String getcode = (String) session.getAttribute("getcode");

        HashMap<String, String> map = new HashMap<>();

        try {
            if (login == null) throw new RuntimeException("用户名有误");
            if (!login.getPassword().equals(password)) throw new RuntimeException("密码有误");
            if (!getcode.equals(code)) throw new RuntimeException("验证码输入有误");
            session.setAttribute("login", login);
            map.put("message","true");
            return map;
        } catch (RuntimeException e) {
            String message = e.getMessage();
            map.put("message", message);

        }

        return map;

    }




    @RequestMapping("exit")
    public String exit(HttpSession session) {
        session.invalidate();
        return "redirect:/login/login.jsp";
    }


   @RequestMapping("getcode")
    public void getcode(HttpSession session, HttpServletResponse response) throws IOException {
        // 获取验证码随机数
        String code = ImageCodeUtil.getSecurityCode();

        // 存随机数
       session.setAttribute("getcode", code);
       System.out.println("验证码："+code);
        // 生成图片
       BufferedImage image = ImageCodeUtil.createImage(code);
       // 获取响应输出流
       ServletOutputStream outputStream = response.getOutputStream();
       // 响应到页面
       ImageIO.write(image,"png",outputStream);
   }


}
