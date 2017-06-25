package com.baeldung.javatuples;

import org.javatuples.KeyValue;
import org.javatuples.LabelValue;
import org.javatuples.Pair;
import org.javatuples.Quartet;
import org.javatuples.Triplet;
import org.javatuples.Unit;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class JavaTuplesUnitTest {

    @SuppressWarnings("unused")
    @Test
    public void whenCreatingTuples_thenCreateTuples() {
        Pair<String, Integer> pair = new Pair<String, Integer>("This is a pair", 55);
        Triplet<String, Integer, Double> triplet = Triplet.with("hello", 23, 33.2);

        List<String> collectionOfNames = Arrays.asList("john", "doe", "anne", "alex");
        Quartet<String, String, String, String> quartet = Quartet.fromCollection(collectionOfNames);

        Pair<String, String> pairFromList = Pair.fromIterable(collectionOfNames, 2);

        String[] names = new String[]{"john", "doe", "anne"};
        Triplet<String, String, String> triplet2 = Triplet.fromArray(names);
    }

    @Test
    public void whenGetValuexFromTuples_thenRetriveValueWithType() {
        Quartet<String, Double, Integer, String> quartet = Quartet.with("john", 72.5, 32, "1051 SW");

        String name = quartet.getValue0();
        Integer age = quartet.getValue2();
        assertThat(name).isEqualTo("john");
        assertThat(age).isEqualTo(32);
    }

    @Test
    public void whenGetKeyValue_thenGetKeyValue() {
        KeyValue<Integer, String> keyValue = KeyValue.with(5, "F");
        Integer key = keyValue.getKey();
        String value = keyValue.getValue();

        assertThat(key).isEqualTo(5);
        assertThat(value).isEqualTo("F");
    }

    @Test
    public void whenGetLabelValue_thenGetLabelValue() {
        LabelValue<Integer, String> labelValue = LabelValue.with(5, "F");
        Integer key = labelValue.getLabel();
        String value = labelValue.getValue();

        assertThat(key).isEqualTo(5);
        assertThat(value).isEqualTo("F");
    }

    @Test
    public void whenGetValueFromTuples_thenRetriveValueWithoutType() {
        Quartet<String, Double, Integer, String> quartet = Quartet.with("john", 72.5, 32, "1051 SW");

        String name = (String) quartet.getValue(0);
        Integer age = (Integer) quartet.getValue(2);
        assertThat(name).isEqualTo("john");
        assertThat(age).isEqualTo(32);
    }

    @Test
    public void whenSetValueInTuple_thenGetANewTuple() {
        Pair<String, Integer> john = Pair.with("john", 32);
        Pair<String, Integer> alex = john.setAt0("alex");
        assertThat(john.toString()).isNotEqualTo(alex.toString());
    }

    @Test
    public void whenAddNewElement_thenCreateNewTuple() {
        Pair<String, Integer> pair1 = Pair.with("john", 32);
        Triplet<String, Integer, String> triplet1 = pair1.add("1051 SW");
        assertThat(triplet1.contains("john"));
        assertThat(triplet1.contains(32));
        assertThat(triplet1.contains("1051 SW"));

        Pair<String, Integer> pair2 = Pair.with("alex", 45);
        Quartet<String, Integer, String, Integer> quartet2 = pair1.add(pair2);
        assertThat(quartet2.containsAll(pair1));
        assertThat(quartet2.containsAll(pair2));

        Quartet<String, Integer, String, Integer> quartet1 = pair1.add("alex", 45);
        assertThat(quartet1.containsAll("alex", "john", 32, 45));

        Triplet<String, String, Integer> triplet2 = pair1.addAt1("1051 SW");
        assertThat(triplet2.indexOf("john")).isEqualTo(0);
        assertThat(triplet2.indexOf("1051 SW")).isEqualTo(1);
        assertThat(triplet2.indexOf(32)).isEqualTo(2);

        Unit<Integer> unit = pair1.removeFrom0();
        assertThat(unit.contains(32));
    }

    @Test
    public void whenCallingToList_thenReturnList() {
        Quartet<String, Double, Integer, String> quartet = Quartet.with("john", 72.5, 32, "1051 SW");
        List<Object> list = quartet.toList();
        assertThat(list.size()).isEqualTo(4);
    }

    @Test
    public void whenCallingToArray_thenReturnArray() {
        Quartet<String, Double, Integer, String> quartet = Quartet.with("john", 72.5, 32, "1051 SW");
        Object[] array = quartet.toArray();
        assertThat(array.length).isEqualTo(4);
    }
}
