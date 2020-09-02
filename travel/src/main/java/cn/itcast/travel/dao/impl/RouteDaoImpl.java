package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.RouteDao;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

/**
 * @program: travel
 * @description: 查询数据库的线路表tab_route
 * @author: Chiry
 * @create: 2020-09-01 10:11
 **/
public class RouteDaoImpl implements RouteDao{
private JdbcTemplate jdbcTemplate =new JdbcTemplate(JDBCUtils.getDataSource());

//查询对应项目的总线路条数
    @Override
    public int findTotalCount(int cid) {
        String sql="select count(*) from tab_route  where cid=?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, cid);
        return count;
    }

    @Override
    public List<Route> findByPage(int cid, int start, int pageSize) {
        String sql ="select * from tab_route where cid=? limit ?,?";
        List<Route> routes = jdbcTemplate.query(sql, new BeanPropertyRowMapper<Route>(Route.class), cid, start, pageSize);
        return routes;
    }
}
