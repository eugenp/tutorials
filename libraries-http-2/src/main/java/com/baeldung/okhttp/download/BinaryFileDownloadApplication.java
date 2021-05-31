package com.baeldung.okhttp.download;

import okhttp3.OkHttpClient;

import java.io.FileOutputStream;

public class BinaryFileDownloadApplication {

  public static void main(String[] args) {
    String url = args[0];
    String file = args[1];
    try (BinaryFileDownloader downloader = new BinaryFileDownloader(new OkHttpClient(),
                                                                    new BinaryFileWriter(new FileOutputStream(file)))) {
      long downloadSize = downloader.download(url);
      double downloadSizeInMB = convertToMB(downloadSize);
      System.out.printf("Successfully downloaded file %s from %s. Total size %.2fMB%n", file, url, downloadSizeInMB);
    } catch (Exception ex) {
      System.err.printf("Could not download file %s from %s. %nError: %s%n", file, url, ex);
    }
  }

  private static double convertToMB(double downloadSize) {
    return downloadSize / (1024.0 * 1024.0);
  }

}
