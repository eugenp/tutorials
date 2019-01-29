package com.baeldung.enums;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author zn.wang
 */
public class Pizza {

    private static EnumSet<PizzaStatusEnum> deliveredPizzaStatuses = EnumSet.of(PizzaStatusEnum.DELIVERED);

    private PizzaStatusEnum status;

    public enum PizzaStatusEnum {
        /**
         * 订购
         */
        ORDERED(5) {
            @Override
            public boolean isOrdered() {
                return true;
            }
        },
        /**
         * 准备
         */
        READY(2) {
            @Override
            public boolean isReady() {
                return true;
            }
        },
        /**
         * 分派
         */
        DELIVERED(0) {
            @Override
            public boolean isDelivered() {
                return true;
            }
        };

        private int timeToDelivery;

        public boolean isReady() {
            return false;
        }

        public boolean isOrdered() {
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

    /**
     * 是可交付的成果
     * @return
     */
    public boolean isDeliverable() {
        return this.status.isReady();
    }

    public void printTimeToDeliver() {
        System.out.println("Time to delivery is " + this.getStatus().getTimeToDelivery() + " days");
    }

    /**
     * 获取所有没送出去的披萨
     * @param input
     * @return
     */
    public static List<Pizza> getAllUndeliveredPizzas(List<Pizza> input) {
        List<Pizza> list = new ArrayList<>();
        for (Pizza s : input) {
            if (!deliveredPizzaStatuses.contains(s.getStatus())) {
                list.add(s);
            }
        }
        return list;
    }

    public static EnumMap<PizzaStatusEnum, List<Pizza>> groupPizzaByStatus(List<Pizza> pzList) {
        EnumMap<PizzaStatusEnum, List<Pizza>> map = new EnumMap<>(PizzaStatusEnum.class);
        for (Pizza pizza : pzList) {
            map.computeIfAbsent(pizza.getStatus(), new Function<PizzaStatusEnum, List<Pizza>>() {
                @Override
                public List<Pizza> apply(PizzaStatusEnum k) {
                    return new ArrayList<>();
                }
            }).add(pizza);
        }
        return map;
    }

    public void deliver() {
        if (isDeliverable()) {
            PizzaDeliverySystemConfiguration.getInstance().getDeliveryStrategy().deliver(this);
            this.setStatus(PizzaStatusEnum.DELIVERED);
        }
    }

}