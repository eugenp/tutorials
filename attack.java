import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.hibernate.cfg.Configuration;
 
public class Main {
    public static void main(String[] args) {
        // Assume inputColumnName is obtained from user input
        String inputColumnName = "age"; // Assume a valid column name
 
        // Create a Hibernate configuration
        Configuration configuration = new Configuration().configure();
        SessionFactory sessionFactory = configuration.buildSessionFactory();
 
        // Open a session
        try (Session session = sessionFactory.openSession()) {
            // Begin a transaction
            Transaction transaction = session.beginTransaction();
 
            // The modified code using a parameterized query
            String hsql = "update People set " + inputColumnName + " = :value";
            Query query = session.createQuery(hsql);
            query.setParameter("value", null);
            query.executeUpdate();
 
            // Commit the transaction
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Close the session factory
            sessionFactory.close();
        }
    }
}
