package com.baeldung.lambda.shipping;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.EAGER;

@Entity(name = "consignment")
@Table(name = "consignment")
public class Consignment {
    private String id;
    private String source;
    private String destination;
    private boolean isDelivered;
    private List<Item> items = new ArrayList<>();
    private List<Checkin> checkins = new ArrayList<>();

    @Id
    @Column(name = "consignment_id")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Column(name = "source")
    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    @Column(name = "destination")
    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    @Column(name = "delivered", columnDefinition = "boolean")
    public boolean isDelivered() {
        return isDelivered;
    }

    public void setDelivered(boolean delivered) {
        isDelivered = delivered;
    }

    @ElementCollection(fetch = EAGER)
    @CollectionTable(name = "consignment_item", joinColumns = @JoinColumn(name = "consignment_id"))
    @OrderColumn(name = "item_index")
    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    @ElementCollection(fetch = EAGER)
    @CollectionTable(name = "consignment_checkin", joinColumns = @JoinColumn(name = "consignment_id"))
    @OrderColumn(name = "checkin_index")
    public List<Checkin> getCheckins() {
        return checkins;
    }

    public void setCheckins(List<Checkin> checkins) {
        this.checkins = checkins;
    }
}
