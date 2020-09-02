package com.itcast.travel.test;

import cn.itcast.travel.domain.Category;
import cn.itcast.travel.service.impl.CategoryServiceImpl;

import java.util.List;

/**
 * @program: travel
 * @description: test category业务方法
 * @author: Chiry
 * @create: 2020-08-31 10:15
 **/
public class TestCategoryService {
    public static void main(String[] args) {
        CategoryServiceImpl categoryService = new CategoryServiceImpl();
        List<Category> all = categoryService.findAll();
        System.out.println(all);

    }
}
