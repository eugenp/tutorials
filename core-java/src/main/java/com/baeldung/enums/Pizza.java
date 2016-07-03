package com.baeldung.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.collections15.Predicate;

import java.io.IOException;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.List;
import java.util.stream.Collectors;

public class Pizza {

    private static EnumSet<PizzaStatus> undeliveredPizzaStatuses =
            EnumSet.of(PizzaStatus.ORDERED, PizzaStatus.READY);

    private PizzaStatus status;

    @JsonFormat(shape = JsonFormat.Shape.OBJECT)
    public enum PizzaStatus {
        ORDERED(5) {
            @Override
            public boolean isOrdered() {
                return true;
            }
        },
        READY(2) {
            @Override
            public boolean isReady() {
                return true;
            }
        },
        DELIVERED(0) {
            @Override
            public boolean isDelivered() {
                return true;
            }
        };

        private int timeToDelivery;

        public boolean isOrdered() {
            return false;
        }

        public boolean isReady() {
            return false;
        }

        public boolean isDelivered() {
            return false;
        }

        @JsonProperty("timeToDelivery")
        public int getTimeToDelivery() {
            return timeToDelivery;
        }

        PizzaStatus(int timeToDelivery) {
            this.timeToDelivery = timeToDelivery;
        }
    }

    public PizzaStatus getStatus() {
        return status;
    }

    public void setStatus(PizzaStatus status) {
        this.status = status;
    }

    public boolean isDeliverable() {
        return this.status.isReady();
    }

    public void printTimeToDeliver() {
        System.out.println("Time to delivery is " + this.getStatus().getTimeToDelivery() + " days");
    }

    public static List<Pizza> getAllUndeliveredPizzas(List<Pizza> input) {
        return input.stream().filter(
                (s) -> undeliveredPizzaStatuses.contains(s.getStatus()))
                .collect(Collectors.toList());
    }

    public static EnumMap<PizzaStatus, List<Pizza>>
    groupPizzaByStatus(List<Pizza> pzList) {
        return pzList.stream().collect(
                Collectors.groupingBy(Pizza::getStatus,
                        () -> new EnumMap<>(PizzaStatus.class), Collectors.toList()));
    }

    public void deliver() {
        if (isDeliverable()) {
            PizzaDeliverySystemConfiguration.getInstance().getDeliveryStrategy().deliver(this);
            this.setStatus(PizzaStatus.DELIVERED);
        }
    }

    public static String getJsonString(Pizza pz) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(pz);
    }

    private static Predicate<Pizza> thatAreNotDelivered() {
        return entry -> undeliveredPizzaStatuses.contains(entry.getStatus());
    }
}