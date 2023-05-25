package com.baeldung.jmxterm;

import java.lang.management.ManagementFactory;
import javax.management.InstanceAlreadyExistsException;
import javax.management.MBeanRegistrationException;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.NotCompliantMBeanException;
import javax.management.ObjectName;

public class MBeanGameServer {

    public static final String ID_FORMAT = "com.baeldung.jmxterm:type=%s,id=%s";
    private final MBeanServer server = ManagementFactory.getPlatformMBeanServer();

    public void registerPlayer(AbstractPlayerMBean player) {
        registerBean(player, "player", player.getId());
    }

    public void registerGame(GuessGame guessGame) {
        registerBean(guessGame, "game", "singlegame");
        guessGame.getPlayers().forEach(this::registerPlayer);
    }

    private void registerBean(Object bean, String type, String id) {
        try {
            ObjectName name = new ObjectName(String.format(ID_FORMAT, type, id));
            server.registerMBean(bean, name);
        } catch (InstanceAlreadyExistsException |
                 MBeanRegistrationException |
                 NotCompliantMBeanException |
                 MalformedObjectNameException e) {
            throw new RuntimeException(e);
        }
    }

}
