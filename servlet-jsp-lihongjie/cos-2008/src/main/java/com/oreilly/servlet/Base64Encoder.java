// Copyright (C) 1999-2002 by Jason Hunter <jhunter_AT_acm_DOT_org>.
// All rights reserved.  Use of this class is limited.
// Please see the LICENSE for more information.

package com.oreilly.servlet;

import java.io.*;

/** 
 * A class to encode Base64 streams and strings.  
 * See RFC 1521 section 5.2 for details of the Base64 algorithm.
 * <p>
 * This class can be used for encoding strings:
 * <blockquote><pre>
 * String unencoded = "webmaster:try2gueSS";
 * String encoded = Base64Encoder.encode(unencoded);
 * </pre></blockquote>
 * or for encoding streams:
 * <blockquote><pre>
 * OutputStream out = new Base64Encoder(System.out);
 * </pre></blockquote>
 *
 * @author <b>Jason Hunter</b>, Copyright &#169; 2000
 * @version 1.2, 2002/11/01, added encode(byte[]) method to better handle
 *                           binary data (thanks to Sean Graham)
 * @version 1.1, 2000/11/17, fixed bug with sign bit for char values
 * @version 1.0, 2000/06/11
 */
public class Base64Encoder extends FilterOutputStream {

  private static final char[] chars = {
    'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
    'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T',
    'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd',
    'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n',
    'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x',
    'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7',
    '8', '9', '+', '/'
  };

  private int charCount;
  private int carryOver;

  /**
   * Constructs a new Base64 encoder that writes output to the given
   * OutputStream.
   *
   * @param out the output stream
   */
  public Base64Encoder(OutputStream out) {
    super(out);
  }

  /**
   * Writes the given byte to the output stream in an encoded form.
   *
   * @exception IOException if an I/O error occurs
   */
  public void write(int b) throws IOException {
    // Take 24-bits from three octets, translate into four encoded chars
    // Break lines at 76 chars
    // If necessary, pad with 0 bits on the right at the end
    // Use = signs as padding at the end to ensure encodedLength % 4 == 0

    // Remove the sign bit,
    // thanks to Christian Schweingruber <chrigu@lorraine.ch>
    if (b < 0) {
      b += 256;
    }

    // First byte use first six bits, save last two bits
    if (charCount % 3 == 0) {
      int lookup = b >> 2;
      carryOver = b & 3;        // last two bits
      out.write(chars[lookup]);
    }
    // Second byte use previous two bits and first four new bits,
    // save last four bits
    else if (charCount % 3 == 1) {
      int lookup = ((carryOver << 4) + (b >> 4)) & 63;
      carryOver = b & 15;       // last four bits
      out.write(chars[lookup]);
    }
    // Third byte use previous four bits and first two new bits,
    // then use last six new bits
    else if (charCount % 3 == 2) {
      int lookup = ((carryOver << 2) + (b >> 6)) & 63;
      out.write(chars[lookup]);
      lookup = b & 63;          // last six bits
      out.write(chars[lookup]);
      carryOver = 0;
    }
    charCount++;

    // Add newline every 76 output chars (that's 57 input chars)
    if (charCount % 57 == 0) {
      out.write('\n');
    }
  }

  /**
   * Writes the given byte array to the output stream in an 
   * encoded form.
   *
   * @param buf the data to be written
   * @param off the start offset of the data
   * @param len the length of the data
   * @exception IOException if an I/O error occurs
   */
  public void write(byte[] buf, int off, int len) throws IOException {
    // This could of course be optimized
    for (int i = 0; i < len; i++) {
      write(buf[off + i]);
    }
  }

  /**
   * Closes the stream, this MUST be called to ensure proper padding is
   * written to the end of the output stream.
   *
   * @exception IOException if an I/O error occurs
   */
  public void close() throws IOException {
    // Handle leftover bytes
    if (charCount % 3 == 1) {  // one leftover
      int lookup = (carryOver << 4) & 63;
      out.write(chars[lookup]);
      out.write('=');
      out.write('=');
    }
    else if (charCount % 3 == 2) {  // two leftovers
      int lookup = (carryOver << 2) & 63;
      out.write(chars[lookup]);
      out.write('=');
    }
    super.close();
  }

  /**
   * Returns the encoded form of the given unencoded string.  The encoder
   * uses the ISO-8859-1 (Latin-1) encoding to convert the string to bytes.
   * For greater control over the encoding, encode the string to bytes
   * yourself and use encode(byte[]).
   *
   * @param unencoded the string to encode
   * @return the encoded form of the unencoded string
   */
  public static String encode(String unencoded) {
    byte[] bytes = null;
    try {
      bytes = unencoded.getBytes("8859_1");
    }
    catch (UnsupportedEncodingException ignored) { }
    return encode(bytes);
  }

  /**
   * Returns the encoded form of the given unencoded string.
   *
   * @param bytes the bytes to encode
   * @return the encoded form of the unencoded string
   */
  public static String encode(byte[] bytes) {
    ByteArrayOutputStream out = 
      new ByteArrayOutputStream((int) (bytes.length * 1.37));
    Base64Encoder encodedOut = new Base64Encoder(out);
    
    try {
      encodedOut.write(bytes);
      encodedOut.close();

      return out.toString("8859_1");
    }
    catch (IOException ignored) { return null; }
  }

  public static void main(String[] args) throws Exception {
    if (args.length != 1) {
      System.err.println(
        "Usage: java com.oreilly.servlet.Base64Encoder fileToEncode");
      return;
    }

    Base64Encoder encoder = null;
    BufferedInputStream in = null;
    try {
      encoder = new Base64Encoder(System.out);
      in = new BufferedInputStream(new FileInputStream(args[0]));

      byte[] buf = new byte[4 * 1024];  // 4K buffer
      int bytesRead;
      while ((bytesRead = in.read(buf)) != -1) {
        encoder.write(buf, 0, bytesRead);
      }
    }
    finally {
      if (in != null) in.close();
      if (encoder != null) encoder.close();
    }
  }
}
