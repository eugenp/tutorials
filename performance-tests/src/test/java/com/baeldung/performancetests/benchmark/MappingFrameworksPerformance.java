package com.baeldung.performancetests.benchmark;

import com.baeldung.performancetests.dozer.DozerConverter;
import com.baeldung.performancetests.jmapper.JMapperConverter;
import com.baeldung.performancetests.mapstruct.MapStructConverter;
import com.baeldung.performancetests.model.destination.DestinationCode;
import com.baeldung.performancetests.model.source.*;
import com.baeldung.performancetests.model.destination.Order;
import com.baeldung.performancetests.modelmapper.ModelMapperConverter;
import com.baeldung.performancetests.orika.OrikaConverter;
import org.junit.Assert;
import org.junit.Test;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.RunnerException;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

@State(Scope.Group)
public class MappingFrameworksPerformance {
    SourceOrder sourceOrder = null;
    SourceCode sourceCode =  null;
    @Setup
    public void setUp() {
        User user = new User("John", "John@doe.com", AccountStatus.ACTIVE);
        RefundPolicy refundPolicy = new RefundPolicy(true, 30, Collections.singletonList("Refundable only if not used!"));

        Product product = new Product(BigDecimal.valueOf(10.99),
          100,
          "Sample Product",
          "Sample Product to be sold",
          true,
          refundPolicy
        );

        Discount discount = new Discount(Instant.now().toString(), Instant.now().toString(), BigDecimal.valueOf(5.99));
        Address deliveryAddress = new Address("Washington Street 5", "New York", "55045", "USA");
        DeliveryData deliveryData = new DeliveryData(deliveryAddress, true, "", 10);
        Address shopAddress = new Address("Roosvelt Street 9", "Boston", "55042", "USA");
        User reviewingUser = new User("John", "Johhny@John.com", AccountStatus.ACTIVE);
        User negativeReviewingUser = new User("Carl", "Carl@Coral.com", AccountStatus.ACTIVE);
        Review review = new Review(5, 5, 5, reviewingUser, "The best shop I've ever bought things in");

        Review negativeReview = new Review(1, 1, 1, negativeReviewingUser, "I will never buy anything again here!");

        List<Review> reviewList = new ArrayList<>();
        reviewList.add(review);
        reviewList.add(negativeReview);
        Shop shop = new Shop("Super Shop", shopAddress,"www.super-shop.com",reviewList);

        sourceOrder = new SourceOrder(OrderStatus.CONFIRMED,
          Instant.now().toString(),
          Instant.MAX.toString(),
          PaymentType.TRANSFER,
          discount,
          deliveryData,
          user,
          Collections.singletonList(product),
          shop,
          1
        );

        sourceCode = new SourceCode("This is source code!");
    }


    public void main(String[] args) throws IOException, RunnerException {
        org.openjdk.jmh.Main.main(args);
    }


    @Benchmark
    @Group("realLifeTest")
    @Fork(value = 1, warmups = 1)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @BenchmarkMode(Mode.All)
    public void orikaMapperRealLifeBenchmark() {
        OrikaConverter orikaConverter = new OrikaConverter();
        Order mappedOrder = orikaConverter.convert(sourceOrder);
        Assert.assertEquals(mappedOrder, sourceOrder);

    }

    @Benchmark
    @Group("realLifeTest")
    @Fork(value = 1, warmups = 1)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @BenchmarkMode(Mode.All)
    public void jmapperRealLifeBenchmark() {
        JMapperConverter jmapperConverter = new JMapperConverter();
        Order mappedOrder = jmapperConverter.convert(sourceOrder);
        Assert.assertEquals(mappedOrder, sourceOrder);
    }

    @Benchmark
    @Group("realLifeTest")
    @Fork(value = 1, warmups = 1)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @BenchmarkMode(Mode.All)
    public void modelMapperRealLifeBenchmark() {
        ModelMapperConverter modelMapperConverter = new ModelMapperConverter();
        Order mappedOrder = modelMapperConverter.convert(sourceOrder);
        Assert.assertEquals(mappedOrder, sourceOrder);
    }


    @Benchmark
    @Group("realLifeTest")
    @Fork(value = 1, warmups = 1)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @BenchmarkMode(Mode.All)
    public void dozerMapperRealLifeBenchmark() {
        DozerConverter dozerConverter = new DozerConverter();
        Order mappedOrder = dozerConverter.convert(sourceOrder);
        Assert.assertEquals(mappedOrder, sourceOrder);

    }

    @Benchmark
    @Group("realLifeTest")
    @Fork(value = 1, warmups = 1)
    @BenchmarkMode(Mode.All)
    public void mapStructRealLifeMapperBenchmark() {
        MapStructConverter converter = MapStructConverter.MAPPER;
        Order mappedOrder = converter.convert(sourceOrder);
        Assert.assertEquals(mappedOrder, sourceOrder);
    }

    @Benchmark
    @Group("simpleTest")
    @Fork(value = 1, warmups = 1)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @BenchmarkMode(Mode.All)
    public void orikaMapperSimpleBenchmark() {
        OrikaConverter orikaConverter = new OrikaConverter();
        DestinationCode mappedCode = orikaConverter.convert(sourceCode);
        Assert.assertEquals(mappedCode.getCode(), sourceCode.getCode());

    }

    @Benchmark
    @Group("simpleTest")
    @Fork(value = 1, warmups = 1)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @BenchmarkMode(Mode.All)
    public void jmapperSimpleBenchmark() {
        JMapperConverter jmapperConverter = new JMapperConverter();
        DestinationCode mappedCode = jmapperConverter.convert(sourceCode);
        Assert.assertEquals(mappedCode.getCode(), sourceCode.getCode());
    }

    @Benchmark
    @Group("simpleTest")
    @Fork(value = 1, warmups = 1)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @BenchmarkMode(Mode.All)
    public void modelMapperBenchmark() {
        ModelMapperConverter modelMapperConverter = new ModelMapperConverter();
        DestinationCode mappedCode = modelMapperConverter.convert(sourceCode);
        Assert.assertEquals(mappedCode.getCode(), sourceCode.getCode());
    }


    @Benchmark
    @Group("simpleTest")
    @Fork(value = 1, warmups = 1)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @BenchmarkMode(Mode.All)
    public void dozerMapperSimpleBenchmark() {
        DozerConverter dozerConverter = new DozerConverter();
        Order mappedOrder = dozerConverter.convert(sourceOrder);
        Assert.assertEquals(mappedOrder, sourceOrder);

    }

    @Benchmark
    @Group("simpleTest")
    @Fork(value = 1, warmups = 1)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @BenchmarkMode(Mode.All)
    public void mapStructMapperSimpleBenchmark() {
        MapStructConverter converter = MapStructConverter.MAPPER;
        DestinationCode mappedCode = converter.convert(sourceCode);
        Assert.assertEquals(mappedCode.getCode(), sourceCode.getCode());
    }


}
