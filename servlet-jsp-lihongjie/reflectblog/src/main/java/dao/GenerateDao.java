package dao;
/**
 * 通用的泛型接口
 */
import java.util.List;
import java.util.Map;

import bean.Pagination;

public interface GenerateDao<T> { //T参数不定的
	public void save(T entity); //T代表所有的	Bean对象
	public void update(T entity);
	public void delete(T entity);
	public T findById(Object id);//通过id查询
	public List<T> findAll();//
	
	//分页
	public Pagination<T> getPagination(Map<String, Object> paramMap,
                                       int pageSize, int pageNumber, int maxElements);
	
	//得到记录总数
	public int getCount();
}
