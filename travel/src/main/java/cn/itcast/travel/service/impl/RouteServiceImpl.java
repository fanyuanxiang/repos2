package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.RouteDao;
import cn.itcast.travel.dao.impl.RouteDaoImpl;
import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.service.RouteService;

import java.util.List;

/**
 * @program: travel
 * @description:
 * @author: Chiry
 * @create: 2020-09-01 09:45
 **/
public class RouteServiceImpl implements RouteService {
    private RouteDao routeDao=new RouteDaoImpl();
    @Override
    public PageBean pageQuery(int cid, int currentPage, int pageSize) {
        PageBean<Route> pb=new PageBean<>();
        pb.setCurrentPage(currentPage);
        pb.setPageSize(pageSize);
        int totalCount = routeDao.findTotalCount(cid);
        pb.setTotalCount(totalCount);
        int start= (currentPage-1)*pageSize;
        List<Route> list=routeDao.findByPage(cid,start,pageSize);
        //总页数
        int totalPage=totalCount%pageSize==0? totalCount/pageSize:totalCount/pageSize+1;
        pb.setTotalpage(totalPage);

        pb.setList(list);
        return pb;
    }
}
