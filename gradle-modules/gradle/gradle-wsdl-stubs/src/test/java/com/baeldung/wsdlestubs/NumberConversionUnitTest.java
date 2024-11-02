package com.baeldung.wsdlestubs;

import com.dataaccess.webservicesserver.NumberConversion;
import com.dataaccess.webservicesserver.NumberConversionSoapType;
import org.junit.jupiter.api.Test;
import java.math.BigInteger;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class NumberConversionUnitTest {

    @Test
    public void givenNumberConversionService_whenConvertingNumberToWords_thenReturnCorrectWords() {
        NumberConversion service = new NumberConversion();
        NumberConversionSoapType port = service.getNumberConversionSoap();

        String numberInWords = port.numberToWords(BigInteger.valueOf(10000000));
        assertEquals("ten million", numberInWords);
    }
}
