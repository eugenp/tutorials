package domain;

public class Item {

    private int id;
    private String kind;
    private String description;

    public Item() {
        id = 00000;
        kind = "not yet";
        description = "not yet";
    }

    public Item(int id, String kind, String description) {
        this.id = id;
        this.kind = kind;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public String getKind() {
        return kind;
    }

    public String getDescription() {
        return description;
    }

}
