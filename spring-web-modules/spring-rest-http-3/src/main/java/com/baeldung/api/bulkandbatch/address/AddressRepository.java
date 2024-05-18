package com.baeldung.api.bulkandbatch.address;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Component
public class AddressRepository {

    private final Map<String, Address> addressMap = new HashMap<>();

    public Set<Address> getAddresses() {
        return new HashSet<>(addressMap.values());
    }

    public Address createOrUpdateAddress(Address address) {
        Address address1;
        if (addressMap.containsKey(address.getAddressLn1() + address.getCity())) {
            address1 = addressMap.get(address.getAddressLn1() + address.getCity());
            address1.setAddressLn1(address.getAddressLn1());
            address1.setCity(address.getCity());
            address1.setPinCode(address.getPinCode());
        } else {
            address1 = getAddress(addressMap.size() + 1, address.getAddressLn1(),
                    address.getCity(), address.getPinCode());
            addressMap.put(address.getAddressLn1() + address.getCity(), address1);
        }

        return address1;
    }

    private static Address getAddress(int id, String addressLn1, String city, int pinCode) {
        Address address = new Address();
        address.setId(id);
        address.setAddressLn1(addressLn1);
        address.setCity(city);
        address.setPinCode(pinCode);

        return address;
    }
}
