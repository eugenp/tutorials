package com.baeldung.apache.opennlp;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import opennlp.tools.langdetect.Language;
import opennlp.tools.langdetect.LanguageDetector;
import opennlp.tools.langdetect.LanguageDetectorFactory;
import opennlp.tools.langdetect.LanguageDetectorME;
import opennlp.tools.langdetect.LanguageDetectorModel;
import opennlp.tools.langdetect.LanguageDetectorSampleStream;
import opennlp.tools.util.InputStreamFactory;
import opennlp.tools.util.MarkableFileInputStreamFactory;
import opennlp.tools.util.ObjectStream;
import opennlp.tools.util.PlainTextByLineStream;
import opennlp.tools.util.TrainingParameters;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import org.junit.Test;

public class LanguageDetectorAndTrainingDataUnitTest {

    @Test
    public void givenLanguageDictionary_whenLanguageDetect_thenLanguageIsDetected() throws FileNotFoundException, IOException {
        InputStreamFactory dataIn = new MarkableFileInputStreamFactory(new File("src/main/resources/models/DoccatSample.txt"));
        ObjectStream lineStream = new PlainTextByLineStream(dataIn, "UTF-8");
        LanguageDetectorSampleStream sampleStream = new LanguageDetectorSampleStream(lineStream);
        TrainingParameters params = new TrainingParameters();
        params.put(TrainingParameters.ITERATIONS_PARAM, 100);
        params.put(TrainingParameters.CUTOFF_PARAM, 5);
        params.put("DataIndexer", "TwoPass");
        params.put(TrainingParameters.ALGORITHM_PARAM, "NAIVEBAYES");

        LanguageDetectorModel model = LanguageDetectorME.train(sampleStream, params, new LanguageDetectorFactory());

        LanguageDetector ld = new LanguageDetectorME(model);
        Language[] languages = ld.predictLanguages("estava em uma marcenaria na Rua Bruno");
        
        assertThat(Arrays.asList(languages)).extracting("lang", "confidence").contains(tuple("pob", 0.9999999950605625),
                 tuple("ita", 4.939427661577956E-9), tuple("spa", 9.665954064665144E-15),
                tuple("fra", 8.250349924885834E-25));
    }
}
