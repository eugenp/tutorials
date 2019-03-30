package com.baeldung.hexagonal.core;

import com.baeldung.hexagonal.adapters.ConsoleOrderNotification;
import com.baeldung.hexagonal.adapters.InMemoryOrderRepositoryAdapter;
import com.baeldung.hexagonal.ports.IOrderNotification;
import com.baeldung.hexagonal.ports.IOrderRepository;
import com.baeldung.hexagonal.ports.IOrderService;
import com.google.inject.AbstractModule;

public class OrderGuiceModule extends AbstractModule {
    @Override
    protected void configure() {
      bind(IOrderRepository.class).to(InMemoryOrderRepositoryAdapter.class);
      bind(IOrderNotification.class).to(ConsoleOrderNotification.class);
      bind(IOrderService.class).to(OrderService.class);
  }
}
