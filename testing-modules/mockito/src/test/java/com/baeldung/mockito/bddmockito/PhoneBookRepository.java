package com.baeldung.mockito.bddmockito;

public interface PhoneBookRepository {

	/**
	 * Insert phone record
	 * @param name Contact name
	 * @param phone Phone number
	 */
    void insert(String name, String phone);
    
    /**
     * Search for contact phone number
     * @param name Contact name
     * @return phone number
     */
    String getPhoneNumberByContactName(String name);
    
    /**
     * Check if the phonebook contains this contact
     * @param name Contact name
     * @return true if this contact name exists
     */
    boolean contains(String name);
    
}
