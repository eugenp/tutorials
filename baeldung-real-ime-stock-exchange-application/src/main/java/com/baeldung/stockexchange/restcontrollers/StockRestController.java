package com.baeldung.stockexchange.restcontrollers;

import com.baeldung.stockexchange.models.PriceChange;
import com.baeldung.stockexchange.models.Stock;
import com.baeldung.stockexchange.repository.StockRepository;
import com.baeldung.stockexchange.services.StockService;
import java.time.Duration;
import java.util.Date;
import java.util.stream.Stream;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

@RestController
@RequestMapping("/stock")
public class StockRestController
{
	@Autowired
	private StockService stockService;

	@Autowired
	private StockRepository stockRepository;

	@GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<Stock> getAllStocks()
	{
		return stockRepository.findAll().delayElements(Duration.ofSeconds(1));
	}

	@GetMapping("/{stockId}/quote")
	public Mono<Stock> getStock(@PathVariable String stockId)
	{
		return stockService.getStock(stockId);
	}

	@PutMapping("/{stockId}")
	public Mono<ResponseEntity<Stock>> updateStock(@PathVariable String stockId, @Valid @RequestBody Stock stock)
	{
		return stockService.getStock(stockId)
				.flatMap(oldStock -> {
					oldStock.setStockPrice(stock.getStockPrice());
					return stockService.updateStock(oldStock);
				})
				.map(updatedStock -> new ResponseEntity<>(updatedStock, HttpStatus.OK))
				.defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	@PostMapping
	public Mono<Stock> createStock(@RequestBody Stock stock)
	{
		return stockService.createStock(stock);
	}

	@DeleteMapping("/{stockId}")
	public Mono<ResponseEntity<Void>> deleteStock(@PathVariable String stockId)
	{
		return stockService.getStock(stockId)
				.flatMap(oldStock -> stockService.deleteStock(oldStock.getStockId())
				.then(Mono.just(new ResponseEntity<Void>(HttpStatus.OK))))
				.defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	@GetMapping(value = "/{stockId}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<PriceChange> getStockStream(@PathVariable String stockId)
	{
		return stockService.getStock(stockId).flatMapMany(stock -> {
			Flux<PriceChange> priceChange = Flux.fromStream(
					Stream.generate(() -> new PriceChange(stock, new Date())));

			return Flux.zip(Flux.interval(Duration.ofSeconds(1)), priceChange).map(Tuple2::getT2);
		});
	}
}
