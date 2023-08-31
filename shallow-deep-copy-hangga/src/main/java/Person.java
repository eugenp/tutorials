public class Person implements Cloneable {
    private final String name;
    private final String[] friends;

    public Person(String name, String[] friends) {
        this.name = name;
        this.friends = friends;
    }
    public String getName() {
        return name;
    }

    public String[] getFriends() {
        return friends;
    }

    @Override
    public Person clone() {
        String[] copiedFriends = new String[friends.length];
        System.arraycopy(friends, 0, copiedFriends, 0, friends.length);
        return new Person(name, copiedFriends);
    }
}
