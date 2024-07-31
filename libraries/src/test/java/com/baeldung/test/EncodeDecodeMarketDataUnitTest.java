package com.baeldung.test;

import java.math.BigDecimal;
import java.nio.ByteBuffer;

import org.agrona.concurrent.UnsafeBuffer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.baeldung.sbe.MarketData;
import com.baeldung.sbe.stub.Currency;
import com.baeldung.sbe.stub.Market;
import com.baeldung.sbe.stub.MessageHeaderDecoder;
import com.baeldung.sbe.stub.MessageHeaderEncoder;
import com.baeldung.sbe.stub.TradeDataDecoder;
import com.baeldung.sbe.stub.TradeDataEncoder;

public class EncodeDecodeMarketDataUnitTest {

    private MarketData marketData;

    @BeforeEach
    public void setup() {
        marketData = new MarketData(2, 128.99, Market.NYSE, Currency.USD, "IBM");
    }

    @Test
    public void givenMarketData_whenEncode_thenDecodedValuesMatch() {
        // our buffer to write encoded data, initial cap. 128 bytes
        UnsafeBuffer buffer = new UnsafeBuffer(ByteBuffer.allocate(128));
        // necessary encoders
        MessageHeaderEncoder headerEncoder = new MessageHeaderEncoder();
        TradeDataEncoder dataEncoder = new TradeDataEncoder();
        // we parse price data (double) into two parts: mantis and exponent
        BigDecimal priceDecimal = BigDecimal.valueOf(marketData.getPrice());
        int priceMantissa = priceDecimal.scaleByPowerOfTen(priceDecimal.scale())
          .intValue();
        int priceExponent = priceDecimal.scale() * -1;
        // encode data
        TradeDataEncoder encoder = dataEncoder.wrapAndApplyHeader(buffer, 0, headerEncoder);
        encoder.amount(marketData.getAmount());
        encoder.quote()
          .market(marketData.getMarket())
          .currency(marketData.getCurrency())
          .symbol(marketData.getSymbol())
          .price()
          .mantissa(priceMantissa)
          .exponent((byte) priceExponent);

        // necessary decoders
        MessageHeaderDecoder headerDecoder = new MessageHeaderDecoder();
        TradeDataDecoder dataDecoder = new TradeDataDecoder();
        // decode data
        dataDecoder.wrapAndApplyHeader(buffer, 0, headerDecoder);
        // decode price data (from mantissa and exponent) into a double
        double price = BigDecimal.valueOf(dataDecoder.quote()
            .price()
            .mantissa())
          .scaleByPowerOfTen(dataDecoder.quote()
            .price()
            .exponent())
          .doubleValue();
        // ensure we have the exact same values
        Assertions.assertEquals(2, dataDecoder.amount());
        Assertions.assertEquals("IBM", dataDecoder.quote()
          .symbol());
        Assertions.assertEquals(Market.NYSE, dataDecoder.quote()
          .market());
        Assertions.assertEquals(Currency.USD, dataDecoder.quote()
          .currency());
        Assertions.assertEquals(128.99, price);
    }

}
