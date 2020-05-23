package baeldung.service;

public interface BookProviderNotificationService {

    void notifyMissingBook(String query);

    void notifyNotEnoughBooks(String query, Integer numberOfCurrentBooks);

}
