module com.baeldung.userdao {
    requires com.baeldung.entity;
    requires com.baeldung.dao;
    provides com.baeldung.dao.Dao with com.baeldung.userdao.UserDao;
    exports com.baeldung.userdao;
}
