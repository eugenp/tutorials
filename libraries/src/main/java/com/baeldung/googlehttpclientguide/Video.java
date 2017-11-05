/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.baeldung.googlehttpclientguide;

import com.google.api.client.util.Key;
import java.util.List;

/**
 *
 * @author Hugo
 */
  public  class Video {
    @Key public String id;

    @Key public List<String> tags;

    @Key public String title;

    @Key public String url;
  }