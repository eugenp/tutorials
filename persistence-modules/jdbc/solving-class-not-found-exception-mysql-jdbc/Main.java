import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
 
public class Main {
    public static void main(String[] args) {
        try {
          
              Class.forName("com.mysql.cj.jdbc.Driver");
              Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/test?" +"user=root");
              System.out.println("Connection to MySQL established”);

        } catch (ClassNotFoundException ex) {
            System.out.println("java.lang.ClassNotFoundException: " + ex.getMessage());
             
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
             
        }
    }
}
