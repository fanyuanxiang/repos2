package cn.itcast.travel.web.servlet;


import cn.itcast.travel.domain.Category;
import cn.itcast.travel.service.CategoryService;
import cn.itcast.travel.service.impl.CategoryServiceImpl;
import cn.itcast.travel.util.JedisUtil;
import redis.clients.jedis.Jedis;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@WebServlet("/category/*")
public class CategoryServlet extends BaseServlet {
    private CategoryService categoryService=new CategoryServiceImpl();

    protected void findAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //1.获取dao的数据
        List<Category> lists = categoryService.findAll();
        Collections.sort(lists, new Comparator<Category>() {
            @Override
            public int compare(Category o1, Category o2) {
                return o1.getCid()-o2.getCid();
            }
        });

        System.out.println(lists);
        //2。封装数据成json数据格式
//        ObjectMapper mapper = new ObjectMapper();
//        response.setContentType("application/json;charset=utf-8");
//        mapper.writeValue(response.getOutputStream(),lists);
        writeValue(lists,response);
        System.out.println("category add 方法");
    }

    protected void del(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("catogory del 方法");
    }
}
