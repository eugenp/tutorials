package com.baeldung.libraries.opencsv;

import com.baeldung.libraries.opencsv.beans.CsvBean;
import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class OpenCsvIntegrationTest {

    private static final String NEW_LINE = System.lineSeparator();

    @Test
    public void givenSampleData_whenReadUsingPosition_thenContentsRead() throws Exception {
        List<CsvBean> values = Application.simplePositionBeanExample();

        assertThat(values)
          .extracting(Object::toString)
          .containsExactly(
          "colA,colB",
          "A,B",
          "C,D",
          "G,G",
          "G,F"
        );
    }

    @Test
    public void givenSampleData_whenReadUsingNamedColumn_thenContentsRead() throws Exception {
        List<CsvBean> values = Application.namedColumnBeanExample();

        assertThat(values)
          .extracting(Object::toString)
          .containsExactly(
            "Joe,27",
            "Jane,32",
            "Bob,53"
        );
    }

    @Test
    public void givenSampleData_whenReadLineByLine_thenContentsRead() throws Exception {
        List<String[]> lineByLineContents = Application.readLineByLineExample();

        assertThat(lineByLineContents)
          .containsExactly(
            new String[] {"colA", "colB"},
            new String[] {"A", "B"},
            new String[] {"C", "D"},
            new String[] {"G", "G"},
            new String[] {"G", "F"}
          );
    }

    @Test
    public void givenSampleData_whenReadAllLines_thenContentsRead() throws Exception {

        List<String[]> contents = Application.readAllLinesExample();

        assertThat(contents)
          .containsExactly(
            new String[] {"colA", "colB"},
            new String[] {"A", "B"},
            new String[] {"C", "D"},
            new String[] {"G", "G"},
            new String[] {"G", "F"}
          );
    }

    @Test
    public void givenSampleData_whenWriteCsvUsingBean_thenContentsWritten() throws Exception {
        String contents = Application.writeCsvFromBeanExample();

        assertThat(contents.split(NEW_LINE))
          .containsExactly(
          "'COLA','COLB','COLC'",
          "'Test1','sample','data'",
          "'Test2','ipso','facto'"
        );
    }

    @Test
    public void givenSampleData_whenWriteCsvLineByLine_thenContentsWritten() throws Exception {
        String contents = Application.writeLineByLineExample();

        assertThat(contents.split(NEW_LINE))
          .containsExactly(
            "\"ColA\",\"ColB\",\"ColC\",\"ColD\"",
            "\"A\",\"B\",\"A\",\"B\"",
            "\"BB\",\"AB\",\"AA\",\"B\""
          );
    }

    @Test
    public void givenSampleData_whenWriteCsvAllLines_thenContentsWritten() throws Exception {
        String contents = Application.writeAllLinesExample();

        assertThat(contents.split(NEW_LINE))
          .containsExactly(
            "\"ColA\",\"ColB\",\"ColC\",\"ColD\"",
            "\"A\",\"B\",\"A\",\"B\"",
            "\"BB\",\"AB\",\"AA\",\"B\""
          );
    }

}