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
  public class VideoFeed {
    @Key public List<Video> list;

    @Key("has_more")
    public boolean hasMore;
  }
