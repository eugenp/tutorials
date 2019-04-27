module com.baeldung.userdao {
    requires com.baeldung.dao;
    requires com.baeldung.entity;
    provides com.baeldung.dao.Dao with com.baeldung.userdao.UserDao;
    exports com.baeldung.userdao;
}
