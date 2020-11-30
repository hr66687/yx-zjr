package com.cn.service.Impl;

import com.alibaba.druid.util.StringUtils;
import com.cn.annotation.AddCache;
import com.cn.annotation.DelCache;
import com.cn.dao.CategoryMapper;
import com.cn.entity.Category;
import com.cn.entity.CategoryExample;
import com.cn.po.CategoryPO;
import com.cn.service.CategoryService;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

/**
 * @Author： zjr
 * @Effect:
 * @Date: 2020/11/20
 */

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class CategoryServiceImpl implements CategoryService {


    private static final Logger log = LoggerFactory.getLogger(CategoryServiceImpl.class);
    @Autowired
    private CategoryMapper categoryMapper;


    /**
     * 一级类别总条数
     * @return
     */
    @AddCache
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Integer countOne() {
        // 条件对象
        CategoryExample example = new CategoryExample();
        // 标准格式
        example.createCriteria().andLevelsEqualTo(1);

        return categoryMapper.selectCountByExample(example);
    }

    /**
     * 一级类别查询记录
     * @param page
     * @param rows
     * @return
     */
    @AddCache
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<Category> queryOne(Integer page, Integer rows) {
       // 分页参数
        Integer pageNum = (page - 1) * rows;
        // 条件对象
        CategoryExample example = new CategoryExample();
        // 标准格式
        example.createCriteria().andLevelsEqualTo(1);
        // 分页查询
        RowBounds rowBounds = new RowBounds(pageNum, rows);
        return categoryMapper.selectByExampleAndRowBounds(example, rowBounds);
    }


    /**
     * 二级类别总条数
     * @param parentId
     * @return
     */
    @AddCache
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Integer counttwo(String parentId) {
        CategoryExample example = new CategoryExample();

        example.createCriteria().andLevelsEqualTo(2).andParentIdEqualTo(parentId);
        //log.info("二级类别：{}", example);
        return categoryMapper.selectCountByExample(example);
    }


    /***
     * 二级类别查询记录
     * @param page
     * @param rwos
     * @param parentId
     * @return
     */
    @AddCache
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<Category> querytwo(Integer page, Integer rwos, String parentId) {

        Integer pageNum = (page - 1) * rwos;

        CategoryExample example = new CategoryExample();

        example.createCriteria().andLevelsEqualTo(2).andParentIdEqualTo(parentId);



        RowBounds rowBounds = new RowBounds(pageNum, rwos);

        return categoryMapper.selectByExampleAndRowBounds(example, rowBounds);
    }



    @DelCache
    @Override
    public void exitCategory(Category category, String oper) {
        //添加
        if (StringUtils.equals("add", oper)) {
            category.setId(UUID.randomUUID().toString());
            category.setLevels(1);
            if (category.getParentId()!=null) category.setLevels(2);
            categoryMapper.insertSelective(category);
        }
        //修改
        if (StringUtils.equals("edit", oper)) categoryMapper.updateByPrimaryKeySelective(category);
        //删除
        if (StringUtils.equals("del", oper)) {

            CategoryExample example = new CategoryExample();
            example.createCriteria().andParentIdEqualTo(category.getId());
            if (categoryMapper.selectByExample(example).size()==0) categoryMapper.deleteByPrimaryKey(category.getId());
            else throw new RuntimeException("删除失败");
        }
    }


    /**
     * 前天分类展示
     *
     * @return
     */
    @Override
    public List<CategoryPO> queryAllCategory() {

        return categoryMapper.queryAllCategory();
    }




    /*@Override
    public HashMap<String, Object> queryPage(Integer page, Integer rows) {

        HashMap<String, Object> map = new HashMap<>();

        // 设置当前页
        map.put("page", page);

        // 设置条件
        CategoryExample example = new CategoryExample();
        // 设置分页参数
        RowBounds rowBounds = new RowBounds((page - 1) * rows, rows);

        //分页查询数据
        List<Category> categories = categoryMapper.selectByExampleAndRowBounds(example, rowBounds);
        map.put("rows", categories);



        //设置总条数
        int count = categoryMapper.selectCountByExample(example);
        map.put("records", count);



        // 设置总页数
        Integer total = count % rows == 0 ? count / rows : count / rows + 1;
        map.put("total", total);
        return map;
    }*/
}
