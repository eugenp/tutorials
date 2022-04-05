package com.baeldung.system;

import java.awt.event.WindowEvent;

/**
 * Note: This class is not meant for unit-testing since it uses system
 * features at low level and that it uses 'System' gc() which suggests
 * JVM for garbage collection. But the usage below demonstrates how the
 * method can be used.
 */
public class ChatWindow {
    public void windowStateChanged(WindowEvent event) {
        if (event.getNewState() == WindowEvent.WINDOW_DEACTIVATED ) {
            System.gc(); // if it ends up running, great!
        }
    }
}
