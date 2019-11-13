/*
 * package com.concertosoft.Dao;
 * 
 * import java.util.List;
 * 
 * 
 * 
 * import org.hibernate.Criteria; import org.hibernate.Session; import
 * org.hibernate.SessionFactory; import
 * org.springframework.beans.factory.annotation.Autowired; import
 * org.springframework.stereotype.Component; //import
 * org.springframework.transaction.annotation.Transactional;
 * 
 * import com.concertosoft.MessageDetail; import com.concertosoft.pojo.Payer;
 * 
 * @Component //@Transactional public class MessageDaoImpl implements
 * MessageRepository{
 * 
 * @Autowired private SessionFactory sessionFactory;
 * 
 * protected Session getSession() { return sessionFactory.getCurrentSession(); }
 * public void save(MessageDetail messageData) {
 * getSession().persist(messageData);
 * 
 * }
 * 
 * public List<MessageDetail> getList() { Criteria criteria =
 * getSession().createCriteria(MessageDetail.class); return
 * (List<MessageDetail>) criteria.list(); }
 * 
 * public void update(MessageDetail messageData) {
 * getSession().update(messageData); }
 * 
 * 
 * }
 */