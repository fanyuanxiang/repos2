package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.UserDao;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

public class UserDaoImpl implements UserDao {
    //使用jdbcTemplate去封装查询数据结果
    private JdbcTemplate jdbcTemplate = new JdbcTemplate(JDBCUtils.getDataSource());

    @Override
    public User findByUsername(String name) {
        User user = null;

        //为service层的regist 判定条件匹配赋值异常null
        //如果为查出结果jdbcTemplate会出现异常，我们把异常抓住，不处理，这样user 赋值就仍然是null了。
        try {
            //1。定义sql
            String sql = "select * from tab_user where username=?";
            //2。执行sql  封装结果集,prepared 防注入查询
            user = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class),name);
        } catch (DataAccessException e) {

        }
        return user;
    }


    /*
    保存用户到数据库
    @Param user
    记得保存验证激活的UUid 、status
     */
    @Override
    public void save(User user) {
        String sql = "insert into tab_user(username,password,name,birthday,sex ,telephone ,email,status,code) values(?,?,?,?,?,?,?,?,?)";

        jdbcTemplate.update(sql, user.getUsername(),
                user.getPassword(),
                user.getName(),
                user.getBirthday(),
                user.getSex(),
                user.getTelephone(),
                user.getEmail(),
                user.getStatus(),
                user.getCode()
        );


    }
    /*
    激活用户，通过uuid去查找数据，确保用户唯一
     */

    @Override
    public User findByCode(String code) {
        User user=null;

        try {
            String sql="select * from tab_user where code=?";
            user = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), code);

        } catch (DataAccessException e) {
        }
        //返回user给service判断转发操作。（user会出现null 、有数据 两种情况）
        return user;
    }

    @Override
    public void updateStatus(User user) {
        String sql="update tab_user set status='Y' where uid =?";
        jdbcTemplate.update(sql,user.getUid());

    }
    @Override
    public User findUserByUsernameAndPassword(User user) {
        User u=null;

        try {
            String sql="select * from tab_user where username=? and password=?";
            /*
             com.alibaba.druid.support.logging.JakartaCommonsLoggingImpl error
             严重: testWhileIdle is true, validationQuery not set

             queryForObject 没有传入？ 号的占位参数，就无法执行查询操作
             */

            u = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class),user.getUsername(),user.getPassword());
        } catch (DataAccessException e) {
                //如果没有查到结果就生吞异常，则执行后须代码return u == null；
        }
        return u;
    }

}
