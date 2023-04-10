package com.baeldung.sbe;

import java.math.BigDecimal;
import java.nio.ByteBuffer;

import org.agrona.concurrent.UnsafeBuffer;

import com.baeldung.sbe.stub.MessageHeaderDecoder;
import com.baeldung.sbe.stub.MessageHeaderEncoder;
import com.baeldung.sbe.stub.TradeDataDecoder;
import com.baeldung.sbe.stub.TradeDataEncoder;

public class MarketDataUtil {

    public static int encodeAndWrite(ByteBuffer buffer, MarketData marketData) {

        final int pos = buffer.position();

        final UnsafeBuffer directBuffer = new UnsafeBuffer(buffer);
        final MessageHeaderEncoder headerEncoder = new MessageHeaderEncoder();
        final TradeDataEncoder dataEncoder = new TradeDataEncoder();

        final BigDecimal priceDecimal = BigDecimal.valueOf(marketData.getPrice());
        final int priceMantis = priceDecimal.scaleByPowerOfTen(priceDecimal.scale())
          .intValue();
        final int priceExponent = priceDecimal.scale() * -1;

        final TradeDataEncoder encoder = dataEncoder.wrapAndApplyHeader(directBuffer, pos, headerEncoder);
        encoder.amount(marketData.getAmount());
        encoder.quote()
          .market(marketData.getMarket())
          .currency(marketData.getCurrency())
          .symbol(marketData.getSymbol())
          .price()
          .mantissa(priceMantis)
          .exponent((byte) priceExponent);

        // set position
        final int encodedLength = headerEncoder.encodedLength() + encoder.encodedLength();
        buffer.position(pos + encodedLength);
        return encodedLength;
    }

    public static MarketData readAndDecode(ByteBuffer buffer) {

        final int pos = buffer.position();

        final UnsafeBuffer directBuffer = new UnsafeBuffer(buffer);
        final MessageHeaderDecoder headerDecoder = new MessageHeaderDecoder();
        final TradeDataDecoder dataDecoder = new TradeDataDecoder();

        dataDecoder.wrapAndApplyHeader(directBuffer, pos, headerDecoder);

        // set position
        final int encodedLength = headerDecoder.encodedLength() + dataDecoder.encodedLength();
        buffer.position(pos + encodedLength);

        final double price = BigDecimal.valueOf(dataDecoder.quote()
            .price()
            .mantissa())
          .scaleByPowerOfTen(dataDecoder.quote()
            .price()
            .exponent())
          .doubleValue();

        return MarketData.builder()
          .amount(dataDecoder.amount())
          .symbol(dataDecoder.quote()
            .symbol())
          .market(dataDecoder.quote()
            .market())
          .currency(dataDecoder.quote()
            .currency())
          .price(price)
          .build();
    }

}
