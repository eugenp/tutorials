
package com.baeldung.examples.guice.constant;

/**
 *
 * @author baeldung
 */
public enum CommunicationModel {

    EMAIL("Email"), SMS("SMS"), IM("IM"), PHONE("Phone");

    final String name;

    CommunicationModel(String name) {
        this.name = name;
    }
}
