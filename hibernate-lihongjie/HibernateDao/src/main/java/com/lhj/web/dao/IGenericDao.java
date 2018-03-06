package com.lhj.web.dao;

import java.io.Serializable;
import java.util.List;

import com.lhj.web.vo.PageBean;


public interface IGenericDao<T extends Serializable, ID extends Serializable> {


    T create(final T entity);

    T findById(final ID id);

    void delete(final ID id);

    void update(final T entity);

    List<T> findAll();

    List<T> findByHQL(final String strHQL, final Object[] params);

    PageBean findByPage(final String strHQL,
                        final int currentPage, final int pageSize, final Object[] params);
}
