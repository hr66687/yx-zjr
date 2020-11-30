package com.cn.service.Impl;

import com.cn.dao.AdiminMapper;
import com.cn.entity.Adimin;
import com.cn.service.AdiminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author： zjr
 * @Effect:
 * @Date: 2020/11/18
 */

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class AdiminServiceImpl implements AdiminService {

    @Autowired
    private AdiminMapper adiminMapper;


    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Adimin queryByName(String username) {

        return adiminMapper.queryByName(username);
    }
}
