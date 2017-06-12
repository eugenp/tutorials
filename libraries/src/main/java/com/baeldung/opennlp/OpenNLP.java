package com.baeldung.opennlp;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.logging.Logger;

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
import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import opennlp.tools.tokenize.WhitespaceTokenizer;
import opennlp.tools.util.InputStreamFactory;
import opennlp.tools.util.InvalidFormatException;
import opennlp.tools.util.ObjectStream;
import opennlp.tools.util.PlainTextByLineStream;
import opennlp.tools.util.Span;
import opennlp.tools.util.TrainingParameters;

public class OpenNLP {

    private final static Logger LOGGER = Logger.getLogger(OpenNLP.class.getName());
    private final static String text = buildString();
    private final static String sentence[] = new String[] { "James", "Jordan", "live", "in", "Oklahoma", "city", "." };

    private DoccatModel docCatModel;

    public static void main(String[] args) {
        new OpenNLP();
    }

    public static String buildString(){
        StringBuilder sb = new StringBuilder();
        sb.append("To get to the south:");
        sb.append(" Go to the store.");
        sb.append(" Buy a compass.");
        sb.append(" Use the compass.");
        sb.append(" Then walk to the south.");
        return sb.toString();
    }
    
    public OpenNLP() {
        try {
            sentenceDetector();
            tokenizer();
            nameFinder();
            locationFinder();
            trainDocumentCategorizer();
            documentCategorizer();
            partOfSpeechTagger();
            chunker();
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sentenceDetector() throws InvalidFormatException, IOException {

        InputStream is = new FileInputStream("OpenNLP/en-sent.bin");
        SentenceModel model = new SentenceModel(is);
        SentenceDetectorME sdetector = new SentenceDetectorME(model);
        String sentences[] = sdetector.sentDetect(text);
        for (String sentence : sentences) {
            LOGGER.info(sentence);
        }
        is.close();
    }

    public void tokenizer() throws InvalidFormatException, IOException {
        InputStream is = new FileInputStream("OpenNLP/en-token.bin");
        TokenizerModel model = new TokenizerModel(is);
        Tokenizer tokenizer = new TokenizerME(model);
        String tokens[] = tokenizer.tokenize(text);
        for (String token : tokens) {
            LOGGER.info(token);
        }
        is.close();
    }

    public static void nameFinder() throws IOException {
        InputStream is = new FileInputStream("OpenNLP/en-ner-person.bin");
        TokenNameFinderModel model = new TokenNameFinderModel(is);
        is.close();
        NameFinderME nameFinder = new NameFinderME(model);
        Span nameSpans[] = nameFinder.find(sentence);
        String[] names = Span.spansToStrings(nameSpans, sentence);
        Arrays.stream(names).forEach(LOGGER::info);
        for (String name : names) {
            LOGGER.info(name);
        }
    }

    public static void locationFinder() throws IOException {
        InputStream is = new FileInputStream("OpenNLP/en-ner-location.bin");
        TokenNameFinderModel model = new TokenNameFinderModel(is);
        is.close();
        NameFinderME nameFinder = new NameFinderME(model);
        Span locationSpans[] = nameFinder.find(sentence);
        String[] locations = Span.spansToStrings(locationSpans, sentence);
        Arrays.stream(locations).forEach(LOGGER::info);
        for (String location : locations) {
            LOGGER.info(location);
        }
        
    }

    public void trainDocumentCategorizer() {

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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void documentCategorizer() {
        DocumentCategorizerME myCategorizer = new DocumentCategorizerME(docCatModel);
        double[] outcomes = myCategorizer.categorize(sentence);
        String category = myCategorizer.getBestCategory(outcomes);

        if (category.equalsIgnoreCase("GOOD")) {
            LOGGER.info("Document is positive :) ");
        } else {
            LOGGER.info("Document is negative :( ");
        }
    }

    public static void partOfSpeechTagger() throws IOException {
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
                LOGGER.info(posSample.toString());
            }
            lineStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void chunker() throws IOException {
        InputStream is = new FileInputStream("OpenNLP/en-chunker.bin");
        ChunkerModel cModel = new ChunkerModel(is);
        ChunkerME chunkerME = new ChunkerME(cModel);
        String[] taggedSentence = new String[] {"Out", "of", "the", "night", "that", "covers", "me"};
        String pos[] = new String[] { "IN", "IN", "DT", "NN", "WDT", "VBZ", "PRP"};
        String chunks[] = chunkerME.chunk(taggedSentence, pos);
        for (String chunk : chunks) {
            LOGGER.info(chunk);
        }
    }

}
