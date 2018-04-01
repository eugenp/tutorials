package dao.impl;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import bean.Pagination;


import util.DBManager;


import annotation.Bean;
import annotation.Column;
import annotation.Id;

import dao.GenerateDao;

public class GenerateDaoImpl<T> implements GenerateDao<T> {

	private final Class<T> clazz;
	
	//ͨ通过反射机制，在运行时得到具体的实现类
	public GenerateDaoImpl(){
		clazz = (Class<T>)((ParameterizedType)getClass()
				.getGenericSuperclass())
				.getActualTypeArguments()[0];
		//也可以这样
//		ParameterizedType pt = (ParameterizedType) this.getClass().getGenericSuperclass();
//		clazz =(Class) pt.getActualTypeArguments()[0];
	}
	
	public void save(T entity) {
		
		Connection conn = null;
		PreparedStatement ps =null;
		conn = DBManager.getConn();
		//构建sql语句
		String tableName = getTableName(clazz);
		StringBuilder placeHolders = new StringBuilder();
		StringBuilder fieldNames = new StringBuilder();
		List<Object> values = new ArrayList<Object>();
		//获得属性名
		Field[] fields = clazz.getDeclaredFields();
		
		for(Field field:fields){
			try {
				PropertyDescriptor pd = new PropertyDescriptor(field.getName(), clazz);
				if(field.isAnnotationPresent(Id.class)){
				/*fieldNames.append(field.getAnnotation(Id.class).value());
				 * values.add(pd.getReadMethod().invoke(entity));*/
				}else if(field.isAnnotationPresent(Column.class)){
					fieldNames.append(field.getAnnotation(Column.class).value()).append(",");
					values.add(pd.getReadMethod().invoke(entity));
					placeHolders.append("?").append(",");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		//删除最后一个逗号
		fieldNames.deleteCharAt(fieldNames.length()-1);
		placeHolders.deleteCharAt(placeHolders.length()-1);
		
		//拼接sql
		StringBuilder sql = new StringBuilder();
		sql.append("insert into ").append(tableName).append("(")
		.append(fieldNames.toString()).append(") values (").append(placeHolders)
		.append(")");
		
		try {
			ps = conn.prepareStatement(sql.toString());
			//设置占位符的值
			setParameter(values, ps);
			ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DBManager.closeConn(conn);
		}
		
	}

	public void delete(T entity) {
		String tableName = getTableName(clazz);
		Field[] fields = clazz.getDeclaredFields();
		String idFieldName = "";
		Object idValue = null;
		for (Field field:fields) {
			if (field.isAnnotationPresent(Id.class)) {
				PropertyDescriptor pd;
				try {
					pd = new PropertyDescriptor(field.getName(), clazz);
					if (field.isAnnotationPresent(Id.class)) {
						idFieldName = field.getAnnotation(Id.class).value();
						idValue = pd.getReadMethod().invoke(entity);
						break;
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		//拼接sql
		Connection conn = null;
		String sql = "delete from " + tableName + " where " + idFieldName+"=?";
		PreparedStatement ps;
		
		try {
			conn = DBManager.getConn();
			ps = conn.prepareStatement(sql);
			ps.setObject(1, idValue);
			ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DBManager.closeConn(conn);
		}
	}
	
	/**
	 * @param clazz
	 * @return
	 */
	//得到表名
	private String getTableName(Class clazz){
		Bean bean = (Bean) clazz.getAnnotation(Bean.class);
		return bean.value();
	}
	
	/**
	 * 设置sql语句中占位符的值
	 * @throws SQLException 
	 */
	private void setParameter(List<Object> values,PreparedStatement ps) throws SQLException{
		//判断values是什么类型 int double之类的
		for(int i=1;i<=values.size();i++){
			Object fieldValue = values.get(i-1);
			if(fieldValue !=null){
				Class clazzValue = fieldValue.getClass();
				if(clazzValue == String.class){
					//是？？
					ps.setString(i, (String)fieldValue);
				}
				
				else if(clazzValue == int.class || clazzValue == Integer.class){
					ps.setInt(i, (Integer)fieldValue);
				}
				
				else if(clazzValue == boolean.class || clazzValue == Boolean.class){
					ps.setBoolean(i, (Boolean)fieldValue);
				}else if(clazzValue == byte.class || clazzValue== Byte.class){
					ps.setByte(i, (Byte)fieldValue);
				}else if(clazzValue == char.class || clazzValue== Character.class){
					ps.setObject(i, fieldValue);
				}else if(clazzValue == Date.class){
					//得到时间
					ps.setTimestamp(i, new Timestamp(((Date) fieldValue).getTime()));
					//ps.setTimestamp(i, new Timestamp(((Date) fieldValue).compareTo(null)));
				}else if(clazzValue.isArray()){
					Object[] arrayValue =(Object[])fieldValue;
					StringBuffer sb = new StringBuffer();
					for(int j=0;j<arrayValue.length;j++){
						sb.append(arrayValue[j]).append("丶");
					}
					ps.setString(i, sb.deleteCharAt(sb.length()-1).toString());
				}
			}
		}
	}

	public void update(T entity) {
		//构造SQL语句，通过类的信息得到该类所代表的表和表中的属性--反射表
		
				String tableName = getTableName(clazz);
				//字段
				List<Object> fieldNames = new ArrayList<Object>();
				//字段值
				List<Object> fieldValues = new ArrayList<Object>();
				//占位符
				List<String> placeholders = new ArrayList<String>();
				
				//得到该类的属性
				Field[] fields = clazz.getDeclaredFields();
				
				//id列
				String idFieldName = "";
				Object idFieldValue = "";
				
				for(Field field:fields){
					try {
						//得到该属性相关的所有信息，属性名，属性值，属性的注解
						PropertyDescriptor pd = new PropertyDescriptor(field.getName(), clazz);
						if(field.isAnnotationPresent(Id.class)){
							idFieldName = field.getAnnotation(Id.class).value();
							idFieldValue = pd.getReadMethod().invoke(entity);//entity
						}else if(field.isAnnotationPresent(Column.class)){
							fieldNames.add(field.getAnnotation(Column.class).value()); 
							fieldValues.add(pd.getReadMethod().invoke(entity));
							placeholders.add("?");//
						}
					} catch (IntrospectionException e) {
						e.printStackTrace();
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						e.printStackTrace();
					}
				}
					
				//ID作为更新条件，放在集合的最后的一个元素
					fieldNames.add(idFieldName);
					fieldValues.add(idFieldValue);
					placeholders.add("?");
					
					System.out.println("admin");
					
					
					//拼接SQL语句
					StringBuilder sql = new StringBuilder("");
					sql.append(" update ").append(tableName).append(" set ");
					int index = fieldNames.size() - 1;
					for (int i = 0; i < index; i++) {
						sql.append(fieldNames.get(i)).append(" = ").append(placeholders.get(i)).append(",");
					}
					sql.deleteCharAt(sql.length()-1).append(" where ").append(fieldNames.get(index)).append("=").append("?");
					
					Connection conn = null;
					PreparedStatement ps = null;
					
					conn = DBManager.getConn();
					try {
						ps = conn.prepareStatement(sql.toString());
						setParameter(fieldValues, ps);
						ps.execute();
					} catch (SQLException e) {
						e.printStackTrace();
					}finally{
						DBManager.closeConn(conn);
					}
				}


	public T findById(Object id) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		T obj = null;
		
		conn = DBManager.getConn();
		
		//构建SQL语句
		String tableName = getTableName(clazz);
		String sql = "select * from " + tableName;
		
		
		try {
			Field[] fields = clazz.getDeclaredFields();
			String idColumn = "";
			for(Field field:fields){
				if(field.isAnnotationPresent(Id.class)){
					idColumn = field.getAnnotation(Id.class).value();
				}
			}
			
			sql = sql +" where "+idColumn + "='"+id+"'";
			
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
//			list=new ArrayList<T>();
//			Field[] fields=clazz.getDeclaredFields();װ
			while(rs.next()){
			    obj = clazz.newInstance();
				initObject(obj, fields, rs);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBManager.closeConn(conn);
		}
		
		return obj;
		
	}
/**
 * @param t
 * @param fields
 * @param rs
 * @throws SQLException 
 * @throws IntrospectionException 
 * @throws InvocationTargetException 
 * @throws IllegalAccessException 
 * @throws IllegalArgumentException 
 */
	//根据结果集初始化对象
	private void initObject(T t,Field[] fields,ResultSet rs) throws SQLException, IntrospectionException, IllegalArgumentException, IllegalAccessException, InvocationTargetException{
		for(Field field:fields){
			//属性名
			String propertyName = field.getName();
			//字段名
			String columnName = "";
			if(field.isAnnotationPresent(Id.class)){
				columnName = field.getAnnotation(Id.class).value();
			}else if(field.isAnnotationPresent(Column.class)){
				columnName = field.getAnnotation(Column.class).value();
			}
			
			Object paramVal = null;
			Class<?> clazzField = field.getType();
			if(clazzField == String.class){
				paramVal = rs.getString(columnName);
			}else if(clazzField == short.class || clazzField == short.class){
				paramVal = rs.getShort(columnName);
			}else if(clazzField == int.class || clazzField == Integer.class){
				paramVal = rs.getInt(columnName);
			}else if(clazzField == long.class ||  clazzField ==Long.class){
				paramVal = rs.getLong(columnName);
			}else if(clazzField == float.class || clazzField == Float.class){
				paramVal = rs.getFloat(columnName);
			}else if(clazzField == double.class || clazzField == Double.class){
				paramVal = rs.getDouble(columnName);
			}else if(clazzField == boolean.class || clazzField == Boolean.class){
				paramVal = rs.getBoolean(columnName);
			}else if(clazzField == byte.class || clazzField == Byte.class){
				paramVal = rs.getByte(columnName);
			}else if(clazzField == char.class || clazzField == Character.class){
				 paramVal = rs.getCharacterStream(columnName);
			}else if(clazzField == Date.class){
				paramVal = rs.getTimestamp(columnName);
			}else if(clazzField.isArray()){
				paramVal = rs.getString(columnName).split(",");
			}
			
			//字段值-属性
			PropertyDescriptor pd = new PropertyDescriptor(propertyName, t.getClass());
			pd.getWriteMethod().invoke(t, paramVal);
			
		}
	}
	
	public List<T> findAll() {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		List<T> list = null;
		
		conn = DBManager.getConn();
		
		//构建SQL语句
		String tableName = getTableName(clazz);
		String sql = "select * from " + tableName;
		
		try {
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			list = new ArrayList<T>();
			Field[] fields = clazz.getDeclaredFields();
			
			while(rs.next()){
				T obj = clazz.newInstance();
				initObject(obj, fields, rs);
				list.add(obj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBManager.closeConn(conn);
		}

		return list;
		
	}
/**
 * select top 页大小 * from table
 * where id not in
 * (select top (页大小 * (页数-1)) id from table where... order by id)
 * and...
 * order by id
 * 
 */
/**
 * 得到记录总数
 * @return
 */
public int getCount() {
	int count = 0;
	String tableName = getTableName(clazz);
	String sql="select count(*) from "+tableName;
	
	Connection conn = DBManager.getConn();
	Statement st = null;
	ResultSet rs = null;
	
	try {
		st = conn.createStatement();
		rs = st.executeQuery(sql);
		rs.next();
		count = rs.getInt(1);
	} catch (SQLException e) {
		e.printStackTrace();

	}
	return count;
}

/**
 * paramMap:构造SQL语句的参数
 * pageSize:每页显示多少条
 * pageNumber:当前页
 * maxElements:总记录数
 * return:分页对象
 */
/**
 * 分页SQL语句
 * select top 页大小 * from table
 * where id not in 
 * (select top (页大小*(页数-1)) id from table order by id)
 * order by id
 * @throws IllegalAccessException 
 * @throws InstantiationException 
 * @throws SQLException 
 */
public Pagination<T> getPagination(Map<String, Object> paramMap, int pageSize,
		int pageNumber, int maxElements)  {
	
	List<T> list = new ArrayList<T>();
	
	String tableName = getTableName(clazz);
	StringBuilder sql = new StringBuilder();
	//构造SQL语句
	if(paramMap != null) {//有条件查询
		sql.append("select top ").append(pageSize).append(" * from ").append(tableName)
		.append(" where id not in ").append("(select top ")
		.append(pageSize*(pageNumber-1)).append(" id from ").append(tableName);
		int i = 0;
		for(String key:paramMap.keySet()) {
			if(i==0){
				sql.append(" where").append(key).append("=").append(paramMap.get(key));
			}else {
				sql.append(" and").append(key).append("=").append(paramMap.get(key));
			}
			i++;
		}
		
		sql.append(" order by id)");
		for(String key:paramMap.keySet()){
			sql.append(" and").append(key).append("=").append(paramMap.get(key));
		}
		sql.append(" order by id");
	}else {//有条件查询
		sql.append("select top ").append(pageSize).append(" * from ").append(tableName)
		.append(" where id not in ").append("(select top ")
		.append(pageSize*(pageNumber-1)).append(" id from ").append(tableName)
		.append(" order by id)")
		.append(" order by id");	
	}
	Connection conn = DBManager.getConn();
	try {
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(sql.toString());
		Field[] fields = clazz.getDeclaredFields();
		T obj = null;
		while(rs.next()) {
			obj = clazz.newInstance();
			initObject(obj, fields, rs);
			list.add(obj);
		}
	} catch (SQLException e) {
		e.printStackTrace();
	} catch (InstantiationException e) {
		e.printStackTrace();
	} catch (IllegalAccessException e) {
		e.printStackTrace();
	} catch (IllegalArgumentException e) {
		e.printStackTrace();
	} catch (InvocationTargetException e) {
		e.printStackTrace();
	} catch (IntrospectionException e) {
		e.printStackTrace();
	}finally {
		DBManager.closeConn(conn);
	}
	
	Pagination<T> pagination = new Pagination<T>(pageSize, pageNumber, maxElements);
	pagination.setPageList(list);
			
	return pagination;
}

}
