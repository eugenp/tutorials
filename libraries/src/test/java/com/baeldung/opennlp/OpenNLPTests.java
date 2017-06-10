package com.baeldung.opennlp;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.junit.Test;

import opennlp.tools.chunker.ChunkerME;
import opennlp.tools.chunker.ChunkerModel;
import opennlp.tools.cmdline.postag.POSModelLoader;
import opennlp.tools.doccat.DoccatFactory;
import opennlp.tools.doccat.DoccatModel;
import opennlp.tools.doccat.DocumentCategorizerME;
import opennlp.tools.doccat.DocumentSample;
import opennlp.tools.doccat.DocumentSampleStream;
import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSSample;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;
import opennlp.tools.tokenize.WhitespaceTokenizer;
import opennlp.tools.util.InputStreamFactory;
import opennlp.tools.util.ObjectStream;
import opennlp.tools.util.PlainTextByLineStream;
import opennlp.tools.util.Span;
import opennlp.tools.util.TrainingParameters;

public class OpenNLPTests {

    private final static String text = "To get to the south: Go to the store. Buy a compass. Use the compass. Then walk to the south.";
    private final static String sentence[] = new String[] { "James", "Jordan", "live", "in", "Oklahoma", "city", "." };
    
    @Test
    public void givenText_WhenDetectSentences_ThenCountSentences(){
        InputStream is;
        SentenceModel model;
        try {
            is = new FileInputStream("OpenNLP/en-sent.bin");
            model = new SentenceModel(is);
            SentenceDetectorME sdetector = new SentenceDetectorME(model);
            String sentences[] = sdetector.sentDetect(text);
            assertEquals(4, sentences.length);
            is.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenText_WhenDetectTokens_ThenVerifyNames(){
        InputStream is;
        TokenNameFinderModel model;
        try {
            is = new FileInputStream("OpenNLP/en-ner-person.bin");
            model = new TokenNameFinderModel(is);
            is.close();
            NameFinderME nameFinder = new NameFinderME(model);
            Span nameSpans[] = nameFinder.find(sentence);
            String[] names = Span.spansToStrings(nameSpans, sentence);
            assertEquals(1, names.length);
            assertEquals("James Jordan", names[0]);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenText_WhenDetectTokens_ThenVerifyLocations(){
        InputStream is;
        TokenNameFinderModel model;
        try {
            is = new FileInputStream("OpenNLP/en-ner-location.bin");
            model = new TokenNameFinderModel(is);
            is.close();
            NameFinderME nameFinder = new NameFinderME(model);
            Span locationSpans[] = nameFinder.find(sentence);
            String[] locations = Span.spansToStrings(locationSpans, sentence);
            assertEquals(1, locations.length);
            assertEquals("Oklahoma", locations[0]);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @Test
    public void givenText_WhenCategorizeDocument_ThenVerifyDocumentContent(){
        DoccatModel docCatModel;
        try {
            InputStreamFactory isf = new InputStreamFactory() {
                public InputStream createInputStream() throws IOException {
                    return new FileInputStream("OpenNLP/doc-cat.train");
                }
            };
            ObjectStream<String> lineStream = new PlainTextByLineStream(isf, "UTF-8");
            ObjectStream<DocumentSample> sampleStream = new DocumentSampleStream(lineStream);
            DoccatFactory docCatFactory = new DoccatFactory();
            docCatModel = DocumentCategorizerME.train("en", sampleStream, TrainingParameters.defaultParams(), docCatFactory);
            DocumentCategorizerME myCategorizer = new DocumentCategorizerME(docCatModel);
            double[] outcomes = myCategorizer.categorize(sentence);
            String category = myCategorizer.getBestCategory(outcomes);
            assertEquals("GOOD", category);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenText_WhenTagDocument_ThenVerifyTaggedString(){
        try {
            POSModel posModel = new POSModelLoader().load(new File("OpenNLP/en-pos-maxent.bin"));
            POSTaggerME posTaggerME = new POSTaggerME(posModel);
            InputStreamFactory isf = new InputStreamFactory() {
                public InputStream createInputStream() throws IOException {
                    return new FileInputStream("OpenNLP/PartOfSpeechTag.txt");
                }
            };
            ObjectStream<String> lineStream = new PlainTextByLineStream(isf, "UTF-8");
            String line;
            while ((line = lineStream.read()) != null) {
                String whitespaceTokenizerLine[] = WhitespaceTokenizer.INSTANCE.tokenize(line);
                String[] tags = posTaggerME.tag(whitespaceTokenizerLine);
                POSSample posSample = new POSSample(whitespaceTokenizerLine, tags);
                assertEquals("Out_IN of_IN the_DT night_NN that_WDT covers_VBZ me_PRP", posSample.toString());
            }
            lineStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @Test
    public void givenText_WhenChunked_ThenCountChunks(){
        try {
            InputStream is = new FileInputStream("OpenNLP/en-chunker.bin");
            ChunkerModel cModel = new ChunkerModel(is);
            ChunkerME chunkerME = new ChunkerME(cModel);
            String pos[] = new String[] { "NNP", "NNP", "NNP", "POS", "NNP", "NN", "VBD"};
            String chunks[] = chunkerME.chunk(sentence, pos);
            assertEquals(7, chunks.length);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}
