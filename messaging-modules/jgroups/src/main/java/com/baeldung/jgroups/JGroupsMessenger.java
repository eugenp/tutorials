package com.baeldung.jgroups;

import org.apache.commons.cli.*;
import org.jgroups.*;
import org.jgroups.util.Util;

import java.io.*;
import java.util.List;
import java.util.Optional;

public class JGroupsMessenger extends ReceiverAdapter {

    private JChannel channel;
    private String userName;
    private String clusterName;
    private View lastView;
    private boolean running = true;

    // Our shared state
    private Integer messageCount = 0;

    /**
     * Connect to a JGroups cluster using command line options
     * @param args command line arguments
     * @throws Exception
     */
    private void start(String[] args) throws Exception {
        processCommandline(args);

        // Create the channel
        // This file could be moved, or made a command line option.
        channel = new JChannel("src/main/resources/udp.xml");

        // Set a name
        channel.name(userName);

        // Register for callbacks
        channel.setReceiver(this);

        // Ignore our messages
        channel.setDiscardOwnMessages(true);

        // Connect
        channel.connect(clusterName);

        // Start state transfer
        channel.getState(null, 0);

        // Do the things
        processInput();

        // Clean up
        channel.close();

    }

    /**
     * Quick and dirty implementaton of commons cli for command line args
     * @param args the command line args
     * @throws ParseException
     */
    private void processCommandline(String[] args) throws ParseException {

        // Options, parser, friendly help
        Options options = new Options();
        CommandLineParser parser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();

        options.addOption("u", "user", true, "User name")
            .addOption("c", "cluster", true, "Cluster name");

        CommandLine line = parser.parse(options, args);

        if (line.hasOption("user")) {
            userName = line.getOptionValue("user");
        } else {
            formatter.printHelp("JGroupsMessenger: need a user name.\n", options);
            System.exit(-1);
        }

        if (line.hasOption("cluster")) {
            clusterName = line.getOptionValue("cluster");
        } else {
            formatter.printHelp("JGroupsMessenger: need a cluster name.\n", options);
            System.exit(-1);
        }
    }

    // Start it up
    public static void main(String[] args) throws Exception {
        new JGroupsMessenger().start(args);
    }


    @Override
    public void viewAccepted(View newView) {

        // Save view if this is the first
        if (lastView == null) {
            System.out.println("Received initial view:");
            newView.forEach(System.out::println);
        } else {
            // Compare to last view
            System.out.println("Received new view.");

            List<Address> newMembers = View.newMembers(lastView, newView);
            System.out.println("New members: ");
            newMembers.forEach(System.out::println);

            List<Address> exMembers = View.leftMembers(lastView, newView);
            System.out.println("Exited members:");
            exMembers.forEach(System.out::println);
        }
        lastView = newView;
    }

    /**
     * Loop on console input until we see 'x' to exit
     */
    private void processInput() throws Exception {

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        while (running) {
            try {

                // Get a destination, <enter> means broadcast
                Address destination = null;
                System.out.print("Enter a destination: ");
                System.out.flush();
                String destinationName = in.readLine().toLowerCase();

                if (destinationName.equals("x")) {
                    running = false;
                    continue;
                } else if (!destinationName.isEmpty()) {
                    destination = getAddress(destinationName)
                      . orElseThrow(() -> new Exception("Destination not found"));
                }

                // Accept a string to send
                System.out.print("Enter a message: ");
                System.out.flush();
                String line = in.readLine().toLowerCase();
                sendMessage(destination, line);
            } catch (IOException ioe) {
                running = false;
            }
        }
        System.out.println("Exiting.");
    }

    /**
     * Send message from here
     * @param destination the destination
     * @param messageString the message
     */
    private void sendMessage(Address destination, String messageString) {
        try {
            System.out.println("Sending " + messageString + " to " + destination);
            Message message = new Message(destination, messageString);
            channel.send(message);
        } catch (Exception exception) {
            System.err.println("Exception sending message: " + exception.getMessage());
            running = false;
        }
    }

    @Override
    public void receive(Message message) {
        // Print source and dest with message
        String line = "Message received from: " + message.getSrc() + " to: " + message.getDest() + " -> " + message.getObject();

        // Only track the count of broadcast messages
        // Tracking direct message would make for a pointless state
        if (message.getDest() == null) {
            messageCount++;
            System.out.println("Message count: " + messageCount);
        }

        System.out.println(line);
    }

    @Override
    public void getState(OutputStream output) throws Exception {
        // Serialize into the stream
        Util.objectToStream(messageCount, new DataOutputStream(output));
    }

    @Override
    public void setState(InputStream input) {

        // NOTE: since we know that incrementing the count and transferring the state
        // is done inside the JChannel's thread, we don't have to worry about synchronizing
        // messageCount. For production code it should be synchronized!
        try {
            // Deserialize
            messageCount = Util.objectFromStream(new DataInputStream(input));
        } catch (Exception e) {
            System.out.println("Error deserialing state!");
        }
        System.out.println(messageCount + " is the current messagecount.");
    }


    private Optional<Address> getAddress(String name) {
        View view = channel.view();
        return view.getMembers().stream().filter(address -> name.equals(address.toString())).findFirst();
    }

}


