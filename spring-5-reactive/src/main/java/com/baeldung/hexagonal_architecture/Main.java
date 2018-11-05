package com.baeldung.hexagonal_architecture;

public class Main {
    public static void main(String[] args) {
        logicFromFile();
        logicFromDb();
        logicFromDbUnchangeable();
    }

    /* Shows how easy it is to switch implementations. We can develop new storage services later.*/
    public static void logicFromFile() {
        System.out.println("************ Getting data from file ************ ");
        MyLogic myLogic = new MyLogic(new FileAmountService("hexagonal_architecture/city-to-amount-map.json"));

        System.out.println("Adding 5 units to Madrid: " + myLogic.addToAmount(5, "madrid"));
        System.out.println("Adding 2 units to Berlin: " + myLogic.addToAmount(2, "berlin"));
    }

    public static void logicFromDb() {
        System.out.println("************ Getting data from database ************ ");
        MyLogic myLogic = new MyLogic(new DbAmountService());

        System.out.println("Adding 5 units to Madrid: " + myLogic.addToAmount(5, "madrid"));
        System.out.println("Adding 2 units to Berlin: " + myLogic.addToAmount(2, "berlin"));
    }

    /**************************************************/

    /* The store media cannot be change. */
    public static void logicFromDbUnchangeable() {
        System.out.println("************ Getting data from database, not changeable ************ ");
        MyDbLogic myLogic = new MyDbLogic();

        System.out.println("Adding 5 units to Madrid: " + myLogic.addToAmount(5, "madrid"));
        System.out.println("Adding 2 units to Berlin: " + myLogic.addToAmount(2, "berlin"));
    }
}