module com.baeldung.userdaomodule {
    requires com.baeldung.entitymodule;
    requires com.baeldung.daomodule;
    provides com.baeldung.daomodule.Dao with com.baeldung.userdaomodule.UserDao;
    exports com.baeldung.userdaomodule;
}
