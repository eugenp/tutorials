import org.javalite.activejdbc.Base;

public class Application {

    public static void main(String[] args){

        try {
            Base.open("com.mysql.jdbc.Driver",
              "jdbc:mysql://localhost/test",
              "root", "password");
            Base.openTransaction();
            Student e = new Student();
            e.set("name", "Jane");
            e.set("age", 21);
            e.saveIt();
            Base.commitTransaction();
        } catch (Exception e) {
            Base.rollbackTransaction();
        } finally {
            Base.close();
        }

    }
}
