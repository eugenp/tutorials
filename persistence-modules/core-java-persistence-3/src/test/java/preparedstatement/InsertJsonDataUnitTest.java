package preparedstatement;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class InsertJsonDataUnitTest {

    @Mock
    InsertJsonData insertJsonData;
    @Mock
    private Connection connection;
    @Mock
    private PreparedStatement preparedStatement;

    @BeforeEach
    public void setup() throws SQLException {
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(insertJsonData.getConnection()).thenReturn(connection);
    }

    @Test
    public void givenValidNameAndInfo_whenInsertUser_thenReturnSuccessMessage() throws SQLException {
        // Given
        String name = "John Doe";
        JSONObject info = new JSONObject();
        info.put("email", "john.doe@example.com");
        info.put("age", 30);
        info.put("active", true);

        // When
        doCallRealMethod().when(insertJsonData).insertUser(name, info);
        insertJsonData.insertUser(name, info);

        // Then
        verify(preparedStatement).setString(1, name);
        verify(preparedStatement).setString(2, info.toString());
        verify(preparedStatement).executeUpdate();
    }
}