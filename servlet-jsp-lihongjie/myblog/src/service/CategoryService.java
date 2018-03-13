package service;

import java.util.List;
import java.util.Map;

import bean.Category;
import bean.Pagination;

/**
 * 分类管理模块的Service
 * 功能：(对应实现的方法)
 * 添加分类
 * 显示分类列表
 * 修改分类
 * 删除分类
 * @author Administrator
 *
 */
public interface CategoryService {
	//添加分类
	public boolean addCategory(Category category);
	//显示分类列表
	public List<Category> listCategory();
	//修改分类
	public boolean editCategory(Category category);
	//删除分类
	public boolean deleteCategory (Category category);
	//根据id得到分类
	public Category findCategoryById(Integer id);
	
	public Pagination<Category> getPage(Map<String, Object> paramMap,
			
				int pageSize,int pageNumber);
}
