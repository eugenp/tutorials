package com.example.hexagonalarchitecture.kitchenassistant.application.service;

import com.example.hexagonalarchitecture.kitchenassistant.adapter.out.persistence.entities.OrderEntryEntity;
import com.example.hexagonalarchitecture.kitchenassistant.application.port.in.OrderItemsPort;
import com.example.hexagonalarchitecture.kitchenassistant.application.port.in.dtos.StockRequest;
import com.example.hexagonalarchitecture.kitchenassistant.application.port.out.LoadUserPort;
import com.example.hexagonalarchitecture.kitchenassistant.application.port.out.OrderEntryPort;
import com.example.hexagonalarchitecture.kitchenassistant.domain.Order;
import com.example.hexagonalarchitecture.kitchenassistant.domain.User;
import com.example.hexagonalarchitecture.kitchenassistant.domain.Wallet;
import com.example.hexagonalarchitecture.utility.UseCase;
import lombok.RequiredArgsConstructor;

import java.util.stream.Collectors;

@RequiredArgsConstructor
@UseCase
public class OrderItemsPortService implements OrderItemsPort {

    private final LoadUserPort userPort;

    private final OrderEntryPort orderEntryPort;


    @Override
    public Long order(StockRequest request) {
        User user = userPort.loadSystemUser(request.getUserId());
        checkHasSufficientBalance(user.getWallet(), request);
        return orderEntryPort.createOrderEntry(user.getId(), request);
    }

    @Override
    public Order findById(Long id) {
        OrderEntryEntity order = orderEntryPort.findById(id);
        return new Order(order.getCost(),
                order.getNote(),
                order.getItems().stream().map(OrderEntryEntity.OrderItem::getName)
                        .collect(Collectors.toList()));
    }

    void checkHasSufficientBalance(Wallet wallet, StockRequest request) {
        if (!wallet.balanceCanBuy(request.getCost())) {
            throw new InsufficientFundsException();
        }
    }
}
