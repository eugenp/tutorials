/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.baeldung.examples.guice.constant;

/**
 *
 * @author Tayo
 */
public enum CommunicationModel {

    EMAIL("Email"), SMS("SMS"), IM("IM"), PHONE("Phone");

    final String name;

    CommunicationModel(String name) {
        this.name = name;
    }
}
