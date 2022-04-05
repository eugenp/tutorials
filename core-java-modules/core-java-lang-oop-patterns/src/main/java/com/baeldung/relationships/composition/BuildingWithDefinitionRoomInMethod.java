package com.baeldung.relationships.composition;

public class BuildingWithDefinitionRoomInMethod {

    public Room createAnonymousRoom() {
        return new Room() {
            @Override
            public void doInRoom() {}
        };
    }

    public Room createInlineRoom() {
        class InlineRoom implements Room {
            @Override
            public void doInRoom() {}
        }
        return new InlineRoom();
    }
    
    public Room createLambdaRoom() {
        return () -> {};
    }

    public interface Room {
        void doInRoom();
    }

}
