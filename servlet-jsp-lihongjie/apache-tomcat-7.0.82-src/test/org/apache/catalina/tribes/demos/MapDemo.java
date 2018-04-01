/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.catalina.tribes.demos;

import java.awt.Color;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.Serializable;
import java.util.Random;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;

import org.apache.catalina.tribes.Channel;
import org.apache.catalina.tribes.ChannelListener;
import org.apache.catalina.tribes.ManagedChannel;
import org.apache.catalina.tribes.Member;
import org.apache.catalina.tribes.MembershipListener;
import org.apache.catalina.tribes.tipis.LazyReplicatedMap;

/**
 * Example of how the lazy replicated map works, also shows how the BackupManager
 * works in a Tomcat cluster
 * @author fhanik
 * @version 1.1
 */
public class MapDemo implements ChannelListener, MembershipListener{

    /**
     * The Map containing the replicated data
     */
    protected LazyReplicatedMap<String,StringBuilder> map;

    /**
     * Table to be displayed in Swing
     */
    protected SimpleTableDemo table;

    /**
     * Constructs a map demo object.
     * @param channel - the Tribes channel object to be used for communication
     * @param mapName - the name of this map
     */
    public MapDemo(Channel channel, String mapName ) {
        //instantiate the replicated map
        map = new LazyReplicatedMap<String,StringBuilder>(null, channel, 5000,
                mapName, null);
        //create a gui, name it with the member name of this JVM
        table = SimpleTableDemo.createAndShowGUI(map,channel.getLocalMember(false).getName());
        //add ourself as a listener for messages
        channel.addChannelListener(this);
        //add ourself as a listener for memberships
        channel.addMembershipListener(this);
        //initialize the map by receiving a fake message
        this.messageReceived(null,null);
    }

    /**
     * Decides if the messageReceived should be invoked
     * will always return false since we rely on the
     * lazy map to do all the messaging for us
     */
    @Override
    public boolean accept(Serializable msg, Member source) {
        //simple refresh the table model
        table.dataModel.getValueAt(-1,-1);
        return false;
    }

    /**
     * Invoked if accept returns true.
     * No op for now
     * @param msg - the message received
     * @param source - the sending member
     */
    @Override
    public void messageReceived(Serializable msg, Member source) {
        // NOOP
    }

    /**
     * Invoked when a member is added to the group
     */
    @Override
    public void memberAdded(Member member) {
        // NOOP
    }

    /**
     * Invoked when a member leaves the group
     */
    @Override
    public void memberDisappeared(Member member) {
        //just refresh the table model
        table.dataModel.getValueAt(-1,-1);
    }

    /**
     * Prints usage
     */
    public static void usage() {
        System.out.println("Tribes MapDemo.");
        System.out.println("Usage:\n\t" +
                           "java MapDemo [channel options] mapName\n\t" +
                           "\tChannel options:" +
                           ChannelCreator.usage());
    }

    /**
     * Main method
     * @param args
     * @throws Exception
     */
    @SuppressWarnings("unused")
    public static void main(String[] args) throws Exception {
        long start = System.currentTimeMillis();
        //create a channel object
        ManagedChannel channel = (ManagedChannel) ChannelCreator.createChannel(args);
        //define a map name, unless one is defined as a paramters
        String mapName = "MapDemo";
        if ( args.length > 0 && (!args[args.length-1].startsWith("-"))) {
            mapName = args[args.length-1];
        }
        //start the channel
        channel.start(Channel.DEFAULT);
        //listen for shutdown
        Runtime.getRuntime().addShutdownHook(new Shutdown(channel));
        //create a map demo object
        new MapDemo(channel,mapName);

        //put the main thread to sleep until we are done
        System.out.println("System test complete, time to start="+(System.currentTimeMillis()-start)+" ms. Sleeping to let threads finish.");
        Thread.sleep(60 * 1000 * 60);
    }

    /**
     * Listens for shutdown events, and stops this instance
     */
    public static class Shutdown extends Thread {
        //the channel running in this demo
        ManagedChannel channel = null;

        public Shutdown(ManagedChannel channel) {
            this.channel = channel;
        }


        @Override
        public void run() {
            System.out.println("Shutting down...");
            //create an exit thread that forces a shutdown if the JVM wont exit cleanly
            SystemExit exit = new SystemExit(5000);
            exit.setDaemon(true);
            exit.start();
            try {
                //stop the channel
                channel.stop(Channel.DEFAULT);
            } catch (Exception x) {
                x.printStackTrace();
            }
            System.out.println("Channel stopped.");
        }
    }

    public static class SystemExit extends Thread {
        private long delay;
        public SystemExit(long delay) {
            this.delay = delay;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(delay);
            } catch (Exception x) {
                x.printStackTrace();
            }
            System.exit(0);
        }
    }

    public static class SimpleTableDemo extends JPanel
            implements ActionListener {

        private static final long serialVersionUID = 1L;

        private static int WIDTH = 550;

        private LazyReplicatedMap<String,StringBuilder> map;
        private boolean DEBUG = false;
        AbstractTableModel dataModel = new AbstractTableModel() {


            private static final long serialVersionUID = 1L;
            String[] columnNames = {
                                   "Rownum",
                                   "Key",
                                   "Value",
                                   "Primary Node",
                                   "Backup Node",
                                   "isPrimary",
                                   "isProxy",
                                   "isBackup"};

            @Override
            public int getColumnCount() { return columnNames.length; }

            @Override
            public int getRowCount() {return map.sizeFull() +1; }

            public StringBuilder getMemberNames(Member[] members){
                StringBuilder buf = new StringBuilder();
                if ( members!=null ) {
                    for (int i=0;i<members.length; i++ ) {
                        buf.append(members[i].getName());
                        buf.append("; ");
                    }
                }
                return buf;
            }

            @Override
            public Object getValueAt(int row, int col) {
                if ( row==-1 ) {
                    update();
                    return "";
                }
                if ( row == 0 ) return columnNames[col];
                Object[] keys = map.keySetFull().toArray();
                String key = (String)keys [row-1];
                LazyReplicatedMap.MapEntry<String,StringBuilder> entry =
                        map.getInternal(key);
                switch (col) {
                    case 0: return String.valueOf(row);
                    case 1: return entry.getKey();
                    case 2: return entry.getValue();
                    case 3: return entry.getPrimary()!=null?entry.getPrimary().getName():"null";
                    case 4: return getMemberNames(entry.getBackupNodes());
                    case 5: return Boolean.valueOf(entry.isPrimary());
                    case 6: return Boolean.valueOf(entry.isProxy());
                    case 7: return Boolean.valueOf(entry.isBackup());
                    default: return "";
                }

            }

            public void update() {
                fireTableDataChanged();
            }
        };

        JTextField txtAddKey = new JTextField(20);
        JTextField txtAddValue = new JTextField(20);
        JTextField txtRemoveKey = new JTextField(20);
        JTextField txtChangeKey = new JTextField(20);
        JTextField txtChangeValue = new JTextField(20);

        JTable table = null;
        public SimpleTableDemo(LazyReplicatedMap<String,StringBuilder> map) {
            super();
            this.map = map;

            this.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);

            //final JTable table = new JTable(data, columnNames);
            table = new JTable(dataModel);

            table.setPreferredScrollableViewportSize(new Dimension(WIDTH, 150));
            for ( int i=0; i<table.getColumnCount(); i++ ) {
                TableColumn tm = table.getColumnModel().getColumn(i);
                tm.setCellRenderer(new ColorRenderer());
            }


            if (DEBUG) {
                table.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        printDebugData(table);
                    }
                });
            }

            //setLayout(new GridLayout(5, 0));
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

            //Create the scroll pane and add the table to it.
            JScrollPane scrollPane = new JScrollPane(table);

            //Add the scroll pane to this panel.
            add(scrollPane);

            //create a add value button
            JPanel addpanel = new JPanel();
            addpanel.setPreferredSize(new Dimension(WIDTH,30));
            addpanel.add(createButton("Add","add"));
            addpanel.add(txtAddKey);
            addpanel.add(txtAddValue);
            addpanel.setMaximumSize(new Dimension(WIDTH,30));
            add(addpanel);

            //create a remove value button
            JPanel removepanel = new JPanel( );
            removepanel.setPreferredSize(new Dimension(WIDTH,30));
            removepanel.add(createButton("Remove","remove"));
            removepanel.add(txtRemoveKey);
            removepanel.setMaximumSize(new Dimension(WIDTH,30));
            add(removepanel);

            //create a change value button
            JPanel changepanel = new JPanel( );
            changepanel.add(createButton("Change","change"));
            changepanel.add(txtChangeKey);
            changepanel.add(txtChangeValue);
            changepanel.setPreferredSize(new Dimension(WIDTH,30));
            changepanel.setMaximumSize(new Dimension(WIDTH,30));
            add(changepanel);


            //create sync button
            JPanel syncpanel = new JPanel( );
            syncpanel.add(createButton("Synchronize","sync"));
            syncpanel.add(createButton("Replicate","replicate"));
            syncpanel.add(createButton("Random","random"));
            syncpanel.setPreferredSize(new Dimension(WIDTH,30));
            syncpanel.setMaximumSize(new Dimension(WIDTH,30));
            add(syncpanel);


        }

        public JButton createButton(String text, String command) {
            JButton button = new JButton(text);
            button.setActionCommand(command);
            button.addActionListener(this);
            return button;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println(e.getActionCommand());
            if ( "add".equals(e.getActionCommand()) ) {
                System.out.println("Add key:"+txtAddKey.getText()+" value:"+txtAddValue.getText());
                map.put(txtAddKey.getText(),new StringBuilder(txtAddValue.getText()));
            }
            if ( "change".equals(e.getActionCommand()) ) {
                System.out.println("Change key:"+txtChangeKey.getText()+" value:"+txtChangeValue.getText());
                StringBuilder buf = map.get(txtChangeKey.getText());
                if ( buf!=null ) {
                    buf.delete(0,buf.length());
                    buf.append(txtChangeValue.getText());
                    map.replicate(txtChangeKey.getText(),true);
                } else {
                    buf = new StringBuilder();
                    buf.append(txtChangeValue.getText());
                    map.put(txtChangeKey.getText(),buf);
                }
            }
            if ( "remove".equals(e.getActionCommand()) ) {
                System.out.println("Remove key:"+txtRemoveKey.getText());
                map.remove(txtRemoveKey.getText());
            }
            if ( "sync".equals(e.getActionCommand()) ) {
                System.out.println("Syncing from another node.");
                map.transferState();
            }
            if ( "random".equals(e.getActionCommand()) ) {
                Thread t = new Thread() {
                    @Override
                    public void run() {
                        for (int i = 0; i < 5; i++) {
                            String key = random(5,0,0,true,true,null);
                            map.put(key, new StringBuilder(key));
                            dataModel.fireTableDataChanged();
                            table.paint(table.getGraphics());
                            try {
                                Thread.sleep(500);
                            } catch (InterruptedException x) {
                                Thread.interrupted();
                            }
                        }
                    }
                };
                t.start();
            }

            if ( "replicate".equals(e.getActionCommand()) ) {
                System.out.println("Replicating out to the other nodes.");
                map.replicate(true);
            }
            dataModel.getValueAt(-1,-1);
        }

        public static Random random = new Random();
        public static String random(int count, int start, int end, boolean letters, boolean numbers,
                                    char[] chars ) {
            if (count == 0) {
                return "";
            } else if (count < 0) {
                throw new IllegalArgumentException("Requested random string length " + count + " is less than 0.");
            }
            if ((start == 0) && (end == 0)) {
                end = 'z' + 1;
                start = ' ';
                if (!letters && !numbers) {
                    start = 0;
                    end = Integer.MAX_VALUE;
                }
            }

            char[] buffer = new char[count];
            int gap = end - start;

            while (count-- != 0) {
                char ch;
                if (chars == null) {
                    ch = (char) (random.nextInt(gap) + start);
                } else {
                    ch = chars[random.nextInt(gap) + start];
                }
                if ((letters && Character.isLetter(ch))
                    || (numbers && Character.isDigit(ch))
                    || (!letters && !numbers))
                {
                    if(ch >= 56320 && ch <= 57343) {
                        if(count == 0) {
                            count++;
                        } else {
                            // low surrogate, insert high surrogate after putting it in
                            buffer[count] = ch;
                            count--;
                            buffer[count] = (char) (55296 + random.nextInt(128));
                        }
                    } else if(ch >= 55296 && ch <= 56191) {
                        if(count == 0) {
                            count++;
                        } else {
                            // high surrogate, insert low surrogate before putting it in
                            buffer[count] = (char) (56320 + random.nextInt(128));
                            count--;
                            buffer[count] = ch;
                        }
                    } else if(ch >= 56192 && ch <= 56319) {
                        // private high surrogate, no effing clue, so skip it
                        count++;
                    } else {
                        buffer[count] = ch;
                    }
                } else {
                    count++;
                }
            }
            return new String(buffer);
    }

        private void printDebugData(JTable table) {
            int numRows = table.getRowCount();
            int numCols = table.getColumnCount();
            javax.swing.table.TableModel model = table.getModel();

            System.out.println("Value of data: ");
            for (int i = 0; i < numRows; i++) {
                System.out.print("    row " + i + ":");
                for (int j = 0; j < numCols; j++) {
                    System.out.print("  " + model.getValueAt(i, j));
                }
                System.out.println();
            }
            System.out.println("--------------------------");
        }

        /**
         * Create the GUI and show it.  For thread safety,
         * this method should be invoked from the
         * event-dispatching thread.
         */
        public static SimpleTableDemo createAndShowGUI(
                LazyReplicatedMap<String,StringBuilder> map, String title) {
            //Make sure we have nice window decorations.
            JFrame.setDefaultLookAndFeelDecorated(true);

            //Create and set up the window.
            JFrame frame = new JFrame("SimpleTableDemo - "+title);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            //Create and set up the content pane.
            SimpleTableDemo newContentPane = new SimpleTableDemo(map);
            newContentPane.setOpaque(true); //content panes must be opaque
            frame.setContentPane(newContentPane);

            //Display the window.
            frame.setSize(450,250);
            newContentPane.setSize(450,300);
            frame.pack();
            frame.setVisible(true);
            return newContentPane;
        }
    }

    static class ColorRenderer extends DefaultTableCellRenderer {

        private static final long serialVersionUID = 1L;

        public ColorRenderer() {
            super();
        }

        @Override
        public Component getTableCellRendererComponent
            (JTable table, Object value, boolean isSelected,
             boolean hasFocus, int row, int column) {
            Component cell = super.getTableCellRendererComponent
                             (table, value, isSelected, hasFocus, row, column);
            cell.setBackground(Color.WHITE);
            if ( row > 0 ) {
                Color color = null;
                boolean primary = ( (Boolean) table.getValueAt(row, 5)).booleanValue();
                boolean proxy = ( (Boolean) table.getValueAt(row, 6)).booleanValue();
                boolean backup = ( (Boolean) table.getValueAt(row, 7)).booleanValue();
                if (primary) color = Color.GREEN;
                else if (proxy) color = Color.RED;
                else if (backup) color = Color.BLUE;
                if ( color != null ) cell.setBackground(color);
            }
            return cell;
        }


    }


}
