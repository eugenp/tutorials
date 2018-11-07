package com.baeldung;


import java.nio.ByteBuffer;
import java.util.Iterator;
import org.apache.bookkeeper.client.api.BookKeeper;
import org.apache.bookkeeper.client.api.DigestType;
import org.apache.bookkeeper.client.api.LedgerEntries;
import org.apache.bookkeeper.client.api.LedgerEntry;
import org.apache.bookkeeper.client.api.ReadHandle;
import org.apache.bookkeeper.client.api.WriteHandle;
import org.apache.bookkeeper.conf.ClientConfiguration;


public class BookkeeperLedger {

  private String host;
  private final static int NUMBER_OF_ENTRIES = 100;
  private final static byte[] LEDGER_PASSWORD = "test".getBytes();
  private BookKeeper bookKeeper;
  private long ledgerId;
  private WriteHandle writeHandle;


  public BookkeeperLedger() {
    this.host = "127.0.0.1:2181";
  }

  public BookkeeperLedger(String host) {
    this.host = host;
  }

  public void connect() throws Exception {
    ClientConfiguration clientConfiguration = new ClientConfiguration();
    bookKeeper = BookKeeper.newBuilder(clientConfiguration.setZkServers(host))
        .build();
  }

  public long createLedger() throws Exception {
    writeHandle = bookKeeper.newCreateLedgerOp()
        .withPassword(LEDGER_PASSWORD)
        .withDigestType(DigestType.MAC)
        .execute()
        .get();
    ledgerId = writeHandle.getId();
    return ledgerId;
  }

  public void write() throws Exception {
    ByteBuffer entry = ByteBuffer.allocate(4);

    // Add entries to the ledger, then close it
    for (int i = 0; i < NUMBER_OF_ENTRIES; i++) {
      entry.putInt(i);
      entry.position(0);
      writeHandle.append(entry.array());
    }
    writeHandle.close();
  }

  public int read() throws Exception {
    ReadHandle readHandle = bookKeeper.newOpenLedgerOp().withLedgerId(ledgerId)
        .withDigestType(DigestType.MAC)
        .withPassword(LEDGER_PASSWORD)
        .execute()
        .get();

    // Read all available entries
    LedgerEntries ledgerEntries = readHandle.read(0, NUMBER_OF_ENTRIES - 1);

    Iterator<LedgerEntry> ledgerEntryIterator = ledgerEntries.iterator();
    int count = 0;

    while (ledgerEntryIterator.hasNext()) {
      count++;
      ByteBuffer result = ByteBuffer.wrap(ledgerEntryIterator.next().getEntryBytes());
      Integer retrEntry = result.getInt();
      // Print the integer stored in each entry
      System.out.println(String.format("Result: %s", retrEntry));
    }

    // Close the ledger and the client
    readHandle.close();
    return count;
  }

  public void closeConnection()throws Exception{
    bookKeeper.close();
  }


  public static void main(String[] args) throws Exception {

    BookkeeperLedger bookkeeperLedger = new BookkeeperLedger();
    bookkeeperLedger.connect();
    bookkeeperLedger.createLedger();
    bookkeeperLedger.write();
    bookkeeperLedger.read();
    bookkeeperLedger.closeConnection();
  }
}
