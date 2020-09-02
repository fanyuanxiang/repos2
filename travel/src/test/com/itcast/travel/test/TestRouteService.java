package com.itcast.travel.test;

import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.service.RouteService;
import cn.itcast.travel.service.impl.RouteServiceImpl;

import java.util.List;

/**
 * @program: travel
 * @description: 测试routeService
 * @author: Chiry
 * @create: 2020-09-01 10:57
 **/
public class TestRouteService {


    public static void main(String[] args) {
    RouteService routeService=new RouteServiceImpl();
        PageBean pageBean = routeService.pageQuery(5, 1, 5);
        List list = pageBean.getList();
        System.out.println(list);
        System.out.println(pageBean.getTotalCount());
        System.out.println(pageBean.getTotalpage());


    }

}
