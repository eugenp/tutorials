package com.baeldung.hexagonalArchitecture.adapters.jaxb;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.namespace.QName;
import javax.xml.transform.stream.StreamSource;

import com.baeldung.hexagonalArchitecture.domain.Player;
import com.baeldung.hexagonalArchitecture.ports.PlayerRepository;

public class PlayerRepositoryJaxbImpl implements PlayerRepository {
    private static final String XML_FILE_NAME = "players.xml";

    @Override
    public boolean savePlayer(Player player) {
        File xmlFile = new File(XML_FILE_NAME);
        PlayerListWrapper players;
        if (xmlFile.exists()) {
            // We have found an existing file with players so we load it
            players = unmarshallPlayers();
        } else {
            players = new PlayerListWrapper();
        }
        players.getPlayers()
            .add(player);

        // We save the new list as an xml
        marshallPlayers(players);

        return true;
    }

    @Override
    public Player getPlayer(String name) {
        PlayerListWrapper players = unmarshallPlayers();
        if (players != null) {
            return players.getPlayers()
                .stream()
                .filter(p -> p.getName()
                    .equals(name))
                .findAny()
                .orElse(null);
        }
        return null;
    }

    private PlayerListWrapper unmarshallPlayers() {
        JAXBContext jc;
        try {
            jc = JAXBContext.newInstance(PlayerListWrapper.class);
            StreamSource xml = new StreamSource(XML_FILE_NAME);
            Unmarshaller unmarshaller = jc.createUnmarshaller();
            JAXBElement<PlayerListWrapper> je = unmarshaller.unmarshal(xml, PlayerListWrapper.class);
            return je.getValue();
        } catch (JAXBException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private void marshallPlayers(PlayerListWrapper players) {
        JAXBContext jc;
        try {
            jc = JAXBContext.newInstance(PlayerListWrapper.class);
            JAXBElement<PlayerListWrapper> element = new JAXBElement<PlayerListWrapper>(new QName("player"), PlayerListWrapper.class, players);
            Marshaller marshaller = jc.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(element, new FileOutputStream(XML_FILE_NAME));
        } catch (JAXBException | FileNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

}
