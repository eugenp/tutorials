package org.disco.racer.domain;

import org.disco.racer.shardsupport.ShardedSessionFactoryBuilder;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;

import java.util.Collection;

public class ShardedRaceDAOImpl implements RaceDAO {

    private SessionFactory sessionFactory;


    public ShardedRaceDAOImpl() {
        super();
    }

//    public void setShardedSessionFactory(ShardedSessionFactoryBuilder builder) {
//        this.sessionFactory = builder.createSessionFactory();
//    }

    public void setShardedSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /**
     * @return
     */
    public Collection<Race> findAll() {
        final HibernateTemplate hibernateTemplate = new HibernateTemplate(
                this.sessionFactory);
        return (Collection<Race>) hibernateTemplate.execute(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException {
                return session.createQuery("from org.disco.racer.domain.Race").list();
            }
        });
    }

    /**
     * @param id
     * @return
     */
    public Race findById(final Long id) {

        final HibernateTemplate hibernateTemplate = new HibernateTemplate(
                this.sessionFactory);

        return (Race) hibernateTemplate.execute(new HibernateCallback() {

            public Object doInHibernate(Session session) throws HibernateException {
                return session.createQuery(
                        "from org.disco.racer.domain.Race as race where race.id = ?")
                        .setLong(0, id)
                        .uniqueResult();
            }
        });
    }

    /**
     * @param name
     * @return
     */
    public Race findByName(final String name) {
        final HibernateTemplate hibernateTemplate = new HibernateTemplate(
                this.sessionFactory);

        return (Race) hibernateTemplate.execute(new HibernateCallback() {

            public Object doInHibernate(Session session) throws HibernateException {
                return session.createQuery(
                        "from org.disco.racer.domain.Race as race where race.name = ?")
                        .setString(0, name.trim())
                        .uniqueResult();
            }
        });
    }

    /**
     * @param race
     */
    public void create(final Race race) {
        final HibernateTemplate tmplte = new HibernateTemplate(
                this.sessionFactory);
        tmplte.save(race);
    }

    /**
     * @param race
     */
    public void update(final Race race) {
        final HibernateTemplate tmplte = new HibernateTemplate(
                this.sessionFactory);
        tmplte.saveOrUpdate(race);
    }

    /**
     * @param race
     */
    public void remove(final Race race) {
        final HibernateTemplate tmplte = new HibernateTemplate(
                this.sessionFactory);
        tmplte.delete(race);
    }

}
