package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.UserDao;
import cn.itcast.travel.dao.impl.UserDaoImpl;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.util.MailUtils;
import cn.itcast.travel.util.UuidUtil;

public class UserServiceImpl implements UserService {


//service 调用dao层用执行业务逻辑操作，因此在创建一个全局变量userDao 供serviceImpl的方法使用
private UserDao userDao=new UserDaoImpl();

    @Override
    public boolean regist(User user) {
        //1.在数据库中查询user
        User u = userDao.findByUsername(user.getUsername());

        //判断u是否查询出结果  (jdbcTemplate未查处结果会报异常)
        if (u!= null){
            //查询出结果，用户存在，注册失败
            return  false;
        }
        //2。用户不存在，保存用户信息
        //2.1设置激活码，唯一字符串
        user.setCode(UuidUtil.getUuid());
        //2.2设置状态码  (N 未激活)
        user.setStatus("N");
        userDao.save(user);

        String content ="<a href='http://localhost/travel/activeUserServlet?code="+user.getCode()+"'> 点击激活【黑马旅游网】</a>";
        MailUtils.sendMail(user.getEmail(),content,"激活邮件");
        return true;

    }

    @Override
    public boolean active(String code) {
        UserDao userDao = new UserDaoImpl();
        User user=userDao.findByCode(code);

        if (user!=null){
         user.setStatus("Y");
         userDao.updateStatus(user);
         return true;

        }
        return false;
    }

    @Override
    public User login(User user) {

        return userDao.findUserByUsernameAndPassword(user);
    }
}
