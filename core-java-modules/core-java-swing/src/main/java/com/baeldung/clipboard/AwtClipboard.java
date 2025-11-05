package com.baeldung.clipboard;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.awt.datatransfer.DataFlavor;

public class AwtClipboard {

    public static void main(String[] args) throws IOException, UnsupportedFlavorException {
        String textToCopy = "Baeldung helps developers explore the Java ecosystem and simply be better engineers.";
        copyToClipboard(textToCopy);

        String textCopied = copyFromClipboard();
        if (textCopied != null) {
            System.out.println(textCopied);
        }
    }

    public static void copyToClipboard(String text) {
        Clipboard cb = Toolkit.getDefaultToolkit().getSystemClipboard();
        StringSelection data = new StringSelection(text);
        cb.setContents(data, null);
    }

    public static String copyFromClipboard() throws UnsupportedFlavorException, IOException {
        Clipboard cb = Toolkit.getDefaultToolkit().getSystemClipboard();
        Transferable transferable = cb.getContents(null);
        if (transferable.isDataFlavorSupported(DataFlavor.stringFlavor)) {
            String data = (String) transferable.getTransferData(DataFlavor.stringFlavor);
            return data;
        }
        System.out.println("Couldn't get data from the clipboard");
        return null;
    }
}