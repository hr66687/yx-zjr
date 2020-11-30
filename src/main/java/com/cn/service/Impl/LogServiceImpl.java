package com.cn.service.Impl;

import com.cn.dao.LogMapper;
import com.cn.entity.*;
import com.cn.service.LogService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

/**
 * @Author： zjr
 * @Effect:
 * @Date: 2020/11/24
 */

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class LogServiceImpl implements LogService {


    @Autowired
    private LogMapper logMapper;


    @Override
    public HashMap<String, Object> queryAlllog(Integer page, Integer rows) {
        HashMap<String, Object> map = new HashMap<>();

        map.put("page", page);

        // 条件对象
        LogExample example = new LogExample();
        // 分页参数
        RowBounds rowBounds = new RowBounds((page - 1) * rows, rows);
        // 查询分页数据
        List<Log> logs = logMapper.selectByExampleAndRowBounds(example, rowBounds);

        map.put("rows", logs);


        //查询总条数
        int count = logMapper.selectCountByExample(example);

        map.put("records", count);


        // 设置总页数

        Integer total = count % rows == 0 ? count / rows : count / rows + 1;

        map.put("total", total);

        return map;

    }
}
