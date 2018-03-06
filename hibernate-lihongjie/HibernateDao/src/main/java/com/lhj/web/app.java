package com.lhj.web;

import com.lhj.web.dao.IDeptDao;
import com.lhj.web.dao.impl.DeptDaoImpl;
import com.lhj.web.po.Dept;

/**
 * Created by lihongjie on 12/25/16.
 */
public class app {

    public static void main(String[] args) {

        IDeptDao deptDao = new DeptDaoImpl();
        Dept dept = new Dept();
        dept.setDname("test");
        dept.setManager("h");
        dept.setPeoplenumber(1);
        dept.setPhonenumber("123456");
        deptDao.create(dept);
    }
}
