module com.baeldung.mainppmodule {
    
    requires com.baeldung.entitymodule;
    requires com.baeldung.daomodule;
    requires com.baeldung.userdaomodule;
    uses com.baeldung.userdaomodule.UserDao;
    
}
