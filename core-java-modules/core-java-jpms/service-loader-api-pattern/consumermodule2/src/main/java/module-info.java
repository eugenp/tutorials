module com.baeldung.consumermodule {
    requires com.baeldung.servicemodule;
    uses com.baeldung.servicemodule.TextService;
}
