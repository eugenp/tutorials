module com.baeldung.dipmodular.daoimplementations {
    requires com.baeldung.dipmodular.entities;
    requires com.baeldung.dipmodular.daos;
    provides com.baeldung.dipmodular.daos.CustomerDao with com.baeldung.dipmodular.daoimplementations.ListCustomerDao;
    exports com.baeldung.dipmodular.daoimplementations;
}
