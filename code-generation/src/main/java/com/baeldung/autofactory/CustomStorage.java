package com.baeldung.autofactory;

import com.baeldung.autofactory.custom.SmartPhone;

/**
 * @author aiet
 */
public interface CustomStorage {

    SmartPhone customROMInGB(int romSize);

}
