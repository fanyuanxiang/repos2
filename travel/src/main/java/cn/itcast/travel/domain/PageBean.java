package cn.itcast.travel.domain;

import java.util.List;

/**
 * @program: travel
 * @description: route页面展示的数据封装对象
 * @author: Chiry
 * @create: 2020-09-01 08:30
 **/
public class PageBean<T> {
    private int totalCount ;
    private int totalpage ;
    private int currentPage ;
    private int pageSize;

    private List<T> list;

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getTotalpage() {
        return totalpage;
    }

    public void setTotalpage(int totalpage) {
        this.totalpage = totalpage;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public List getList() {
        return list;
    }

    public void setList(List list) {
        this.list = list;
    }
}
