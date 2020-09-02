package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.ResultInfo;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.service.impl.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/*
因为该方法extends BaseServlet类，而BaseServlet类继承了HttpServlet类，这来自该注解的请求会调用该父类的service方法执行相关请求
 */

@WebServlet("/user/*")
public class UesrServlet extends BaseServlet {
    //提取公共使用的对象
    private UserService service = new UserServiceImpl();

    /**
     * @Description: 注册
     * @Param:
     * @return:
     * @Author: Chiry
     * @Date: 2020/8/30
     */
    public void regist(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //0.验证校验
        String check = request.getParameter("check");//获取提交验证码

        HttpSession session = request.getSession();
        String checkcode_server = (String) session.getAttribute("CHECKCODE_SERVER");
        session.removeAttribute("CHECKCODE_SERVER");

        if (checkcode_server == null || !checkcode_server.equals(check)) {
            ResultInfo info = new ResultInfo();
            info.setFlag(false);
            info.setErrorMsg("验证码错误！");
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(info);

            response.setContentType("application/json;charset=utf-8");//设置响应信息类型，告知浏览器解析
            response.getWriter().write(json);

            return;
        }
        //1。获取数据表单提交的数据集合

        Map<String, String[]> map = request.getParameterMap();
        //2。封装对象
        User user = new User();
        //使用apache 的beanUtils 封装表单绑定数据map对象数据到user对象

        try {
            BeanUtils.populate(user, map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        //3。调用service 完成注册
       // UserService servie = new UserServiceImpl();
        boolean flag = service.regist(user);
        ResultInfo info = new ResultInfo();

        //4。响应结果
        if (flag) {
            info.setFlag(true);
        } else {
            info.setFlag(false);
            info.setErrorMsg("注册失败！");
        }
        //将ResultInfo对象序列化成json数据
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(info);

        response.setContentType("application/json;charset=utf-8");//设置响应信息类型，告知浏览器解析
        response.getWriter().write(json);
    }

    /*
     * @Description: 登录
     * @Param: [request, response]
     * @return: void
     * @Author: Chiry
     * @Date: 2020/8/30
     */
    public void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.获取表单提交的数据map集合
        Map<String, String[]> map = request.getParameterMap();
        //2。封装数据
        User user = new User();

        try {
            BeanUtils.populate(user, map);  //这里封装的数据对象user要有与map获取到的表单数据命名对应的，这样才可以封装好数据
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        //3。调用service查询结果
      //  UserService service = new UserServiceImpl();
        User u = service.login(user);//这里需要判断用户的激活状态所以需要传入一个对象
        ResultInfo info = new ResultInfo();
        //4。根据查询结果响应对应的结果响应
        if (u == null) {
            info.setFlag(false);
            info.setErrorMsg("用户名或密码错误！");
        }
        if (u != null && "Y".equals(u.getStatus())) {
            request.getSession().setAttribute("user", u);
            info.setFlag(true);
        } else if (u != null && !"Y".equals(u.getStatus())) {
            info.setFlag(false);
            info.setErrorMsg("您尚未激活，请激活！");
        }

        //5。把信息对象响应给浏览器  (对象使用json数据格式)
        ObjectMapper mapper = new ObjectMapper();
        String s = mapper.writeValueAsString(info);
        response.setContentType("application/json;charset=utf-8");//这里设置响应memi数据类型
        response.getWriter().write(s);
    }

    /**
     * @Description: 查找用户
     * @Param: [request, response]
     * @return: void
     * @Author: Chiry
     * @Date: 2020/8/30
     */
    public void findOne(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Object user = request.getSession().getAttribute("user");
        ObjectMapper mapper = new ObjectMapper();

        String json = mapper.writeValueAsString(user);
        response.getWriter().write(json);
    }

    /**
     * @Description: 用户退出
     * @Param: [request, response]
     * @return: void
     * @Author: Chiry
     * @Date: 2020/8/30
     */
    public void exit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getSession().invalidate();  //销毁session，即销毁用户的当前登录信息
//        request.getContextPath()  虚拟路径名(或者定义为项目名)
        response.sendRedirect(request.getContextPath() + "/index.html");

    }

    /**
     * @Description: 激活用户
     * @Param: [request, response]
     * @return: void
     * @Author: Chiry
     * @Date: 2020/8/30
     */
    public void active(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String code = request.getParameter("code");
        if (code != null) {
            //调用service 完成激活
         //   UserService service = new UserServiceImpl();
            boolean flag = service.active(code);

            String msg = null;
            if (flag) {
                //激活成功
                msg = "激活成功！请<a href='login.html'>登入</a>";
            } else {
                //激活失败
                msg = "激活失败！请联系管理员。";
            }

            //设置响应格式，信心
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().write(msg);
        }
    }
}
