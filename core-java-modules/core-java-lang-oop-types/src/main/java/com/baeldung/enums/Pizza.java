package com.baeldung.enums;

import java.util.EnumMap;
import java.util.EnumSet;
import java.util.List;
import java.util.stream.Collectors;

public class Pizza {

    private static EnumSet<PizzaStatusEnum> deliveredPizzaStatuses = EnumSet.of(PizzaStatusEnum.DELIVERED);

    private PizzaStatusEnum status;

    public enum PizzaStatusEnum {
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

        public int getTimeToDelivery() {
            return timeToDelivery;
        }

        PizzaStatusEnum(int timeToDelivery) {
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

    public static List<Pizza> getAllUndeliveredPizzas(List<Pizza> input) {
        return input.stream().filter((s) -> !deliveredPizzaStatuses.contains(s.getStatus())).collect(Collectors.toList());
    }

    public static EnumMap<PizzaStatusEnum, List<Pizza>> groupPizzaByStatus(List<Pizza> pzList) {
        return pzList.stream().collect(Collectors.groupingBy(Pizza::getStatus, () -> new EnumMap<>(PizzaStatusEnum.class), Collectors.toList()));
    }

    public void deliver() {
        if (isDeliverable()) {
            PizzaDeliverySystemConfiguration.getInstance().getDeliveryStrategy().deliver(this);
            this.setStatus(PizzaStatusEnum.DELIVERED);
        }
    }
    
    public int getDeliveryTimeInDays() {
        switch (status) {
            case ORDERED: return 5;
            case READY: return 2;
            case DELIVERED: return 0;
        }
        return 0;
    }

}