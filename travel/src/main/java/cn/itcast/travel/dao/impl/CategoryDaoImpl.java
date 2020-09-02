package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.CategoryDao;
import cn.itcast.travel.domain.Category;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import java.util.List;

/**
 * @program: travel
 * @description:查找category
 * @author: Chiry
 * @create: 2020-08-30 20:26
 **/
public class CategoryDaoImpl implements CategoryDao{

    private JdbcTemplate jdbcTemplate =new JdbcTemplate(JDBCUtils.getDataSource());


    @Override
    public List<Category> findAll() {
        String sql = "select * from tab_category ";
        return jdbcTemplate.query(sql,new BeanPropertyRowMapper<Category>(Category.class));
    }

}
