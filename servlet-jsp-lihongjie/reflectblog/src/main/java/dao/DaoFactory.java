package dao;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * DAO工厂类：负责向service层提供所需要的DAO实现类。
 * DAO工厂类就是向service提
 *
 * @author Administrator
 */
public class DaoFactory {
    private static DaoFactory daoFactory = null;
    //生产的DAO
    private static UserDao userDao = null;
    private static BlogDao blogDao = null;
    private static CategoryDao categoryDao = null;
    private static CommentDao commentDao = null;
    private static EntryDao entryDao = null;
    private static LinkDao linkDao = null;

    private DaoFactory() {
        try {
            //生产对象:通过配置文件来确定dao的实现类
            Properties prop = new Properties();//Property 类，用来读属性文件
            //把daoconfig.properties文件变成输入流
            InputStream inStream = DaoFactory.class.getClassLoader().getResourceAsStream("daoconfig.properties");
            //将daoconfig.properties的键值对放入prop里面
            prop.load(inStream);

            //java反射机制
            String userDaoClass = prop.getProperty("UserDaoClass");
            userDao = (UserDao) Class.forName(userDaoClass).newInstance();

            String blogDaoClass = prop.getProperty("BlogDaoClass");
            blogDao = (BlogDao) Class.forName(blogDaoClass).newInstance();

            String categoryDaoClass = prop.getProperty("CategoryDaoClass");
            categoryDao = (CategoryDao) Class.forName(categoryDaoClass).newInstance();

            String commentDaoClass = prop.getProperty("CommentDaoClass");
            commentDao = (CommentDao) Class.forName(commentDaoClass).newInstance();

            String entryDaoClass = prop.getProperty("EntryDaoClass");
            entryDao = (EntryDao) Class.forName(entryDaoClass).newInstance();

            String linkDaoClass = prop.getProperty("LinkDaoClass");
            linkDao = (LinkDao) Class.forName(linkDaoClass).newInstance();


        } catch (InstantiationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static DaoFactory getInstance() {//两个线程同时进入这个方法
        if (daoFactory == null) { //两个线程同时进入if判断
            synchronized (DaoFactory.class) {//A线程拿到锁，	B线程等待
                if (daoFactory == null) {//B线程
                    daoFactory = new DaoFactory();
                }

            }
        }
        return daoFactory;
    }


    public static DaoFactory getDaoFactory() {
        return daoFactory;
    }

    public static void setDaoFactory(DaoFactory daoFactory) {
        DaoFactory.daoFactory = daoFactory;
    }

    public static UserDao getUserDao() {
        return userDao;
    }

    public static void setUserDao(UserDao userDao) {
        DaoFactory.userDao = userDao;
    }

    public static BlogDao getBlogDao() {
        return blogDao;
    }

    public static void setBlogDao(BlogDao blogDao) {
        DaoFactory.blogDao = blogDao;
    }

    public static CategoryDao getCategoryDao() {
        return categoryDao;
    }

    public static void setCategoryDao(CategoryDao categoryDao) {
        DaoFactory.categoryDao = categoryDao;
    }

    public static CommentDao getCommentDao() {
        return commentDao;
    }

    public static void setCommentDao(CommentDao commentDao) {
        DaoFactory.commentDao = commentDao;
    }

    public static EntryDao getEntryDao() {
        return entryDao;
    }

    public static void setEntryDao(EntryDao entryDao) {
        DaoFactory.entryDao = entryDao;
    }

    public static LinkDao getLinkDao() {
        return linkDao;
    }

    public static void setLinkDao(LinkDao linkDao) {
        DaoFactory.linkDao = linkDao;
    }


}
