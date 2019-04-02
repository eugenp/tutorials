module com.baeldung.dip.daoimplementations {
    requires com.baeldung.dip.entities;
    requires com.baeldung.dip.daos;
    provides com.baeldung.dip.daos.CustomerDao with com.baeldung.dip.daoimplementations.SimpleCustomerDao;
    exports com.baeldung.dip.daoimplementations;
}
