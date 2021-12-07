package com.baeldung.patterns.hexagonal.architecture.data;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;

import com.baeldung.patterns.hexagonal.architecture.domain.TradeOrder;

public interface TradeOrderRepository extends JpaRepository<TradeOrder, Serializable> {
}
