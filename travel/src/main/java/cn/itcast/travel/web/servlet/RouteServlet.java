package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.service.RouteService;
import cn.itcast.travel.service.impl.RouteServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/route/*")
public class RouteServlet extends BaseServlet {
    /**
    * @Description:  分页查询
    * @Param: [request, response] 
    * @return: void 
    * @Author: Chiry
    * @Date: 2020/9/1 
    */ 
    private RouteService routeServiceImpl=new RouteServiceImpl();
    public  void pageQuery(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.接收参数
        String currentPageStr=request.getParameter("currentPage");
        String pageSizeStr=request.getParameter("pageSize");
        String cidStr=request.getParameter("cid");
        int cid = 0;
        //类别id 
        // 2.处理参数 
        if(cidStr != null && cidStr.length() > 0) {
            cid = Integer.parseInt(cidStr);
        }
        int currentPage = 0;
        //当前页码，如果不传递，则默认为第一页 
        if(currentPageStr != null && currentPageStr.length() > 0){
            currentPage = Integer.parseInt(currentPageStr);
        }else{
            currentPage = 1;
        }
        int pageSize = 0;//每页显示条数，如果不传递，默认每页显示5条记录 
        if(pageSizeStr != null && pageSizeStr.length() > 0){
            pageSize = Integer.parseInt(pageSizeStr);
        }else{
            pageSize = 5;
        }

        //查询pageBean对象
        PageBean pb=routeServiceImpl.pageQuery(cid,currentPage,pageSize);
        writeValue(pb,response);


    }

}
