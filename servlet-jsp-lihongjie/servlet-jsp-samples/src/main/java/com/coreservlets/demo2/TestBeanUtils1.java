package com.coreservlets.demo2;

import com.coreservlets.demo2.bean.Person;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * Servlet implementation class TestBeanUtils1
 * 测试： http://localhost:9090/populate-bean?name=lihongjie&email=726676865@qq.com
 * 输出：name : lihongjie
 *      email : 726676865@qq.com

 */
@WebServlet(urlPatterns = "/populate-bean")
public class TestBeanUtils1 extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public TestBeanUtils1() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Person person = new Person();
        BeanUtilities.populateBean(person, request);
        System.out.println("name : " + person.getName());
        System.out.println("email : " + person.getEmail());
    }

}
