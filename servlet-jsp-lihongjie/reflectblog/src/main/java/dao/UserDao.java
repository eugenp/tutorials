package dao;


import bean.User;

/**
 * UserDao专门来操作User这张表，操作User这个对象来操作这张表，使用接口连接层与层之间。
 * 数据访问层：与业务逻辑无关，只是对数据表进行基本的操作：增删改查
 * 层与层之间使用的是对象传递数据。
 */
public interface UserDao extends GenerateDao<User> {
//	public void save(User user);//增加
//	public void delete(User user);//删除
//	public void update(User user);//修改
//	public List<User> findAll();//查询，返回一张表的所有数据，一行对应一个对象，很多对象就使用List接口。
//	public User findById(Integer id);//通过id找对象的方法

    public User findByNameAndPwd(String username, String password);
}
