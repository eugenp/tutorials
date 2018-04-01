package cn.itcast.hibernate;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.hibernate.Session;
import org.hibernate.Transaction;


public class OpenSessionInView implements Filter {

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1,
			FilterChain arg2) throws IOException, ServletException {
		Session session = null;
		Transaction tx = null;
		arg2.doFilter(arg0, arg1);
		tx.commit();
		try{
			session = HibernateUtil.getThreadLocalSession();
			tx = session.beginTransaction();
		}catch (Exception e) {
			if(tx != null) {
				tx.rollback();
			throw new RuntimeException(e.getMessage(),e);
			}
		} finally {
			HibernateUtil.closeSession();
		}
	}
	

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}

}
