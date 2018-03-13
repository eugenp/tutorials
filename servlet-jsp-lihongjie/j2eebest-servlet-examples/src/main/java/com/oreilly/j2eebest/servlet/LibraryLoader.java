package com.oreilly.j2eebest.servlet;

import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class LibraryLoader {

  static final String CONFIG_FILE = "library.properties";
  static final String CACHE_FILE =  "library.ser";

  public ConfigData load() throws IOException {
    // Find the config file and fetch its contents as Properties
    Resource config = new Resource(CONFIG_FILE);
    Properties props = new Properties();
    props.load(config.getInputStream());

    // Determine the source dir of the config file and look for a cache file
    // next to it containing a full representation of our program state.
    // If we find a cache file and it is current, load and return that data.
    if (config.getDirectory() != null) {
      File cache = new File(config.getDirectory(), CACHE_FILE);
      if (cache.exists() &&
            cache.lastModified() >= config.lastModified()) {
        try {
          return loadCache(new FileInputStream(cache));
        }
        catch (IOException ignored) { }
      }
    }

    // We get here if there's no cache file or it's stale and we need to do a
    // full reload.  Locate the name of the raw data file from the config file
    // and return its contents using Resource.
    Resource data = new Resource(props.getProperty("data.file"));
    return loadData(data.getInputStream());
  }

  private ConfigData loadCache(InputStream in) {
    // Read the file, perhaps as a serialized object
    return null;
  }

  private ConfigData loadData(InputStream in) {
    // Read the file, perhaps as XML
    return null;
  }

  class ConfigData {
    // An example class that would hold config data
  }
}
