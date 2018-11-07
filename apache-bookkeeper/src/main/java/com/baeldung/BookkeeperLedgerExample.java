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


public class BookkeeperLedgerExample {

  public static void main(String[] args) throws Exception {

    ClientConfiguration clientConfiguration = new ClientConfiguration();

    BookKeeper bkc = BookKeeper.newBuilder(clientConfiguration.setZkServers("127.0.0.1:2181"))
        .build();

    //create ledger with the specicfied password
    byte[] ledgerPassword = "test".getBytes();

    WriteHandle lh = bkc.newCreateLedgerOp()
        .withPassword(ledgerPassword)
        .withDigestType(DigestType.MAC)
        .execute()
        .get();
    long ledgerId = lh.getId();

    //create ledger entries

    ByteBuffer entry = ByteBuffer.allocate(4);
    int numberOfEntries = 100;

    // Add entries to the ledger, then close it
    for (int i = 0; i < numberOfEntries; i++) {
      entry.putInt(i);
      entry.position(0);
      lh.append(entry.array());
    }
    lh.close();

    // Open the ledger for reading
    ReadHandle readHandle = bkc.newOpenLedgerOp().withLedgerId(ledgerId)
        .withDigestType(DigestType.MAC)
        .withPassword(ledgerPassword)
        .execute()
        .get();

    // Read all available entries
    LedgerEntries ledgerEntries = readHandle.read(0, numberOfEntries - 1);

    Iterator<LedgerEntry> ledgerEntryIterator = ledgerEntries.iterator();

    while (ledgerEntryIterator.hasNext()) {
      ByteBuffer result = ByteBuffer.wrap(ledgerEntryIterator.next().getEntryBytes());
      Integer retrEntry = result.getInt();

      // Print the integer stored in each entry
      System.out.println(String.format("Result: %s", retrEntry));
    }

    // Close the ledger and the client
    readHandle.close();
    bkc.close();

  }
}
