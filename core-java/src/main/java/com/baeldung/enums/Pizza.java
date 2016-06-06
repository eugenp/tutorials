package com.baeldung.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.collections15.CollectionUtils;
import org.apache.commons.collections15.Predicate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.List;

public class Pizza {

    private static EnumSet<PizzaStatusEnum> undeliveredPizzaStatuses =
      EnumSet.of(PizzaStatusEnum.ORDERED, PizzaStatusEnum.READY);

    private PizzaStatusEnum status;

    @JsonFormat(shape = JsonFormat.Shape.OBJECT)
    public enum PizzaStatusEnum {
        ORDERED (5){
            @Override
            public boolean isOrdered() {
                return true;
            }
        },
        READY (2){
            @Override
            public boolean isReady() {
                return true;
            }
        },
        DELIVERED (0){
            @Override
            public boolean isDelivered() {
                return true;
            }
        };

        private int timeToDelivery;

        public boolean isOrdered() {return false;}

        public boolean isReady() {return false;}

        public boolean isDelivered(){return  false;}
        @JsonProperty("timeToDelivery")
        public int getTimeToDelivery() {
            return timeToDelivery;
        }

        private PizzaStatusEnum (int timeToDelivery) {
            this.timeToDelivery = timeToDelivery;
        }
    }

    public PizzaStatusEnum getStatus() {
        return status;
    }

    public void setStatus(PizzaStatusEnum status) {
        this.status = status;
    }

    public boolean isDeliverable() {
        return this.status.isReady();
    }

    public void printTimeToDeliver() {
        System.out.println("Time to delivery is " + this.getStatus().getTimeToDelivery() + " days");
    }

    public static List<Pizza> getAllUndeliveredPizza(List<Pizza> input) {
        List<Pizza> undelivered = input;
        CollectionUtils.filter(undelivered, thatAreNotDelivered());
        return undelivered;
    }

    public static EnumMap<PizzaStatusEnum, List<Pizza>> groupPizzaByStatus(List<Pizza> pizzaList) {
        EnumMap<PizzaStatusEnum, List<Pizza>> pzByStatus = new EnumMap<PizzaStatusEnum, List<Pizza>>(PizzaStatusEnum.class);
        for (Pizza pz : pizzaList) {
            PizzaStatusEnum status = pz.getStatus();

            if (pzByStatus.containsKey(status)) {
                pzByStatus.get(status).add(pz);
            } else {
                List<Pizza> newPzList = new ArrayList<Pizza>();
                newPzList.add(pz);
                pzByStatus.put(status, newPzList);
            }
        }
        return pzByStatus;
    }

    public void deliver() {
        if (isDeliverable()) {
            PizzaDeliverySystemConfiguration.getInstance().getDeliveryStrategy().deliver(this);
            this.setStatus(PizzaStatusEnum.DELIVERED);
        }
    }

    public static String getJsonString(Pizza pz) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(pz);
    }

    private static Predicate<Pizza> thatAreNotDelivered() {
        return new Predicate<Pizza>() {
            public boolean evaluate(Pizza entry) {
                return undeliveredPizzaStatuses.contains(entry.getStatus());
            }
        };
    }
}