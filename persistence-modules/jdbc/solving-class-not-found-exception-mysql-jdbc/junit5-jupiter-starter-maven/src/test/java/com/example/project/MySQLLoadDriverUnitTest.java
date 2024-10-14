public class MySQLLoadDriverUnitTest {

    @Test
    void givenADriverClass_whenDriverLoaded_thenEnsureNoExceptionThrown() {
        assertDoesNotThrow(() -> {
            Class.forName("com.mysql.cj.jdbc.Driver");
        });
    }

}
