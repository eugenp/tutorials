module com.baeldung.providermodule {
    requires com.baeldung.servicemodule;
    provides com.baeldung.servicemodule.TextService with com.baeldung.providermodule.LowercaseTextService;
}