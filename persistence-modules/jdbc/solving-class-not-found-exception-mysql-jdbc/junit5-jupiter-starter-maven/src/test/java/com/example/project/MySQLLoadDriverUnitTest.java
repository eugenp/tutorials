public class MySQLLoadDriverUnitTest {

    @Test
    void givenADriverClass_whenDriverLoaded_thenEnsureNoExceptionThrown() {
        assertDoesNotThrow(() -&gt; {
            Class.forName("com.mysql.cj.jdbc.Driver");
        });
    }

}
