module com.baeldung.mainapp {
    requires com.baeldung.entity;
    requires com.baeldung.dao;
    requires com.baeldung.userdao;
    uses com.baeldung.dao.Dao;
}
