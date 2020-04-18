module com.baeldung.mainapp {
    requires com.baeldung.entity;
    requires com.baeldung.userdao;
    requires com.baeldung.dao;
    uses com.baeldung.dao.Dao;
}
