package cn.itcast.travel.service;

import cn.itcast.travel.domain.PageBean;

/**
 * @program: travel
 * @description:
 * @author: Chiry
 * @create: 2020-09-01 09:42
 **/
public interface RouteService {
    PageBean pageQuery(int cid,int currentPage,int pageSize);
}
