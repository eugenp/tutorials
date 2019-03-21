module com.baeldung.dipmodular.services {
    requires com.baeldung.dipmodular.entities;
    requires com.baeldung.dipmodular.daos;
    uses com.baeldung.dipmodular.daos.CustomerDao;
    exports com.baeldung.dipmodular.services;
}
