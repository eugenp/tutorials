package com.baeldung.dddhexagonalsimple.adapters.rest;

import com.baeldung.dddhexagonalsimple.domain.model.PizzaOrder;
import com.baeldung.dddhexagonalsimple.domain.ports.inbound.ProcessPizzaOrderUseCase;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.baeldung.dddhexagonalsimple.domain.model.Pizza;
import com.baeldung.dddhexagonalsimple.domain.ports.inbound.CreatePizzaUseCase;
import com.baeldung.dddhexagonalsimple.domain.ports.inbound.GetPizzaUseCase;

import java.net.URI;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping(path = "/pizza")
final class PizzaEndpoint {

    private final CreatePizzaUseCase createPizzaUseCase;
    private final GetPizzaUseCase getPizzaUseCase;
    private final ProcessPizzaOrderUseCase processPizzaOrderUseCase;

    @PostMapping(consumes = "application/json", produces = "application/json")
    ResponseEntity<PizzaResponse> create(@RequestBody PizzaRequest request) {
        Pizza created = createPizzaUseCase.create(request.getName(), request.getPricePerSquareInch());

        return ResponseEntity.created(URI.create(String.format("/pizza/%d", created.getId()))).body(PizzaResponse.fromDomain(created));
    }

    @GetMapping(path = "/{id}", produces = "application/json")
    ResponseEntity<?> get(@PathVariable Long id) {
        Optional<Pizza> foundOptional = getPizzaUseCase.get(id);

        if (foundOptional.isPresent()) {
            return ResponseEntity.ok().body(PizzaResponse.fromDomain(foundOptional.get()));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(path = "/orders", consumes = "application/json", produces = "application/json")
    ResponseEntity<?> process(@RequestBody PizzaOrderRequest request) {
        Optional<Pizza> pizzaOptional = getPizzaUseCase.get(request.getPizzaId());
        if (pizzaOptional.isPresent()) {
            PizzaOrder processed = processPizzaOrderUseCase.process(pizzaOptional.get(), request.getDiameterInInches());

            return ResponseEntity.created(URI.create(String.format("/pizza/orders/%d", processed.getId()))).body(PizzaOrderResponse.fromDomain(processed));
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
