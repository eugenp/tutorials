package service.impl;

import bean.Category;
import bean.Pagination;
import dao.CategoryDao;
import dao.DaoFactory;
import service.CategoryService;

import java.util.List;
import java.util.Map;

public class CategoryServiceImpl implements CategoryService {

    //DAO的注入
    private CategoryDao categoryDao;

    //初始化DAO
    public CategoryServiceImpl() {

        categoryDao = DaoFactory.getInstance().getCategoryDao();
    }

    //下面几个方法是CategoryService 的实现方法
    public boolean addCategory(Category category) {
        //添加分类,save为方法
        categoryDao.save(category);
        return true;
    }

    public List<Category> listCategory() {

        return categoryDao.findAll();
    }

    //修改
    public boolean editCategory(Category category) {
        categoryDao.update(category);
        return true;
    }

    public boolean deleteCategory(Category category) {
        categoryDao.delete(category);
        return true;
    }

    public Category findCategoryById(Integer id) {

        return categoryDao.findById(id);
    }

    //分页
    public Pagination<Category> getPage(Map<String, Object> paramMap,
                                        int pageSize, int pageNumber) {
        int maxElements = categoryDao.getCount();
        return categoryDao.getPagination(paramMap, pageSize, pageNumber, maxElements);
    }

}
