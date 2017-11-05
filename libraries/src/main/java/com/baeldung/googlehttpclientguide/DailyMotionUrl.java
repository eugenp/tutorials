/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.baeldung.googlehttpclientguide;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.util.Key;

/**
 *
 * @author Hugo
 */
  public class DailyMotionUrl extends GenericUrl {

    public DailyMotionUrl(String encodedUrl) {
      super(encodedUrl);
    }

    @Key public String fields;
  }
