module com.baeldung.dipmodular.mainapp {
    requires com.baeldung.dipmodular.entities;
    requires com.baeldung.dipmodular.daos;
    requires com.baeldung.dipmodular.daoimplementations;
    requires com.baeldung.dipmodular.services;
    exports com.baeldung.dipmodular.mainapp;
}
