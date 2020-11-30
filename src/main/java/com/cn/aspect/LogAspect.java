package com.cn.aspect;

import com.cn.annotation.AddLog;
import com.cn.dao.LogMapper;
import com.cn.entity.Adimin;
import com.cn.entity.Log;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.UUID;

/**
 * @Author： zjr
 * @Effect:
 * @Date: 2020/11/24
 *
 * @before 前置通知
 * @after 后置通知
 * @around   环绕通知
 * @exception
 *
 *
 *
 */

// 指定是一个配置类
@Configuration
// 切面类
@Aspect
public class LogAspect {

    @Resource
    HttpServletRequest request;


    @Autowired
    LogMapper logMapper;

//     @Around("execution(* com.cn.service.Impl.*.*(..))  && !execution(* com.cn.service.Impl.*.query*(..))")
    @Around("@annotation(com.cn.annotation.AddLog)")
    public Object addLog(ProceedingJoinPoint proceedingJoinPoint) {

        // 谁     时间     操作    成功


        // 获取用户数据
        Adimin admin = (Adimin) request.getSession().getAttribute("login");


        // 获取方法名
        String methodName = proceedingJoinPoint.getSignature().getName();

        //获取方法
        MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
        Method method = signature.getMethod();

        //获取注解
        AddLog addLog = method.getAnnotation(AddLog.class);


        //获取注解对应的属性值
        String value = addLog.value();




        String message=null;
        Object result =null;
        String msg = null;
        //放行方法
        try {
            result = proceedingJoinPoint.proceed();

            String s = result.toString();

            message="success";
        } catch (Throwable throwable) {
            msg = throwable.getMessage();
            if (msg == null) {
                message = "success";
            }else {
                message = "error";
            }

        }



        Log log = new Log(
                UUID.randomUUID().toString(),
                admin.getUsername(),
                new Date(),
                methodName+" ("+value+")",
                message
                );


        logMapper.insert(log);


        System.out.println("========"+log);

        return result;

    }


}
