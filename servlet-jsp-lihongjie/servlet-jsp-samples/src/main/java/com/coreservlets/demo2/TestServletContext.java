package com.coreservlets.demo2;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Iterator;

/**
 * Servlet implementation class TestServletContext
 * 测试ServletContext的方法: getResourcePaths()
 */
public class TestServletContext extends HttpServlet {
    private static final long serialVersionUID = 1L;

//	private ServletContext context; //不重写有参init方法就可以不用声明ServletConfig,ServletContext对象，因为GenericServlet 已经申明了ServletConfig对象

    /**
     * @see HttpServlet#HttpServlet()
     */
    public TestServletContext() {
        super();
    }

    /**
     * @see Servlet#init(ServletConfig)
     * 重写有参数init方法还是无参init方法，参见笔记
     */
//    @Override
//	public void init(ServletConfig config) throws ServletException {
////		super.init(config);//重写了Servlet的init方法后一定要记得调用父类的有参init方法，否则在service/doGet/doPost方法中使用getServletContext()方法获取ServletContext对象时就会出现java.lang.NullPointerException异常
////		context = getServletContext()
//		//不调用父类init(config) 就得使用config.getServletContext()方式
//		context = config.getServletContext();
//	 System.out.println("调用了有参init方法");
//	}
    @Override
    public void init() throws ServletException {
        System.out.println("调用了 无参init方法");

    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Iterator<String> it = getServletContext().getResourcePaths("/").iterator();
        while (it.hasNext()) {
//			String paths = it.next();
//			System.out.println("path : " + paths);
            String paths = it.next().replaceFirst("/", "");
            System.out.println("替换前面/的路径： " + paths);
        }
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
    }

}
