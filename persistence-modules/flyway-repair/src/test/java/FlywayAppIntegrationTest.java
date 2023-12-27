import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;

import javax.sql.DataSource;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.baeldung.flywaycallbacks.FlywayApplication;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = { FlywayApplication.class })
public class FlywayAppIntegrationTest {

    @Autowired
    private DataSource dataSource;

    @Test
    public void testAllMigrationsExecuted() throws SQLException {
        DatabaseMetaData metadata = dataSource.getConnection()
            .getMetaData();
        ResultSet resultSet = metadata.getTables(null, null, null, new String[] { "TABLE" });
        Set<String> tables = Set.of("TABLE_ONE", "TABLE_TWO", "TABLE_THREE", "TABLE_FOUR");
        int migrations = 0;
        while (resultSet.next()) {
            if (tables.contains(resultSet.getString("TABLE_NAME"))) {
                migrations++;
            }
        }
        Assert.assertEquals(migrations, 4);
    }
}
