package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.ResultInfo;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.service.impl.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.jdbc.core.RowMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

@WebServlet("/loginServlet")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.获取表单提交的数据map集合
        Map<String, String[]> map = request.getParameterMap();
        //2。封装数据
        User user = new User();

        try {
            BeanUtils.populate(user,map);  //这里封装的数据对象user要有与map获取到的表单数据命名对应的，这样才可以封装好数据
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        //3。调用service查询结果
        UserService service = new UserServiceImpl();
        User u=service.login(user);//这里需要判断用户的激活状态所以需要传入一个对象
        ResultInfo info =new ResultInfo();
        //4。根据查询结果响应对应的结果响应
        if(u==null){
            info.setFlag(false);
            info.setErrorMsg("用户名或密码错误！");
        }
        if(u!=null&&"Y".equals(u.getStatus())){
            request.getSession().setAttribute("user",u);
            info.setFlag(true);
        }
        else if(u!=null&&!"Y".equals(u.getStatus())){
            info.setFlag(false);
            info.setErrorMsg("您尚未激活，请激活！");
        }

        //5。把信息对象响应给浏览器  (对象使用json数据格式)
        ObjectMapper mapper = new ObjectMapper();
        String s = mapper.writeValueAsString(info);
        response.setContentType("application/json;charset=utf-8");//这里设置响应mime数据类型
        response.getWriter().write(s);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
