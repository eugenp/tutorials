package com.baeldung.encoding;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CodingErrorAction;
import java.nio.charset.MalformedInputException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class CharacterEncodingExamplesUnitTest {

    @Test
    public void givenTextFile_whenCalledWithEncodingASCII_thenProduceIncorrectResult() throws IOException {
        Assert.assertEquals(
          CharacterEncodingExamples.readFile(
            "src/test/resources/encoding.txt", "US-ASCII"), 
            "The fa��ade pattern is a software-design pattern commonly used with object-oriented programming.");
    }

    @Test
    public void givenTextFile_whenCalledWithEncodingUTF8_thenProduceCorrectResult() throws IOException {
        Assert.assertEquals(
          CharacterEncodingExamples.readFile(
            "src/test/resources/encoding.txt", "UTF-8"), 
            "The façade pattern is a software-design pattern commonly used with object-oriented programming.");
    }

    @Test
    public void givenCharacterA_whenConvertedtoBinaryWithEncodingASCII_thenProduceResult() throws IOException {
        Assert.assertEquals(
          CharacterEncodingExamples.convertToBinary("A", "US-ASCII"), 
          "1000001 ");
    }

    @Test
    public void givenCharacterA_whenConvertedtoBinaryWithEncodingUTF8_thenProduceResult() throws IOException {
        Assert.assertEquals(
          CharacterEncodingExamples.convertToBinary("A", "UTF-8"), 
          "1000001 ");
    }

    @Test
    public void givenCharacterCh_whenConvertedtoBinaryWithEncodingBig5_thenProduceResult() throws IOException {
        Assert.assertEquals(
          CharacterEncodingExamples.convertToBinary("語", "Big5"), 
          "10111011 1111001 ");
    }

    @Test
    public void givenCharacterCh_whenConvertedtoBinaryWithEncodingUTF8_thenProduceResult() throws IOException {
        Assert.assertEquals(
          CharacterEncodingExamples.convertToBinary("語", "UTF-8"), 
          "11101000 10101010 10011110 ");
    }

    @Test
    public void givenCharacterCh_whenConvertedtoBinaryWithEncodingUTF32_thenProduceResult() throws IOException {
        Assert.assertEquals(
          CharacterEncodingExamples.convertToBinary("語", "UTF-32"), 
          "0 0 10001010 10011110 ");
    }

    @Test
    public void givenUTF8String_whenDecodeByUS_ASCII_thenIgnoreMalformedInputSequence() throws IOException {
        Assertions.assertEquals("The faade pattern is a software design pattern.", CharacterEncodingExamples.decodeText("The façade pattern is a software design pattern.", StandardCharsets.US_ASCII, CodingErrorAction.IGNORE));
    }

    //@Test
    // run this manually as it's dependent on platform encoding, which has to be UTF-8
    public void givenUTF8String_whenDecodeByUS_ASCII_thenReplaceMalformedInputSequence() throws IOException {
        Assertions.assertEquals(
          "The fa��ade pattern is a software design pattern.",
          CharacterEncodingExamples.decodeText(
            "The façade pattern is a software design pattern.",
            StandardCharsets.US_ASCII,
            CodingErrorAction.REPLACE));
    }

    @Test
    public void givenUTF8String_whenDecodeByUS_ASCII_thenReportMalformedInputSequence() {
        Assertions.assertThrows(
          MalformedInputException.class,
          () -> CharacterEncodingExamples.decodeText(
            "The façade pattern is a software design pattern.",
            StandardCharsets.US_ASCII,
            CodingErrorAction.REPORT));
    }

    @Test
    public void givenTextFile_whenLoopOverAllCandidateEncodings_thenProduceSuitableCandidateEncodings() {
        Path path = Paths.get("src/test/resources/encoding.txt");
        List<Charset> allCandidateCharSets = Arrays.asList(
          StandardCharsets.US_ASCII, StandardCharsets.UTF_8, StandardCharsets.ISO_8859_1);

        List<Charset> suitableCharsets = new ArrayList<>();
        allCandidateCharSets.forEach(charset -> {
            try {
                CharsetDecoder charsetDecoder = charset.newDecoder().onMalformedInput(CodingErrorAction.REPORT);
                Reader reader = new InputStreamReader(Files.newInputStream(path), charsetDecoder);
                BufferedReader bufferedReader = new BufferedReader(reader);
                bufferedReader.readLine();
                suitableCharsets.add(charset);
            } catch (MalformedInputException ignored) {
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        Assertions.assertEquals(suitableCharsets, Arrays.asList(StandardCharsets.UTF_8, StandardCharsets.ISO_8859_1));
    }

}
