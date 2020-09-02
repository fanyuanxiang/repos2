package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.CategoryDao;
import cn.itcast.travel.dao.impl.CategoryDaoImpl;
import cn.itcast.travel.domain.Category;
import cn.itcast.travel.service.CategoryService;
import cn.itcast.travel.util.JedisUtil;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 查询用户所有业务列表
 * @program: travel
 * @description:
 * @author: Chiry
 * @create: 2020-08-30 20:19
 **/
public class CategoryServiceImpl implements CategoryService {
    CategoryDao categoryDao= new CategoryDaoImpl();

    @Override
    public List<Category> findAll() {
        //1.从redis中查询数据
        //缓存数据的优化，使用redis缓存不会频繁变换的数据，避免频繁访问数据库给数据库压力过大
        //1.创建jedis 对象连接redis数据库
        List<Category> lists=null;
        Jedis jedis = JedisUtil.getJedis();
        List<String> categorys = jedis.lrange("category", 0, -1);

        if(categorys==null||categorys.size()==0){
            System.out.println("从数据库中查询数据");
            lists = categoryDao.findAll();
            Collections.sort(lists, new Comparator<Category>() {
                @Override
                public int compare(Category o1, Category o2) {
                    return o1.getCid()-o2.getCid();
                }
            });
            for (Category c :
                    lists) {
                jedis.rpush("category",c.getCname());
            }

        }else {
            System.out.println("从redis中查询数据");
                lists=new ArrayList<Category>();
                int i=0;
            for (String name :
                    categorys) {
                Category category = new Category();
                category.setCname(name);
                category.setCid(++i);
                lists.add(category);
            }
        }
        return lists;
    }
}
