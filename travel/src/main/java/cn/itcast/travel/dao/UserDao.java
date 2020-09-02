package cn.itcast.travel.dao;

import cn.itcast.travel.domain.User;

public interface UserDao {
    /*
   通过username查找用户
   @return: User
  */
    public User findByUsername(String name);

    /*
     *保存用户
     * */
    public void save(User user);


    User findByCode(String code);

    void updateStatus(User user);

    User findUserByUsernameAndPassword(User u);
}
